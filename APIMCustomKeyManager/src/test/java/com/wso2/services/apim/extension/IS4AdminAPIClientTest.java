package com.wso2.services.apim.extension;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.wso2.services.apim.extension.clients.IS4AdminAPIClient;
import com.wso2.services.apim.extension.clients.IS4TokenAPIClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.wso2.services.is4.ApiException;
import org.wso2.services.is4.model.ClientDto;
import org.wso2.services.is4.model.ClientDtoRet;
import org.wso2.services.is4.model.CreateClientDto;
import org.wso2.services.is4.model.ProtectedResourceDto;
import org.wso2.services.is4.model.ScopeDto;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class IS4AdminAPIClientTest {

    private static Log log = LogFactory.getLog(IS4AdminAPIClientTest.class);
    private static final String CLIENT_NAME = "test-client";
    private static final String CLIENT_CALLBACK = "http://localhost/sample";

    private static final String PROTECTED_RESOURCE_KEY = "sample-api1";
    private static final String PROTECTED_RESOURCE_SCOPE = "sample-api1.read";
    
    private final IS4AdminAPIClient api;

    public IS4AdminAPIClientTest() throws ApiException {
        api = new IS4AdminAPIClient();
        api.init(new IS4TokenAPIClient(), "admin_ui_sample", "admin_ui_sample", "info@rocksolidknowledge.com",
                "Password123!");
        cleanup();
    }

    private void cleanup() throws ApiException {
        ClientDtoRet response = api.getAllClients();

        for (ClientDto dto : response) {
            if (dto.getClientName().contains(CLIENT_NAME)) {
                api.deleteClientById(dto.getId());
                break;
            }
        }
        if (api.getProtectedResource(PROTECTED_RESOURCE_KEY) != null) {
            api.deleteProtectedResourceWithKey(PROTECTED_RESOURCE_KEY);
        }
    }

    @Test
    public void clientsGetTest() throws ApiException {
        ClientDtoRet response = api.getAllClients();

        assertTrue(response.size() > 0);
    }

    @Test
    public void clientsPostTest() throws ApiException {
        api.addClient(CLIENT_NAME, CLIENT_CALLBACK);
    }
    
    @Test
    public void addClientUsingProvidedConsumerKeyAsNameAndRetrieveByItTest() throws ApiException {
        String consumerKey = "12345";

        CreateClientDto createClientDto = new CreateClientDto();
        createClientDto.setClientId(consumerKey);
        createClientDto.setClientName(CLIENT_NAME);
        createClientDto.setRedirectUris(new ArrayList<String>() {{
            add(Constants.CALLBACK_URL_DEFAULT);
        }});
        api.addClient(createClientDto);
        
        ClientDto retrieved = api.getClientByConsumerKey(consumerKey);
        assertNotNull(retrieved);
        assertTrue(retrieved.getClientName().contains(CLIENT_NAME));
        assertTrue(retrieved.getClientName().contains(consumerKey));
    }

    @Test
    public void clientsPostTestWithoutCallback() throws ApiException {
        api.addClient(CLIENT_NAME, null);
    }
    
    /*Doesn't work as IS4 doesn't allow us to update the client id*/
    public void updateClientIdWithIdTest() throws ApiException {
        ClientDto addedApp = api.addClient(CLIENT_NAME, CLIENT_CALLBACK);
        addedApp.setClientId(addedApp.getId());
        api.updateClientById(addedApp.getId(), addedApp);

        ClientDto updatedApp = api.getClientById(addedApp.getId());
        assertEquals("Updating consumer key with id failed", addedApp.getId(), updatedApp.getClientId());
    }

    @Test
    public void createIdentityResourceAndRetrieveByKey() throws ApiException, IOException {
        api.addProtectedResource(PROTECTED_RESOURCE_KEY, "sample-api1-sec", new String[] { PROTECTED_RESOURCE_SCOPE });
        ProtectedResourceDto protectedResourceDto = api.getProtectedResource(PROTECTED_RESOURCE_KEY);

        assertTrue("Protected resource name is wrong", protectedResourceDto.getName().equals(PROTECTED_RESOURCE_KEY));
        assertTrue("Resource scopes are missing", protectedResourceDto.getScopes() != null && protectedResourceDto.getScopes().size() > 0);

        boolean foundScope = false;
        for (ScopeDto scope: protectedResourceDto.getScopes()) {
            if (scope.getName().equals(PROTECTED_RESOURCE_SCOPE)) {
                foundScope = true;
            }
        }

        if (!foundScope) {
            fail("Cannot find scope " + PROTECTED_RESOURCE_SCOPE);
        }
    }

    public void createResourceAppAndGenerateAccessTokenTest() throws ApiException, IOException {
        /*
            curl -X POST http://ids:5003/connect/token -d "client_id=086410bc-505e-4ec6-81bb-25a30d04ed7d
            &client_secret=0b7ee9a2-bc43-4025-8fa8-568cd58f8447&grant_type=client_credentials&scope=api_gw admin_api"
         */
        ClientDto addedApp = api.addClient(CLIENT_NAME, CLIENT_CALLBACK);
        addedApp.setAllowedGrantTypes(new ArrayList<String>() {{
            add("client_credentials");
        }});
        addedApp.setAllowedScopes(new ArrayList<String>() {{
            add("api_gw");
        }});

        api.updateClientById(addedApp.getId(), addedApp);

        OkHttpClient client = new OkHttpClient();
        
        RequestBody formEncoding = new FormEncodingBuilder()
                .add("client_id", addedApp.getClientId())
                .add("client_secret", addedApp.getClientSecrets().get(0).getValue())
                .add("grant_type", "client_credentials")
                .add("scope", "api_gw")
                .build();

        Request request = new Request.Builder()
                .url(Constants.IS4_TOKEN_API_URL_DEFAULT)
                .post(formEncoding)
                .build();

        Response response = client.newCall(request).execute();
        String accessTokenResponse = response.body().string();
        log.info(accessTokenResponse);

        assertTrue(accessTokenResponse.contains("access_token"));
    }
    
}
