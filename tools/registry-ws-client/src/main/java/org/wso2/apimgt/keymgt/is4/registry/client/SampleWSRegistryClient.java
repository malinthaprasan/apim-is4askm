/*
*  Copyright (c) 2005-2010, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
*  WSO2 Inc. licenses this file to you under the Apache License,
*  Version 2.0 (the "License"); you may not use this file except
*  in compliance with the License.
*  You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied.  See the License for the
* specific language governing permissions and limitations
* under the License.
*/
package org.wso2.apimgt.keymgt.is4.registry.client;

import org.wso2.carbon.registry.core.Registry;
import org.wso2.carbon.registry.core.Resource;
import org.wso2.carbon.registry.ws.client.registry.WSRegistryServiceClient;

import java.io.File;

public class SampleWSRegistryClient {
    private static final String username = "admin";
    private static final String password = "admin";
    private static final String serverURL = "https://localhost:9443/services/";

    private static WSRegistryServiceClient initialize() throws Exception {

//        System.setProperty("javax.net.ssl.trustStore", CARBON_HOME + File.separator + "repository" +
//                File.separator + "resources" + File.separator + "security" + File.separator +
//                "wso2carbon.jks");
        
        
        System.setProperty("javax.net.ssl.trustStore",
                "/home/malintha/wso2apim/cur/NantQ/wso2am-2.5.0/repository/resources/security/client-truststore.jks");
        
        System.setProperty("javax.net.ssl.trustStorePassword", "wso2carbon");
        System.setProperty("javax.net.ssl.trustStoreType", "JKS");

        return new WSRegistryServiceClient(serverURL, username, password, null);
    }
    
