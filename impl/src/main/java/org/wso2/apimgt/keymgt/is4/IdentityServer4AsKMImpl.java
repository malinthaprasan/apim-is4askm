package org.wso2.apimgt.keymgt.is4;

import com.google.common.base.Ascii;
import org.wso2.apimgt.keymgt.is4.clients.IS4TokenAPIClient;
import org.wso2.apimgt.keymgt.is4.clients.APIManagerAdminClient;
import org.wso2.apimgt.keymgt.is4.clients.IS4AdminAPIClient;
import org.wso2.apimgt.keymgt.is4.clients.IS4IntrospectionAPIClient;
import org.wso2.apimgt.keymgt.is4.exception.IntrospectionAPIException;
import org.wso2.apimgt.keymgt.is4.exception.TokenAPIException;
import org.wso2.apimgt.keymgt.is4.util.ExtentionsUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.common.StringUtils;
import org.json.simple.JSONObject;
import org.wso2.carbon.apimgt.api.APIManagementException;
import org.wso2.carbon.apimgt.api.model.*;
import org.wso2.carbon.apimgt.impl.APIConstants;
import org.wso2.carbon.apimgt.impl.AbstractKeyManager;
import org.wso2.services.is4.ApiException;
import org.wso2.services.is4.model.ClientDto;
import org.wso2.services.is4.model.ProtectedResourceDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.wso2.apimgt.keymgt.is4.Constants.IS4_DEFAULT_SCOPE_DEFAULT;

public class IdentityServer4AsKMImpl extends AbstractKeyManager {

    private static Log log = LogFactory.getLog(IdentityServer4AsKMImpl.class);
    private static final String ERR_MESSAGE = " Error occurred during operation.";

    private KeyManagerConfiguration keyManagerConfiguration;
    private IS4AdminAPIClient is4AdminAPIClient;
    private APIManagerAdminClient APIManagerAdminClient;
    private IS4TokenAPIClient tokenAPIClient;
    private IS4IntrospectionAPIClient introspectionAPIClient;

    public IdentityServer4AsKMImpl() {
        is4AdminAPIClient = new IS4AdminAPIClient();
        APIManagerAdminClient = new APIManagerAdminClient();
        tokenAPIClient = new IS4TokenAPIClient();
        introspectionAPIClient = new IS4IntrospectionAPIClient();
    }

    public void loadConfiguration(KeyManagerConfiguration keyManagerConfiguration) throws APIManagementException {
        log.debug("loadConfiguration executed with : " + keyManagerConfiguration);
        this.keyManagerConfiguration = keyManagerConfiguration;

        String tokenAPIUrl = keyManagerConfiguration.getParameter(Constants.IS4_TOKEN_API_URL);
        if (!StringUtils.isEmpty(tokenAPIUrl)) {
            tokenAPIClient.setTokenAPIUrl(tokenAPIUrl);
        }

        String introspectionAPIUrl = keyManagerConfiguration.getParameter(Constants.IS4_INTROSPECTION_API);
        if (!StringUtils.isEmpty(introspectionAPIUrl)) {
            introspectionAPIClient.setIntrospectionAPIUrl(introspectionAPIUrl);
        }

        is4AdminAPIClient.init(
                tokenAPIClient,
                keyManagerConfiguration.getParameter(Constants.IS4_CLIENT_ID),
                keyManagerConfiguration.getParameter(Constants.IS4_CLIENT_SECRET)
        );

        String adminAPIUrl = keyManagerConfiguration.getParameter(Constants.IS4_ADMIN_API_BASE_PATH);

        if (!StringUtils.isEmpty(adminAPIUrl)) {
            is4AdminAPIClient.setBasePath(adminAPIUrl);
        }
    }

