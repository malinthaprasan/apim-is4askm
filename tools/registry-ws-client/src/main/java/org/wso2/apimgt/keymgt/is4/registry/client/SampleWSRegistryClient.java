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

public class SampleWSRegistryClient {
    private static final String username = "admin";
    private static final String password = "admin";
    private static final String serverURL = "https://localhost:9443/services/";

    private static WSRegistryServiceClient initialize() throws Exception {
        
        String trustStorePath = System.getProperty("trustStore");
        
        if (trustStorePath == null) {
            throw new Exception("'trustStore' system property should be provided.");
        }
        
        System.setProperty("javax.net.ssl.trustStore", trustStorePath);

        System.setProperty("javax.net.ssl.trustStorePassword", "wso2carbon");
        System.setProperty("javax.net.ssl.trustStoreType", "JKS");

        return new WSRegistryServiceClient(serverURL, username, password, null);
    }
    
	public static void main(String[] args) throws Exception {
        Registry registry = initialize();
        try {
            System.out.println("========================== \nUpdating workflow-extensions.xml in registry.");
            Resource resource = registry.newResource();
            resource.setContent(SampleWSRegistryClient.class.getResourceAsStream("/workflow-extensions.xml"));

            String resourcePath = "/_system/governance/apimgt/applicationdata/workflow-extensions.xml";
            registry.put(resourcePath, resource);

            System.out.println("'workflow-extensions.xml' added to: " + resourcePath);
            
            Resource getResource = registry.get(resourcePath);
            
            System.out.println("Updated 'workflow-extensions.xml' content: \n========================== \n" +
                    new String((byte[])getResource.getContent()) + "\n==========================\n SUCCESS");

        } finally {
            //Close the session
            ((WSRegistryServiceClient)registry).logut();
        }
		System.exit(0);
		
	}
}
