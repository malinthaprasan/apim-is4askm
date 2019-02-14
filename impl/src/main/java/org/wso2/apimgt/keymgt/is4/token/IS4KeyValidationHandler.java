package org.wso2.apimgt.keymgt.is4.token;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.apimgt.impl.APIConstants;
import org.wso2.carbon.apimgt.impl.dto.APIKeyValidationInfoDTO;
import org.wso2.carbon.apimgt.keymgt.APIKeyMgtException;
import org.wso2.carbon.apimgt.keymgt.handlers.DefaultKeyValidationHandler;
import org.wso2.carbon.apimgt.keymgt.service.TokenValidationContext;
import org.wso2.carbon.identity.application.authentication.framework.model.AuthenticatedUser;
import org.wso2.carbon.identity.core.util.IdentityUtil;
import org.wso2.carbon.identity.oauth.cache.CacheEntry;
import org.wso2.carbon.identity.oauth.cache.OAuthCache;
import org.wso2.carbon.identity.oauth.cache.OAuthCacheKey;
import org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception;
import org.wso2.carbon.identity.oauth2.dao.OAuthTokenPersistenceFactory;
import org.wso2.carbon.identity.oauth2.model.AccessTokenDO;
import org.wso2.carbon.identity.oauth2.model.ResourceScopeCacheEntry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class IS4KeyValidationHandler extends DefaultKeyValidationHandler {

    private static Log log = LogFactory.getLog(IS4KeyValidationHandler.class);

    public boolean validateScopes(TokenValidationContext validationContext) throws APIKeyMgtException {
        log.debug("Validating scopes from the IS4KeyValidationHandler");

        if (validationContext.isCacheHit()) {
            return true;
        }

        APIKeyValidationInfoDTO apiKeyValidationInfoDTO = validationContext.getValidationInfoDTO();

        if (apiKeyValidationInfoDTO == null) {
            throw new APIKeyMgtException("Key Validation information not set");
        }

        String[] scopes = null;
        Set<String> scopesSet = apiKeyValidationInfoDTO.getScopes();

        if (scopesSet != null && !scopesSet.isEmpty()) {
            scopes = scopesSet.toArray(new String[scopesSet.size()]);
            if (log.isDebugEnabled() && scopes != null) {
                StringBuilder scopeList = new StringBuilder();
                for (String scope : scopes) {
                    scopeList.append(scope);
                    scopeList.append(",");
                }
                scopeList.deleteCharAt(scopeList.length() - 1);
                log.debug("Scopes allowed for token : " + validationContext.getAccessToken() + " : "
                        + scopeList.toString());
            }
        }

        AuthenticatedUser user = new AuthenticatedUser();
        user.setUserName(apiKeyValidationInfoDTO.getEndUserName());

        if (user.getUserName() != null && APIConstants.FEDERATED_USER
                .equalsIgnoreCase(IdentityUtil.extractDomainFromName(user.getUserName()))) {
            user.setFederatedUser(true);
        }

        AccessTokenDO accessTokenDO = new AccessTokenDO(apiKeyValidationInfoDTO.getConsumerKey(), user, scopes, null,
                null, apiKeyValidationInfoDTO.getValidityPeriod(), apiKeyValidationInfoDTO.getValidityPeriod(),
                apiKeyValidationInfoDTO.getType());

        accessTokenDO.setAccessToken(validationContext.getAccessToken());

        String actualVersion = validationContext.getVersion();
        //Check if the api version has been prefixed with _default_
        if (actualVersion != null && actualVersion.startsWith(APIConstants.DEFAULT_VERSION_PREFIX)) {
            //Remove the prefix from the version.
            actualVersion = actualVersion.split(APIConstants.DEFAULT_VERSION_PREFIX)[1];
        }
        String resource = validationContext.getContext() + "/" + actualVersion + validationContext
                .getMatchingResource()
                + ":" +
                validationContext.getHttpVerb();

        try {
            if (validateScope(accessTokenDO, resource)) {
                return true;
            } else {
                apiKeyValidationInfoDTO.setAuthorized(false);
                apiKeyValidationInfoDTO.setValidationStatus(APIConstants.KeyValidationStatus.INVALID_SCOPE);
                return false;
            }
        } catch (IdentityOAuth2Exception e) {
            log.error("ERROR while validating token scope " + e.getMessage(), e);
            apiKeyValidationInfoDTO.setAuthorized(false);
            apiKeyValidationInfoDTO.setValidationStatus(APIConstants.KeyValidationStatus.INVALID_SCOPE);
        }
        return false;
    }

    private boolean validateScope(AccessTokenDO accessTokenDO, String resource) throws IdentityOAuth2Exception {
        if (resource == null) {
            return true;
        }

        String[] scopes = accessTokenDO.getScope();
        if (scopes == null || scopes.length == 0) {
            return true;
        }

        String resourceScope = null;
        int resourceTenantId = -1;
        boolean cacheHit = false;
        OAuthCacheKey cacheKey = new OAuthCacheKey(resource);
        CacheEntry result = OAuthCache.getInstance().getValueFromCache(cacheKey);
        if (result != null && result instanceof ResourceScopeCacheEntry) {
            resourceScope = ((ResourceScopeCacheEntry) result).getScope();
            resourceTenantId = ((ResourceScopeCacheEntry) result).getTenantId();
            cacheHit = true;
        }

        if (!cacheHit) {
            Pair<String, Integer> scopeMap = OAuthTokenPersistenceFactory.getInstance().getTokenManagementDAO()
                    .findTenantAndScopeOfResource(resource);
            if (scopeMap != null) {
                resourceScope = scopeMap.getLeft();
                resourceTenantId = scopeMap.getRight().intValue();
            }

            cacheKey = new OAuthCacheKey(resource);
            ResourceScopeCacheEntry cacheEntry = new ResourceScopeCacheEntry(resourceScope);
            cacheEntry.setTenantId(resourceTenantId);
            OAuthCache.getInstance().addToCache(cacheKey, cacheEntry);
        }

        if (resourceScope == null) {
            if (log.isDebugEnabled()) {
                log.debug("Resource '" + resource + "' is not protected with a scope");
            }

            return true;
        }

        List<String> scopeList = new ArrayList(Arrays.asList(scopes));
        if (!scopeList.contains(resourceScope)) {
            if (log.isDebugEnabled() && IdentityUtil.isTokenLoggable("AccessToken")) {
                log.debug(
                        "Access token '" + accessTokenDO.getAccessToken() + "' does not bear the scope '"
                                + resourceScope + "'");
            }

            return false;
        }
        
        return true;
    }
}