    public AccessTokenRequest buildAccessTokenRequestFromOAuthApp(OAuthApplicationInfo oAuthApplication,
                                                                  AccessTokenRequest tokenRequest)
            throws APIManagementException {
        if (oAuthApplication == null) {
            return tokenRequest;
        }
        if (tokenRequest == null) {
            tokenRequest = new AccessTokenRequest();
        }

        if (oAuthApplication.getClientId() == null || oAuthApplication.getClientSecret() == null) {
            throw new APIManagementException("Consumer key or Consumer Secret missing.");
        }
        tokenRequest.setClientId(oAuthApplication.getClientId());
        tokenRequest.setClientSecret(oAuthApplication.getClientSecret());

        Object tokenScopeObj = oAuthApplication.getParameter("tokenScope");
        if (tokenScopeObj != null) {
            if (tokenScopeObj instanceof String[]) {
                String[] tokenScopeArr = (String[]) tokenScopeObj;
                //change APIM default scope to is4 default scope
                for (int i = 0; i < tokenScopeArr.length; i++) {
                    if (Constants.APIM_TOKEN_SCOPE_DEFAULT.equals(tokenScopeArr[i])) {
                        tokenScopeArr[i] = IS4_DEFAULT_SCOPE_DEFAULT;
                    }
                }
                tokenRequest.setScope((String[]) tokenScopeObj);
            } else if (tokenScopeObj instanceof String) {
                String tokenScope = (String) tokenScopeObj;
                //change APIM default scope to is4 default scope
                if (Constants.APIM_TOKEN_SCOPE_DEFAULT.equals(tokenScope)) {
                    tokenScope = IS4_DEFAULT_SCOPE_DEFAULT;
                }
                String[] tokenScopes = new String[1];
                tokenScopes[0] = tokenScope;
                tokenRequest.setScope(tokenScopes);
            }
        } else {
            String[] tokenScopes = new String[1];
            tokenScopes[0] = IS4_DEFAULT_SCOPE_DEFAULT;
            tokenRequest.setScope(tokenScopes);
        }

        if (oAuthApplication.getParameter(ApplicationConstants.VALIDITY_PERIOD) != null) {
            tokenRequest.setValidityPeriod(Long.parseLong((String) oAuthApplication.getParameter(ApplicationConstants
                    .VALIDITY_PERIOD)));
        }

        return tokenRequest;
    }

    public OAuthApplicationInfo buildFromJSON(String json) throws APIManagementException {
        return null;
    }

    public OAuthApplicationInfo createApplication(OAuthAppRequest oauthAppRequest) throws APIManagementException {
        // Creating DCR Application
        String logPrefix = "[Create OAuth App:" + oauthAppRequest.getOAuthApplicationInfo().getClientName() + "] ";

        OAuthApplicationInfo oAuthApplicationInfo = oauthAppRequest.getOAuthApplicationInfo();
        log.debug(logPrefix + "Started");
        try {
            String appName = oAuthApplicationInfo.getClientName();
            //todo: check with application  sharing
            String appOwner = (String) oAuthApplicationInfo.getParameter("username");

            List<String> subscribedAPIIds = APIManagerAdminClient.getSubscribedAPIIds(appOwner, appName);
            List<String> scopes = is4AdminAPIClient.getScopeList(subscribedAPIIds);

            if (scopes.isEmpty()) {
                // This is an error condition in IS4 where it cannot issue an token for an empty scope.
                // Check whether the default scope exists in IS4
                List<String> existingScopes = is4AdminAPIClient
                        .getScopeList(Collections.singletonList(IS4_DEFAULT_SCOPE_DEFAULT));
                if (existingScopes.isEmpty() || !existingScopes.contains(IS4_DEFAULT_SCOPE_DEFAULT)) {
                    is4AdminAPIClient.addProtectedResource(IS4_DEFAULT_SCOPE_DEFAULT, IS4_DEFAULT_SCOPE_DEFAULT,
                            new String[] { IS4_DEFAULT_SCOPE_DEFAULT });
                }
                scopes.add(IS4_DEFAULT_SCOPE_DEFAULT);
            }

            // Create API request
            ClientDto clientDto = is4AdminAPIClient.addClient(appName, oAuthApplicationInfo.getCallBackURL());

            // Add the access token to the application attributes
            int applicationId = APIManagerAdminClient.getApplicationId(appName, appOwner);
            String attributeKey =
                    generateApplicationSecretPropertyName(
                            String.valueOf(oAuthApplicationInfo.getParameter("key_type")), "Consumer Secret");
            Map<String, String> applicationAttributes = new HashMap<>();
            applicationAttributes.put(attributeKey, clientDto.getClientSecrets().get(0).getValue());

            APIManagerAdminClient.setApplicationAttributes(applicationId, applicationAttributes);

            //Populate the OAuth2 grantTypes coming from the UI
            populateOAuth2GrantTypes(oAuthApplicationInfo, clientDto);

            // This has been done because the grant types were not persisted after sending them in the add request.
            // TODO: Check this in a proper IS4 deployment and if possible send the grant types at application creation time
            clientDto.setAllowedScopes(scopes);
            is4AdminAPIClient.updateClientById(clientDto.getId(), clientDto);

            ClientDto updatedDto = is4AdminAPIClient.getClientById(clientDto.getId());
            ExtentionsUtil.setSecrets(clientDto, updatedDto);
            oAuthApplicationInfo = ExtentionsUtil.getOAuthAppInfoFromIS4Client(updatedDto, oAuthApplicationInfo);

            log.debug(logPrefix + "Completed");
            return oAuthApplicationInfo;
        } catch (LinkageError e) { //can execute when dependencies are not added properly
            handleException(logPrefix + ERR_MESSAGE + " Please make sure "
                    + "correct dependencies are added to the runtime", e);
        } catch (ApiException e) {
            handleException(logPrefix + ERR_MESSAGE, e);
        }
        return null;
    }