	public static void main(String[] args) throws Exception {
        Registry registry = initialize();
        try {
            Resource resource = registry.newResource();
            resource.setContent("" 
                    + "<!-- ABCD SDFDSFDSFDSFS -->" 
                    + "<WorkFlowExtensions>\n"
                    + "    <ApplicationCreation executor=\"org.wso2.carbon.apimgt.impl.workflow.ApplicationCreationSimpleWorkflowExecutor\"/>\n"
                    + "    <!--ApplicationCreation executor=\"org.wso2.carbon.apimgt.impl.workflow.ApplicationCreationWSWorkflowExecutor\">\n"
                    + "         <Property name=\"serviceEndpoint\">http://localhost:9765/services/ApplicationApprovalWorkFlowProcess/</Property>\n"
                    + "         <Property name=\"username\">admin</Property>\n"
                    + "         <Property name=\"password\">admin</Property>\n"
                    + "         <Property name=\"callbackURL\">https://localhost:8243/services/WorkflowCallbackService</Property>\n"
                    + "    </ApplicationCreation-->\n"
                    + "    <ProductionApplicationRegistration executor=\"com.wso2.services.apim.extension.workflow.IS4ApplicationRegistrationWorkflow\"/>\n"
                    + "    <!--ProductionApplicationRegistration executor=\"org.wso2.carbon.apimgt.impl.workflow.ApplicationRegistrationWSWorkflowExecutor\">\n"
                    + "        <Property name=\"serviceEndpoint\">http://localhost:9765/services/ApplicationRegistrationWorkFlowProcess/</Property>\n"
                    + "        <Property name=\"username\">admin</Property>\n"
                    + "        <Property name=\"password\">admin</Property>\n"
                    + "        <Property name=\"callbackURL\">https://localhost:8248/services/WorkflowCallbackService</Property>\n"
                    + "    </ProductionApplicationRegistration-->\n"
                    + "    <SandboxApplicationRegistration executor=\"com.wso2.services.apim.extension.workflow.IS4ApplicationRegistrationWorkflow\"/>\n"
                    + "    <!--SandboxApplicationRegistration executor=\"org.wso2.carbon.apimgt.impl.workflow.ApplicationRegistrationWSWorkflowExecutor\">\n"
                    + "        <Property name=\"serviceEndpoint\">http://localhost:9765/services/ApplicationRegistrationWorkFlowProcess/</Property>\n"
                    + "        <Property name=\"username\">admin</Property>\n"
                    + "        <Property name=\"password\">admin</Property>\n"
                    + "        <Property name=\"callbackURL\">https://localhost:8248/services/WorkflowCallbackService</Property>\n"
                    + "    </SandboxApplicationRegistration-->\n"
                    + "\n"
                    + "    <!--SubscriptionCreation executor=\"org.wso2.carbon.apimgt.impl.workflow.SubscriptionCreationSimpleWorkflowExecutor\"/-->\n"
                    + "    <SubscriptionCreation executor=\"com.wso2.services.apim.extension.workflow.IS4SubscriptionCreationWorkflow\"/>\n"
                    + "\n"
                    + "    <!--SubscriptionCreation executor=\"org.wso2.carbon.apimgt.impl.workflow.SubscriptionCreationWSWorkflowExecutor\">\n"
                    + "         <Property name=\"serviceEndpoint\">http://localhost:9765/services/SubscriptionApprovalWorkFlowProcess/</Property>\n"
                    + "         <Property name=\"username\">admin</Property>\n"
                    + "         <Property name=\"password\">admin</Property>\n"
                    + "         <Property name=\"callbackURL\">https://localhost:8243/services/WorkflowCallbackService</Property>\n"
                    + "    </SubscriptionCreation-->\n"
                    + "    <UserSignUp executor=\"org.wso2.carbon.apimgt.impl.workflow.UserSignUpSimpleWorkflowExecutor\"/>\n"
                    + "    <!--UserSignUp executor=\"org.wso2.carbon.apimgt.impl.workflow.UserSignUpWSWorkflowExecutor\">\n"
                    + "         <Property name=\"serviceEndpoint\">http://localhost:9765/services/UserSignupProcess/</Property>\n"
                    + "         <Property name=\"username\">admin</Property>\n"
                    + "         <Property name=\"password\">admin</Property>\n"
                    + "         <Property name=\"callbackURL\">https://localhost:8243/services/WorkflowCallbackService</Property>\n"
                    + "    </UserSignUp-->\n"
                    + "\n"
                    + "\t<!--\n"
                    + "\t***NOTE:***\n"
                    + "        Users of deletion workflows are expected to implement their own deletion workflow executors and services.\n"
                    + "        By default API Manager only implements the core functionalities required to support deletion workflows and\n"
                    + "        simple deletion workflow executors. Default WS deletion workflow implementations are not available with the\n"
                    + "        distribution.\n"
                    + "    -->\n"
                    + "\n"
                    + "    <!--SubscriptionDeletion executor=\"org.wso2.carbon.apimgt.impl.workflow.SubscriptionDeletionSimpleWorkflowExecutor\"/-->\n"
                    + "    <SubscriptionDeletion executor=\"com.wso2.services.apim.extension.workflow.IS4SubscriptionDeletionWorkflow\"/>\n"
                    + "\n"
                    + "    <!--SubscriptionDeletion executor=\"org.wso2.carbon.apimgt.impl.workflow.SubscriptionDeletionSimpleWorkflowExecutor\">\n"
                    + "         <Property name=\"serviceEndpoint\">http://localhost:9765/services/SubscriptionApprovalWorkFlowProcess/</Property>\n"
                    + "         <Property name=\"username\">admin</Property>\n"
                    + "         <Property name=\"password\">admin</Property>\n"
                    + "         <Property name=\"callbackURL\">https://localhost:8243/services/WorkflowCallbackService</Property>\n"
                    + "    </SubscriptionDeletion -->\n"
                    + "    <ApplicationDeletion executor=\"org.wso2.carbon.apimgt.impl.workflow.ApplicationDeletionSimpleWorkflowExecutor\"/>\n"
                    + "    <!--ApplicationDeletion executor=\"org.wso2.carbon.apimgt.impl.workflow.ApplicationDeletionSimpleWorkflowExecutor\">\n"
                    + "         <Property name=\"serviceEndpoint\">http://localhost:9765/services/ApplicationApprovalWorkFlowProcess/</Property>\n"
                    + "         <Property name=\"username\">admin</Property>\n"
                    + "         <Property name=\"password\">admin</Property>\n"
                    + "         <Property name=\"callbackURL\">https://localhost:8243/services/WorkflowCallbackService</Property>\n"
                    + "    </ApplicationDeletion-->\n"
                    + "    \n"
                    + "    <!-- Publisher related workflows -->\n"
                    + "    <APIStateChange executor=\"org.wso2.carbon.apimgt.impl.workflow.APIStateChangeSimpleWorkflowExecutor\" />\n"
                    + "    <!-- <APIStateChange executor=\"org.wso2.carbon.apimgt.impl.workflow.APIStateChangeWSWorkflowExecutor\">\n"
                    + "        <Property name=\"processDefinitionKey\">APIStateChangeApprovalProcess</Property>\n"
                    + "        <Property name=\"stateList\">Created:Publish,Published:Block</Property>        \n"
                    + "    </APIStateChange>-->\n"
                    + "\n"
                    + "    \n"
                    + "    \n"
                    + "</WorkFlowExtensions>");

            String resourcePath = "/_system/governance/apimgt/applicationdata/workflow-extensions.xml";
            registry.put(resourcePath, resource);

            System.out.println("A resource added to: " + resourcePath);

            
            
            Resource getResource = registry.get("/_system/governance/apimgt/applicationdata/workflow-extensions.xml");
            
            System.out.println("Resource retrieved");
            System.out.println("Printing retrieved resource content: " +
                    new String((byte[])getResource.getContent()));

        } finally {
            //Close the session
            ((WSRegistryServiceClient)registry).logut();
        }
		System.exit(0);
		
	}
}
