package com.wso2.services.apim.extension.clients;

import com.wso2.services.apim.extension.Constants;
import com.wso2.services.apim.extension.IdentityServer4AsKMImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.apimgt.api.APIConsumer;
import org.wso2.carbon.apimgt.api.APIManagementException;
import org.wso2.carbon.apimgt.api.model.*;
import org.wso2.carbon.apimgt.impl.APIManagerFactory;
import org.wso2.carbon.apimgt.impl.dao.ApiMgtDAO;
import org.wso2.carbon.apimgt.impl.utils.APIMgtDBUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class APIManagerAdminClient {
    private static Log log = LogFactory.getLog(IdentityServer4AsKMImpl.class);
    private ApiMgtDAO apiMgtDAO;

    public APIManagerAdminClient() {
        apiMgtDAO = ApiMgtDAO.getInstance();
    }

    public List<String> getSubscribedAPIIds(String username, String applicationName) throws APIManagementException {
        Set<SubscribedAPI> subscriptions;
        List<String> identifiers = new ArrayList<>();

        Subscriber subscriber = new Subscriber(username);
        APIConsumer apiConsumer = APIManagerFactory.getInstance().getAPIConsumer(username);
        subscriptions = apiConsumer.getSubscribedAPIs(subscriber, applicationName, null);

        for (SubscribedAPI subscribedAPI : subscriptions) {
            identifiers.add(subscribedAPI.getApiId().toString());
            if (log.isDebugEnabled()) {
                log.debug("API " + subscribedAPI.getApiId() + " is subscribed to the the application " + username +
                        ":" + applicationName);
            }
        }
        return identifiers;
    }

    public Map<String, String> getApplicationAttributes(int id) throws APIManagementException {
        try (Connection connection = APIMgtDBUtil.getConnection()) {
            return apiMgtDAO.getApplicationAttributes(connection, id);
        } catch (SQLException e) {
            throw new APIManagementException("Error while getting application attributes for app Id " + id);
        }
    }

    public void setApplicationAttributes(int applicationID, Map<String, String> applicationAttributes)
            throws APIManagementException {
        // There is not update method. Hence deleting and reinserting the values
        for (String attributeKey : applicationAttributes.keySet()) {
            apiMgtDAO.deleteApplicationAttributes(attributeKey, applicationID);
        }
        apiMgtDAO.addApplicationAttributes(applicationAttributes, applicationID, -1234);
    }

    public int getApplicationIdFromConsumerKey(String consumerKey) throws APIManagementException {
        Application application = apiMgtDAO.getApplicationByClientId(consumerKey);

        if (application != null) {
            return application.getId();
        }
        return -1;
    }

    public String getApplicationTokenType(String consumerKey) throws APIManagementException {
        Map<String, String> applicationIdAndTokenTypeMap =
                apiMgtDAO.getApplicationIdAndTokenTypeByConsumerKey(consumerKey);
        if (applicationIdAndTokenTypeMap != null) {
            return applicationIdAndTokenTypeMap.get("token_type");
        }
        return null;
    }

    public void deleteApplicationAttributes(int applicationId) throws APIManagementException {
        Map<String, String> applicationAttributes = getApplicationAttributes(applicationId);
        for (String attributeName : applicationAttributes.keySet()) {
            apiMgtDAO.deleteApplicationAttributes(attributeName, applicationId);
        }
    }

    public void deleteApplicationAttribute(int applicationId, String attributeName) throws APIManagementException {
        apiMgtDAO.deleteApplicationAttributes(attributeName, applicationId);
    }

    public Set<String> getConsumerKeysOfApplication(int applicationId) throws APIManagementException {
        return apiMgtDAO.getConsumerKeysOfApplication(applicationId);
    }

    public Set<Scope> getAPIScopes(APIIdentifier apiIdentifier) throws APIManagementException {
        return apiMgtDAO.getAPIScopes(apiIdentifier);
    }

    public int getApplicationId(String applicationName, String username) throws APIManagementException {
        return apiMgtDAO.getApplicationId(applicationName, username);
    }
}
