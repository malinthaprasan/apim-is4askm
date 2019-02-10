package com.wso2.services.apim.extension.workflow;

import com.wso2.services.apim.extension.APIMClient;
import com.wso2.services.apim.extension.IS4AdminAPIClient;
import com.wso2.services.apim.extension.MappingUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.apimgt.api.APIManagementException;
import org.wso2.carbon.apimgt.api.WorkflowResponse;
import org.wso2.carbon.apimgt.api.model.APIIdentifier;
import org.wso2.carbon.apimgt.impl.dto.SubscriptionWorkflowDTO;
import org.wso2.carbon.apimgt.impl.dto.WorkflowDTO;
import org.wso2.carbon.apimgt.impl.workflow.SubscriptionDeletionSimpleWorkflowExecutor;
import org.wso2.carbon.apimgt.impl.workflow.WorkflowException;
import org.wso2.services.is4.ApiException;
import org.wso2.services.is4.model.ClientDto;
import org.wso2.services.is4.model.ProtectedResourceDto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class IS4SubscriptionDeletionWorkflow extends SubscriptionDeletionSimpleWorkflowExecutor {
    private static Log log = LogFactory.getLog(IS4SubscriptionDeletionWorkflow.class);


    private final APIMClient apimClient;
    private final IS4AdminAPIClient is4AdminAPIClient;

    public IS4SubscriptionDeletionWorkflow() {
        apimClient = new APIMClient();
        is4AdminAPIClient = new IS4AdminAPIClient();

    }

    @Override
    public WorkflowResponse execute(WorkflowDTO workflowDTO) throws WorkflowException {
        SubscriptionWorkflowDTO subscriptionWorkflowDTO = (SubscriptionWorkflowDTO) workflowDTO;

        APIIdentifier apiIdentifier = MappingUtil.getAPIID(subscriptionWorkflowDTO.getApiName(),
                subscriptionWorkflowDTO.getApiVersion(), subscriptionWorkflowDTO.getApiProvider());
        String apiId = apiIdentifier.toString();

        ProtectedResourceDto protectedResourceDto;
        try {
            protectedResourceDto = is4AdminAPIClient.getProtectedResource(apiId);
        } catch (ApiException e) {
            String msg = "Unable to get the protected resource from IS4 server with name : '" + apiId + "'";
            throw new WorkflowException(msg, e);
        }


        List<String> protectedResourceScopesList = new ArrayList<>();
        if (protectedResourceDto == null) {
            // This means that the protected resource is missing in IS4.
            // Flow cannot continue.
            String msg = "Unable to create protected resource : '" + apiId + "' in IS4";
            throw new WorkflowException(msg);
        } else {
            // This means that the protected resource is created in IS4.
            // Hence we retrieve the existing scopes.
            List<String> protectedResourceList = new ArrayList<>();
            protectedResourceList.add(apiId);

            try {
                protectedResourceScopesList = is4AdminAPIClient.getScopeList(protectedResourceList);
            } catch (ApiException e) {
                String msg = "Unable to get the list of scopes for protected resource : '" + apiId + "'";
                throw new WorkflowException(msg, e);
            }
        }

        // Get the application id and the corresponding client information from IS4
        int applicationId = subscriptionWorkflowDTO.getApplicationId();

        Set<String> consumerKeys;
        try {
            consumerKeys = apimClient.getConsumerKeysOfApplication(applicationId);
        } catch (APIManagementException e) {
            String msg = "Error while retrieving consumer keys of application : '"
                    + subscriptionWorkflowDTO.getApplicationName() + "'";

            log.debug(msg + " Application ID : '" + applicationId + "'");
            throw new WorkflowException(msg, e);
        }

        if (consumerKeys != null) {
            for (String consumerKey : consumerKeys) {
                ClientDto clientDto;
                try {
                    clientDto = is4AdminAPIClient.getClientByConsumerKey(consumerKey);
                } catch (ApiException e) {
                    String msg = "Unable to retrieve the consumer keys for consumer key : '" + consumerKey + "'";
                    throw new WorkflowException(msg, e);
                }

                if (clientDto == null) {
                    String msg = "Unable to retrieve the consumer keys for consumer key : '" + consumerKey + "'";
                    throw new WorkflowException(msg);
                }
                List<String> allowedScopes = clientDto.getAllowedScopes();

                Set<String> uniqueScopes = new HashSet<>();
                uniqueScopes.addAll(allowedScopes);

                // Remove the scopes related to the unsubscribed API.
                uniqueScopes.removeAll(protectedResourceScopesList);

                clientDto.setAllowedScopes(new ArrayList<>(uniqueScopes));
                try {
                    is4AdminAPIClient.updateClientById(clientDto.getId(), clientDto);
                } catch (ApiException e) {
                    throw new WorkflowException("Unable to update IS4 OAuth client details", e);
                }
            }
        }
        return super.execute(workflowDTO);
    }
}
