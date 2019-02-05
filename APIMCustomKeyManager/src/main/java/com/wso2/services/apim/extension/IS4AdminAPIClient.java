package com.wso2.services.apim.extension;

import org.wso2.services.is4.ApiClient;
import org.wso2.services.is4.ApiException;
import org.wso2.services.is4.api.ClientsApi;
import org.wso2.services.is4.model.ClientDto;
import org.wso2.services.is4.model.ClientDtoRet;
import org.wso2.services.is4.model.CreateClientDto;
import org.wso2.services.is4.model.CreateSecretDto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class IS4AdminAPIClient {

    private final ClientsApi clientsApi;

    public IS4AdminAPIClient() {
        clientsApi = new ClientsApi();
        ApiClient client = clientsApi.getApiClient();
        client.setBasePath(Constants.BASE_PATH);
        client.setAccessToken(
                "eyJhbGciOiJSUzI1NiIsImtpZCI6IjFjMzU3NzQ0MjZjZmFiZDhmOGMwMThjMjIzNDllOWRkIiwidHlwIjoiSldUIn0.eyJuYmYiOjE1NDkyNjc2OTEsImV4cCI6MTU1MTg1OTY5MSwiaXNzIjoiaHR0cDovL2lkczo1MDAzIiwiYXVkIjpbImh0dHA6Ly9pZHM6NTAwMy9yZXNvdXJjZXMiLCJhZG1pbl9hcGkiXSwiY2xpZW50X2lkIjoiYWRtaW5fdWlfc2FtcGxlIiwic3ViIjoiMjU4ZGY1OTEtMjI5Ni00Y2NlLTg4ZmQtN2NjNDEzMjc3Y2YwIiwiYXV0aF90aW1lIjoxNTQ5MjY3NjkxLCJpZHAiOiJsb2NhbCIsInJvbGUiOiJBZG1pblVJIEFkbWluaXN0cmF0b3IiLCJuYW1lIjoiaW5mb0Byb2Nrc29saWRrbm93bGVkZ2UuY29tIiwic2NvcGUiOlsib3BlbmlkIiwicHJvZmlsZSIsImFkbWluX2FwaSJdLCJhbXIiOlsicHdkIl19.nq01nPQfjhnDxfk9bBD5zFhHL2GtQqSrn5THxCOlfmO2w5zCfekPCc3XAHQSX932JvQXa0kFo7VKHQmRcFDMiB9qo3Y0UFREkrzd_DqMr35ditdCO95MxF2UC7r-pKgRNYDXDk1oRMRYs6_D9johAL7hhKW4bw61ZeUwUkzXugGhoX-wtp8NWHD1U2GtGKVqnC312s_tWVO7amCXpMWlN-EEKmdAhzV6VXEmD0f2u7yYdSryomuoDu9pFnrmk8JlgVKF3kWJ6SV7zf3mPfjdhUVHthB3PndwxRw1p6ZMvLN-JYZULQyL9tDdMMlJpWmhpf1jfRMyibjd8_5JP2YFUA");
        client.setDebugging(true);
    }

    public ClientDto getClientById(String id) throws ApiException {
        return clientsApi.clientsByIdGet(id);
    }

    public ClientDto addClient(String name, final String callbackURI) throws ApiException {
        CreateClientDto createClientDto = new CreateClientDto();
        createClientDto.setClientName(name);
        if (callbackURI == null) {
            createClientDto.setRedirectUris(new ArrayList<String>() {{
                add(Constants.CALLBACK_URL_DEFAULT);
            }});
        } else {
            createClientDto.setRedirectUris(new ArrayList<String>() {{
                add(callbackURI);
            }});
        }

        return addClient(createClientDto);
    }

    public ClientDto addClient(CreateClientDto client) throws ApiException {
        final String id = UUID.randomUUID().toString();
        if (client.getClientId() == null) {
            client.setClientId(id);
        }
        client.setClientType(CreateClientDto.ClientTypeEnum.NUMBER_1);

        List<CreateSecretDto> secretDtos = new ArrayList<>();
        CreateSecretDto secretDto = new CreateSecretDto();
        secretDto.setType("Shared Secret");
        secretDtos.add(secretDto);
        client.setClientSecrets(secretDtos);

        clientsApi.clientsPost(client);

        return getClientByName(client.getClientName());
    }
    
    public ClientDto getClientByName(String name) throws ApiException {
        ClientDtoRet clientDtos = clientsApi.clientsGet(name);

        if (clientDtos != null && clientDtos.size() > 0) {
            if (clientDtos.size() > 1) {
                throw new ApiException("Client with name " + name + " found more than once.");
            }
            return clientDtos.get(0);
        } else {
            throw new ApiException("Client with name " + name + " not found.");
        }
    }

    public boolean deleteClientByClientId(String id) throws ApiException {
        clientsApi.clientsByIdDelete(id);
        return true;
    }

    public ClientDtoRet getAllClients() throws ApiException {
        return clientsApi.clientsGet(null);
    } 
}
