package com.wso2.services.apim.extension;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.apimgt.api.APIConsumer;
import org.wso2.carbon.apimgt.api.APIManagementException;
import org.wso2.carbon.apimgt.api.model.APIIdentifier;
import org.wso2.carbon.apimgt.api.model.Scope;
import org.wso2.carbon.apimgt.api.model.SubscribedAPI;
import org.wso2.carbon.apimgt.api.model.Subscriber;
import org.wso2.carbon.apimgt.impl.APIManagerFactory;
import org.wso2.carbon.apimgt.impl.dao.ApiMgtDAO;
import org.wso2.carbon.apimgt.impl.utils.APIMgtDBUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class APIMClient {

    protected ApiMgtDAO apiMgtDAO;
    private static Log log = LogFactory.getLog(IdentityExpressAsKMImpl.class);
    
    public APIMClient() {
        apiMgtDAO = ApiMgtDAO.getInstance();    
    }

    public String getIS4AppIdFromConsumerKey(String consumerKey) throws APIManagementException {
        String logPrefix = "[getAppIdFromConsumerKey: consumerKey] ";
        log.debug(logPrefix + "Started");
        Map<String, String> applicationIdAndTokenTypeMap =
                apiMgtDAO.getApplicationIdAndTokenTypeByConsumerKey(consumerKey);
        log.debug(logPrefix + "Retrieved from Id and Type:" + applicationIdAndTokenTypeMap);
        if (applicationIdAndTokenTypeMap != null) {
            String applicationId = applicationIdAndTokenTypeMap.get("application_id");
            String tokenType = applicationIdAndTokenTypeMap.get("token_type");
            Map<String, String> appAttributes = getApplicationAttributes(Integer.getInteger(applicationId));
            log.debug(logPrefix + "App Attributes: " + appAttributes);
            log.debug(logPrefix + "Completed");
            return appAttributes.get(Constants.IS4_APP_ATTR_PREFIX_DEFAULT + tokenType);
        } else {
            throw new APIManagementException("Cannot find APIM Application Id from consumer key " + consumerKey);
        }
        
    }

    public List<String> getSubscribedAPIIds(String username, String applicationName) throws APIManagementException {
        Subscriber subscriber = new Subscriber(username);
        Set<SubscribedAPI> subscriptions;
        APIConsumer apiConsumer = APIManagerFactory.getInstance().getAPIConsumer(username);
        subscriptions = apiConsumer.getSubscribedAPIs(subscriber, applicationName, null);
        Iterator<SubscribedAPI> subscribedAPIIterator = subscriptions.iterator();
        List<String> identifiers = new ArrayList<>();

        while (subscribedAPIIterator.hasNext()) {
            SubscribedAPI subscribedAPI = subscribedAPIIterator.next();
            identifiers.add(subscribedAPI.getApiId().toString());
            if (log.isDebugEnabled()) {
                log.debug(
                        "API " + subscribedAPI.getApiId() + " is subscribed to the the application " + username + ":"
                                + applicationName);
            }
        }
        return identifiers;
    }

    private Map<String, String> getApplicationAttributes(int id) throws APIManagementException {
        try (Connection connection = APIMgtDBUtil.getConnection()) {
            return apiMgtDAO.getApplicationAttributes(connection, id);
        } catch (SQLException e) {
            throw new APIManagementException("Error while getting application attributes for app Id " + id);
        }
    }
}