    public OAuthApplicationInfo updateApplication(OAuthAppRequest oauthAppRequest) throws APIManagementException {
        String logPrefix = "[Updating OAuth App:" + oauthAppRequest.getOAuthApplicationInfo().getClientName() + "] ";
        log.debug(logPrefix + "started");

        OAuthApplicationInfo oAuthApplicationInfo = oauthAppRequest.getOAuthApplicationInfo();
        try {
            ClientDto clientDto = is4AdminAPIClient.getClientByConsumerKey(oAuthApplicationInfo.getClientId());
            clientDto.setRedirectUris(new ArrayList<>(Collections.singleton(oAuthApplicationInfo.getCallBackURL())));

            populateOAuth2GrantTypes(oAuthApplicationInfo, clientDto);

            is4AdminAPIClient.updateClientById(clientDto.getId(), clientDto);

            ClientDto updatedDto = is4AdminAPIClient.getClientById(clientDto.getId());
            ExtentionsUtil.setSecrets(clientDto, updatedDto);
            oAuthApplicationInfo = ExtentionsUtil.getOAuthAppInfoFromIS4Client(updatedDto, oAuthApplicationInfo);

            log.debug(logPrefix + "Completed");
            return oAuthApplicationInfo;
        } catch (LinkageError e) { //can execute when dependencies are not added properly
            handleException(logPrefix + ERR_MESSAGE + " Please make sure "
                    + "correct dependencies are added to the runtime", e);
        } catch (ApiException e) {
            handleException(logPrefix + ERR_MESSAGE, e);
        }

        return null;
    }

    public void deleteApplication(String consumerKey) throws APIManagementException {
        String logPrefix = "[Deleting App:" + consumerKey + "] ";
        log.debug(logPrefix + "Started");
        try {
            is4AdminAPIClient.deleteClientByConsumerKey(consumerKey);

            // Delete the application attributes
            int applicationId = APIManagerAdminClient.getApplicationIdFromConsumerKey(consumerKey);
            APIManagerAdminClient.deleteApplicationAttributes(applicationId);

            log.debug(logPrefix + "Completed");
        } catch (ApiException e) {
            handleException(logPrefix + ERR_MESSAGE, e);
        }
    }

