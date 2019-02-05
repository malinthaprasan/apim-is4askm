package com.wso2.services.apim.extension;

import org.junit.Test;
import org.wso2.services.is4.ApiException;
import org.wso2.services.is4.model.ClientDto;
import org.wso2.services.is4.model.ClientDtoRet;
import org.wso2.services.is4.model.CreateClientDto;

import java.util.ArrayList;

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
                api.deleteClientByClientId(dto.getId());
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
    public void addClientUsingProvidedConsumerKeyAndRetrieveByItTest() throws ApiException {
        CreateClientDto createClientDto = new CreateClientDto();
        createClientDto.setClientId("12345");
        createClientDto.setClientName(CLIENT_NAME);
        createClientDto.setRedirectUris(new ArrayList<String>() {{
            add(Constants.CALLBACK_URL_DEFAULT);
        }});
        api.addClient(createClientDto);
        
        ClientDto retrieved = api.getClientById("12345");
        assertNotNull(retrieved);
        assertTrue(retrieved.getClientName().equals(CLIENT_NAME));
    }

    @Test
    public void clientsPostTestWithoutCallback() throws ApiException {
        api.addClient(CLIENT_NAME, null);
    }
}