package com.wso2.services.apim.extension;

import org.junit.Test;
import org.wso2.services.is4.ApiException;
import org.wso2.services.is4.model.ClientDto;
import org.wso2.services.is4.model.ClientDtoRet;
import org.wso2.services.is4.model.CreateClientDto;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class IS4AdminAPIClientTest {
    private static final String CLIENT_NAME = "test-client";
    private static final String CLIENT_CALLBACK = "http://localhost/sample";

    private final IS4AdminAPIClient api;

    public IS4AdminAPIClientTest() throws ApiException {
        api = new IS4AdminAPIClient();
        cleanup();
    }

    private void cleanup() throws ApiException {
        ClientDtoRet response = api.getAllClients();

        for (ClientDto dto : response) {
            if (dto.getClientName().equals(CLIENT_NAME)) {
                api.deleteClientById(dto.getId());
                break;
            }
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
    
//    @Test
//    public void updateClientIdWithIdTest() throws ApiException {
//        ClientDto addedApp = api.addClient(CLIENT_NAME, CLIENT_CALLBACK);
//        addedApp.setClientId(addedApp.getId());
//        api.updateClient(addedApp.getId(), addedApp);
//        
//        ClientDto updatedApp = api.getClientById(addedApp.getId());
//        assertEquals("Updating consumer key with id failed", addedApp.getId(), updatedApp.getClientId());
//    }
    
    
}