    public OAuthApplicationInfo retrieveApplication(String consumerKey) throws APIManagementException {
        String logPrefix = "[Retrieving App:" + consumerKey + "] ";
        log.debug(logPrefix + " Started");
        try {
            ClientDto clientDto = is4AdminAPIClient.getClientByConsumerKey(consumerKey);
            OAuthApplicationInfo oAuthApplicationInfo = ExtentionsUtil.getOAuthAppInfoFromIS4Client(clientDto);

            // Set the consumerSecret and access token from application
            int applicationId = APIManagerAdminClient.getApplicationIdFromConsumerKey(consumerKey);
            Map<String, String> applicationAttributes = APIManagerAdminClient.getApplicationAttributes(applicationId);
            String applicationTokenType = APIManagerAdminClient.getApplicationTokenType(consumerKey);

            String attributeKey = generateApplicationSecretPropertyName(applicationTokenType, "Consumer Secret");

            oAuthApplicationInfo.setClientSecret(applicationAttributes.get(attributeKey));

            log.debug(logPrefix + " Completed");
            return oAuthApplicationInfo;
        } catch (ApiException e) {
            handleException(logPrefix + ERR_MESSAGE, e);
        }
        return null;
    }

    public void deleteMappedApplication(String applicationKey) throws APIManagementException {
        log.debug("Request to deleteMappedApplication > " + applicationKey + ". But this is not implemented.");
    }

    public void deleteRegisteredResourceByAPIId(String APIId) throws APIManagementException {

        log.debug("Request to deleteRegisteredResourceByAPIId > " + APIId + ". But this is not implemented.");
    }

    public AccessTokenInfo getAccessTokenByConsumerKey(String consumerKey) throws APIManagementException {
        log.debug("Retrieving token for consumer key : " + consumerKey);
        int applicationId = APIManagerAdminClient.getApplicationIdFromConsumerKey(consumerKey);
        Map<String, String> applicationAttributes = APIManagerAdminClient.getApplicationAttributes(applicationId);
        String applicationTokenType = APIManagerAdminClient.getApplicationTokenType(consumerKey);

        String attributeKey = generateApplicationSecretPropertyName(applicationTokenType, "Access Token");

        AccessTokenInfo tokenInfo = new AccessTokenInfo();
        tokenInfo.setConsumerKey(consumerKey);
        tokenInfo.setAccessToken(applicationAttributes.get(attributeKey));
        return tokenInfo;
    }

    public Set<String> getActiveTokensByConsumerKey(String consumerKey) throws APIManagementException {
        log.debug("Request to getActiveTokensByConsumerKey > " + consumerKey + ". But this is not implemented.");
        return null;
    }

    public KeyManagerConfiguration getKeyManagerConfiguration() throws APIManagementException {
        return keyManagerConfiguration;
    }

    public AccessTokenInfo getNewApplicationAccessToken(AccessTokenRequest accessTokenRequest)
            throws APIManagementException {
        String logPrefix = "[Getting new access token for App:" + accessTokenRequest.getClientId() + "] ";
        log.debug(logPrefix + "Started");
        AccessTokenInfo tokenInfo = new AccessTokenInfo();
        try {
            JSONObject tokenResponse = tokenAPIClient
                    .getNewAccessTokenWithClientCredentials(accessTokenRequest.getClientId(),
                            accessTokenRequest.getClientSecret());
            String accessToken = tokenResponse.get(Constants.OAUTH_RESPONSE_ACCESSTOKEN).toString();
            long validityPeriod = Long.parseLong(tokenResponse.get(Constants.OAUTH_RESPONSE_EXPIRY_TIME).toString());
            if (tokenResponse.get("scope") != null) {
                tokenInfo.setScope(((String) tokenResponse.get("scope")).split(" "));
            }
            tokenInfo.setAccessToken(accessToken);
            tokenInfo.setValidityPeriod(validityPeriod);
            tokenInfo.setConsumerKey(accessTokenRequest.getClientId());
            tokenInfo.setTokenValid(true);

            // Once token creation is successful, we set that information as an attribute of the application.
            try {
                persistApplicationAccessToken(accessTokenRequest, accessToken);
            } catch (APIManagementException e) {
                handleException("Error while persisting application attributes", e);
            }
        } catch (TokenAPIException e) {
            handleException("Error while parsing response from token api", e);
        }
        log.debug(logPrefix + "Completed");
        return tokenInfo;
    }

