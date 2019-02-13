package com.wso2.services.apim.extension.jwt;

import com.nimbusds.jwt.JWTClaimsSet;
import com.wso2.services.apim.extension.internal.ServiceReferenceHolder;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.apimgt.api.APIManagementException;
import org.wso2.carbon.apimgt.impl.APIConstants;
import org.wso2.carbon.apimgt.impl.token.ClaimsRetriever;
import org.wso2.carbon.apimgt.impl.utils.APIUtil;
import org.wso2.carbon.apimgt.keymgt.service.TokenValidationContext;
import org.wso2.carbon.apimgt.keymgt.token.JWTGenerator;
import org.wso2.carbon.user.api.UserStoreException;
import org.wso2.carbon.user.api.UserStoreManager;
import org.wso2.carbon.utils.multitenancy.MultitenantUtils;

import java.util.*;

public class CustomJWTGenerator extends JWTGenerator {
    private static Log log = LogFactory.getLog(CustomJWTGenerator.class);
    private ClaimsRetriever claimsRetriever;

    private String userAttributeSeparator = APIConstants.MULTI_ATTRIBUTE_SEPARATOR_DEFAULT;

    @Override
    public Map<String, String> populateCustomClaims(TokenValidationContext validationContext)
            throws APIManagementException {
        ClaimsRetriever claimsRetriever = this.getClaimsRetriever();
        if (claimsRetriever != null) {
            String tenantAwareUserName = validationContext.getValidationInfoDTO().getEndUserName();

            try {
                int tenantId = APIUtil.getTenantId(tenantAwareUserName);

                if (tenantId != -1) {
                    UserStoreManager manager = ServiceReferenceHolder.getInstance().
                            getRealmService().getTenantUserRealm(tenantId).getUserStoreManager();

                    String tenantDomain = MultitenantUtils.getTenantDomain(tenantAwareUserName);
                    String[] split = tenantAwareUserName.split(tenantDomain);

                    if (split.length != 1) {
                        log.error("Could not extract username without tenant domain for: " + tenantAwareUserName);
                        return null;
                    }

                    String username = split[0].substring(0, split[0].length() - 1);

                    if (manager.isExistingUser(username)) {
                        return claimsRetriever.getClaims(tenantAwareUserName);
                    } else {
                        log.warn("User " + tenantAwareUserName + " cannot be found by user store manager");
                    }
                } else {
                    log.error("Tenant cannot be found for username: " + tenantAwareUserName);
                }
            } catch (APIManagementException e) {
                log.error("Error while retrieving claims ", e);
            } catch (UserStoreException e) {
                log.error("Error while retrieving user store ", e);
            }
        }
        return null;
    }

}
