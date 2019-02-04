package com.wso2.services.apim.extension;

import org.junit.Assert;

import org.junit.Test;
import org.wso2.services.is4.ApiClient;
import org.wso2.services.is4.ApiException;
import org.wso2.services.is4.api.ClientsApi;
import org.wso2.services.is4.model.ClientDto;
import org.wso2.services.is4.model.ClientDtoRet;
import org.wso2.services.is4.model.CreateClientDto;
import org.wso2.services.is4.model.CreateSecretDto;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class APIClientTest {
    private static final String CLIENT_NAME = "test-client";
    private static final String BASE_PATH = "http://localhost:5001";
    private static final String ACCESS_TOKEN = "";

    private final ClientsApi api;

    public APIClientTest () throws ApiException {
        api = new ClientsApi();

        ApiClient client =  api.getApiClient();
        client.setBasePath(BASE_PATH);
        client.setAccessToken(ACCESS_TOKEN);
        client.setDebugging(true);
        
        cleanup();
    }

    private void cleanup() throws ApiException {
        ClientDtoRet response = api.clientsGet(null);

        for (ClientDto dto : response) {
            if (dto.getClientName().equals(CLIENT_NAME)) {
                api.clientsByIdDelete(dto.getId());
                break;
            }
        }
    }
    
    @Test
    public void clientsGetTest() throws ApiException {
        ClientDtoRet response = api.clientsGet(null);

        assertTrue(response.size() > 0);
    }

    @Test
    public void clientsPostTest() throws ApiException {
        CreateClientDto client = new CreateClientDto();

        client.clientName("test-client");
        client.setClientId("1234567");
        client.setClientType(CreateClientDto.ClientTypeEnum.NUMBER_1);

        List<CreateSecretDto> secretDtos = new ArrayList<>();
        CreateSecretDto secretDto = new CreateSecretDto();
        secretDto.setType("Shared Secret");
        secretDtos.add(secretDto);
        client.setClientSecrets(secretDtos);
        client.setRedirectUris(new ArrayList<String>() {{
            add("http://localhost/sample");
        }});

        api.clientsPost(client);
    }
}