    public String getNewApplicationConsumerSecret(AccessTokenRequest accessTokenRequest) throws APIManagementException {
        log.debug("Request to getNewApplicationConsumerSecret > " + accessTokenRequest
                + ". But this is not implemented.");
        return null;
    }

    public Map getResourceByApiId(String apiId) throws APIManagementException {
        String logPrefix = "[Retrieving protected resource for api:" + apiId + "] ";
        log.debug(logPrefix + "Started");
        try {
            ProtectedResourceDto resourceDto = is4AdminAPIClient.getProtectedResource(apiId);
            if (resourceDto != null) {
                log.debug(logPrefix + "Found.");
                Map<String, String> resourceMap = new HashMap<>();
                resourceMap.put("resourceId", resourceDto.getId());
                return resourceMap;
            }
        } catch (ApiException e) {
            handleException(logPrefix + "Error while getting resource " + apiId, e);
        }
        log.debug(logPrefix + "Not found.");
        log.debug(logPrefix + "Completed.");
        return null;
    }

    public boolean registerNewResource(API api, Map data) throws APIManagementException {
        String logPrefix = "[Registering a new protected resource for api:" + api.getId().toString() + "] ";
        log.debug(logPrefix + "Started");
        String[] scopes = getAPIScopes(api);
        try {
            is4AdminAPIClient.addProtectedResource(api.getId().toString(), api.getId().toString(), scopes);
        } catch (ApiException e) {
            handleException(logPrefix + "Error while registering resource " + api.getId().toString() + " with scopes.",
                    e);
        }
        log.debug(logPrefix + "Completed");
        return true;
    }

    public boolean updateRegisteredResource(API api, Map data) throws APIManagementException {
        String logPrefix = "[Updating protected resource for api with scopes:" + api.getId().toString() + "] ";
        log.debug(logPrefix + "Started");
        try {
            String[] scopes = getAPIScopes(api);
            Map<String, List<String>> changes = is4AdminAPIClient
                    .updateProtectedResourceWithScopes(api.getId().toString(), scopes);
            
            List<String> scopesToAdd = changes.get(Constants.ADDED_SCOPES);
            List<String> scopesToRemove = changes.get(Constants.REMOVED_SCOPES);
            
            if (log.isDebugEnabled()) {
                if (scopesToAdd != null && scopesToAdd.size() > 0) {
                    String scopesToAddString = String.join(",", scopesToAdd);
                    log.debug("Scopes to add: " + scopesToAddString);
                }
                if (scopesToRemove != null && scopesToRemove.size() > 0) {
                    String scopesToRemoveString = String.join(",", scopesToRemove);
                    log.debug("Scopes to remove: " + scopesToRemoveString);
                }
            }
            APIIdentifier apiId = api.getId();
            List<String> clientsToUpdate = APIManagerAdminClient
                    .getSubscribedApplicationConsumerKeysOfAPI(apiId.getProviderName(), apiId.getApiName(),
                            apiId.getVersion());
            
            if (clientsToUpdate != null && clientsToUpdate.size() > 0) {
                for (String consumerKey : clientsToUpdate) {
                    log.debug("Updating client " + consumerKey + " with scopes");
                    ClientDto dto = is4AdminAPIClient.getClientByConsumerKey(consumerKey);
                    if (dto != null) {
                        is4AdminAPIClient.updateScopesOfRetrievedClient(dto, scopesToAdd, scopesToRemove);
                    } else {
                        log.warn("A stale reference for consumer key " + consumerKey + " which does not exist in " 
                                + "Identity Server");
                    }
                    log.debug("Updated client " + consumerKey + " with scopes");
                }
            }
        } catch (ApiException e) {
            handleException(logPrefix + "Error while updating resource " + api.getId().toString(), e);
        }
        log.debug(logPrefix + "Completed");
        return true;
    }

    public Map<String, Set<Scope>> getScopesForAPIS(String apis) throws APIManagementException {
        log.debug("Request to getScopesForAPIS > " + apis + ". But this is not implemented.");
        return null;
    }

    public AccessTokenInfo getTokenMetaData(String authHeader) throws APIManagementException {
        AccessTokenInfo tokenInfo = new AccessTokenInfo();

        String[] headerParts = authHeader.split(Constants.AUTHORIZATION_HEADER_SPLITTER);

        if (headerParts.length < 1) {
            handleException("Invalid authHeader without accessToken and API name");
        }

        String accessToken = headerParts[0];
        String apiName = headerParts[1];

        JSONObject introspectionResponse;
        try {
            introspectionResponse = introspectionAPIClient
                    .getIntrospectionResponse(accessToken, apiName, apiName);

            tokenInfo.setTokenValid((Boolean) introspectionResponse.get("active"));
            if (!tokenInfo.isTokenValid()) {
                tokenInfo.setErrorcode(APIConstants.KeyValidationStatus.API_AUTH_INVALID_CREDENTIALS);
                return tokenInfo;
            }

            tokenInfo.setConsumerKey((String) introspectionResponse.get("client_id"));
            tokenInfo.setValidityPeriod(3600L);
            tokenInfo.setIssuedTime(System.currentTimeMillis());

            String scopes = (String) introspectionResponse.get("scope");
            tokenInfo.setScope(scopes.split(" "));
            return tokenInfo;
        } catch (IntrospectionAPIException e) {
            e.printStackTrace();
        }
        return null;
    }

    public OAuthApplicationInfo mapOAuthApplication(OAuthAppRequest oAuthAppRequest) throws APIManagementException {
        return oAuthAppRequest.getOAuthApplicationInfo();
    }

    private String[] getAPIScopes(API api) {
        String[] scopes = null;
        if (api.getScopes() != null && api.getScopes().size() > 0) {
            scopes = new String[api.getScopes().size()];
            int i = 0;
            for (Scope s : api.getScopes()) {
                scopes[i++] = s.getName();
            }
        }
        return scopes;
    }

    private void persistApplicationAccessToken(AccessTokenRequest accessTokenRequest, String accessToken)
            throws APIManagementException {
        int applicationId = APIManagerAdminClient.getApplicationIdFromConsumerKey(accessTokenRequest.getClientId());
        String applicationTokenType = APIManagerAdminClient.getApplicationTokenType(accessTokenRequest.getClientId());
        String attributeKey = generateApplicationSecretPropertyName(applicationTokenType, "Access Token");

        //Remove the existing attribute
        APIManagerAdminClient.deleteApplicationAttribute(applicationId, attributeKey);

        Map<String, String> attributesMap = new HashMap<>();
        attributesMap.put(attributeKey, accessToken);
        APIManagerAdminClient.setApplicationAttributes(applicationId, attributesMap);
    }

    private String generateApplicationSecretPropertyName(String key_type, String suffix) {
        String parsedKeyType = key_type.isEmpty() ? key_type :
                (new StringBuilder(key_type.length())).append(Ascii.toUpperCase(key_type.charAt(0)))
                        .append(Ascii.toLowerCase(key_type.substring(1))).toString();
        return parsedKeyType + " " + suffix;
    }

    private void populateOAuth2GrantTypes(OAuthApplicationInfo oAuthApplicationInfo, ClientDto clientDto) {
        List<String> grantTypes = new ArrayList<>();
        if (oAuthApplicationInfo.getParameter("grant_types") != null) {
            String[] allGrantTypes = String.valueOf(oAuthApplicationInfo.getParameter("grant_types")).split(",");
            grantTypes.addAll(Arrays.asList(allGrantTypes));
        } else {
            grantTypes.add("client_credentials");
        }
        clientDto.setAllowedGrantTypes(grantTypes);
    }

    private void handleException(String msg) throws APIManagementException {
        log.error(msg);
        throw new APIManagementException(msg);
    }

    private void handleException(String msg, Throwable e) throws APIManagementException {
        log.error(msg, e);
        throw new APIManagementException(msg, e);
    }
}
