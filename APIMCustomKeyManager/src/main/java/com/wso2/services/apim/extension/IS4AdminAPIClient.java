package com.wso2.services.apim.extension;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.services.is4.ApiClient;
import org.wso2.services.is4.ApiException;
import org.wso2.services.is4.api.ClientsApi;
import org.wso2.services.is4.api.ProtectedResourcesApi;
import org.wso2.services.is4.model.ClientDto;
import org.wso2.services.is4.model.ClientDtoRet;
import org.wso2.services.is4.model.CreateClientDto;
import org.wso2.services.is4.model.CreateProtectedResourceDto;
import org.wso2.services.is4.model.CreateSecretDto;
import org.wso2.services.is4.model.ProtectedResourceDto;
import org.wso2.services.is4.model.ProtectedResourceList;
import org.wso2.services.is4.model.ScopeDto;
import org.wso2.services.is4.model.SecretDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class IS4AdminAPIClient {

    private final ClientsApi clientsApi;
    private final ProtectedResourcesApi resourcesApi;
    private static Log log = LogFactory.getLog(IS4AdminAPIClient.class);
    
    public IS4AdminAPIClient() {
        clientsApi = new ClientsApi();
        ApiClient client = clientsApi.getApiClient();
        client.setBasePath(Constants.ADMIN_API_BASE_PATH_DEFAULT);
        client.setAccessToken(
                "eyJhbGciOiJSUzI1NiIsImtpZCI6IjFjMzU3NzQ0MjZjZmFiZDhmOGMwMThjMjIzNDllOWRkIiwidHlwIjoiSldUIn0.eyJuYmYiOjE1NDkyNjc2OTEsImV4cCI6MTU1MTg1OTY5MSwiaXNzIjoiaHR0cDovL2lkczo1MDAzIiwiYXVkIjpbImh0dHA6Ly9pZHM6NTAwMy9yZXNvdXJjZXMiLCJhZG1pbl9hcGkiXSwiY2xpZW50X2lkIjoiYWRtaW5fdWlfc2FtcGxlIiwic3ViIjoiMjU4ZGY1OTEtMjI5Ni00Y2NlLTg4ZmQtN2NjNDEzMjc3Y2YwIiwiYXV0aF90aW1lIjoxNTQ5MjY3NjkxLCJpZHAiOiJsb2NhbCIsInJvbGUiOiJBZG1pblVJIEFkbWluaXN0cmF0b3IiLCJuYW1lIjoiaW5mb0Byb2Nrc29saWRrbm93bGVkZ2UuY29tIiwic2NvcGUiOlsib3BlbmlkIiwicHJvZmlsZSIsImFkbWluX2FwaSJdLCJhbXIiOlsicHdkIl19.nq01nPQfjhnDxfk9bBD5zFhHL2GtQqSrn5THxCOlfmO2w5zCfekPCc3XAHQSX932JvQXa0kFo7VKHQmRcFDMiB9qo3Y0UFREkrzd_DqMr35ditdCO95MxF2UC7r-pKgRNYDXDk1oRMRYs6_D9johAL7hhKW4bw61ZeUwUkzXugGhoX-wtp8NWHD1U2GtGKVqnC312s_tWVO7amCXpMWlN-EEKmdAhzV6VXEmD0f2u7yYdSryomuoDu9pFnrmk8JlgVKF3kWJ6SV7zf3mPfjdhUVHthB3PndwxRw1p6ZMvLN-JYZULQyL9tDdMMlJpWmhpf1jfRMyibjd8_5JP2YFUA");
        client.setDebugging(true);
        
        resourcesApi = new ProtectedResourcesApi();
        ApiClient resourcesApiClient = resourcesApi.getApiClient();
        resourcesApiClient.setBasePath(Constants.ADMIN_API_BASE_PATH_DEFAULT);
        resourcesApiClient.setAccessToken(
                "eyJhbGciOiJSUzI1NiIsImtpZCI6IjFjMzU3NzQ0MjZjZmFiZDhmOGMwMThjMjIzNDllOWRkIiwidHlwIjoiSldUIn0.eyJuYmYiOjE1NDkyNjc2OTEsImV4cCI6MTU1MTg1OTY5MSwiaXNzIjoiaHR0cDovL2lkczo1MDAzIiwiYXVkIjpbImh0dHA6Ly9pZHM6NTAwMy9yZXNvdXJjZXMiLCJhZG1pbl9hcGkiXSwiY2xpZW50X2lkIjoiYWRtaW5fdWlfc2FtcGxlIiwic3ViIjoiMjU4ZGY1OTEtMjI5Ni00Y2NlLTg4ZmQtN2NjNDEzMjc3Y2YwIiwiYXV0aF90aW1lIjoxNTQ5MjY3NjkxLCJpZHAiOiJsb2NhbCIsInJvbGUiOiJBZG1pblVJIEFkbWluaXN0cmF0b3IiLCJuYW1lIjoiaW5mb0Byb2Nrc29saWRrbm93bGVkZ2UuY29tIiwic2NvcGUiOlsib3BlbmlkIiwicHJvZmlsZSIsImFkbWluX2FwaSJdLCJhbXIiOlsicHdkIl19.nq01nPQfjhnDxfk9bBD5zFhHL2GtQqSrn5THxCOlfmO2w5zCfekPCc3XAHQSX932JvQXa0kFo7VKHQmRcFDMiB9qo3Y0UFREkrzd_DqMr35ditdCO95MxF2UC7r-pKgRNYDXDk1oRMRYs6_D9johAL7hhKW4bw61ZeUwUkzXugGhoX-wtp8NWHD1U2GtGKVqnC312s_tWVO7amCXpMWlN-EEKmdAhzV6VXEmD0f2u7yYdSryomuoDu9pFnrmk8JlgVKF3kWJ6SV7zf3mPfjdhUVHthB3PndwxRw1p6ZMvLN-JYZULQyL9tDdMMlJpWmhpf1jfRMyibjd8_5JP2YFUA");
        resourcesApiClient.setDebugging(true);
    }

    public ClientDto getClientById(String id) throws ApiException {
        return clientsApi.clientsByIdGet(id);
    }

    /**
     * Creates a client with {name}_{consumer-key} format
     * 
     * @param namePrefix Name prefix
     * @param callbackURI Callback URL
     * @return
     * @throws ApiException
     */
    public ClientDto addClient(String namePrefix, final String callbackURI) throws ApiException {
        CreateClientDto createClientDto = new CreateClientDto();
        createClientDto.setClientName(namePrefix);
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

    /**
     * Client name will be in {name}_{consumer-key} format
     * 
     * @param client
     * @return
     * @throws ApiException
     */
    public ClientDto addClient(CreateClientDto client) throws ApiException {
        if (client.getClientId() == null) {
            client.setClientId(UUID.randomUUID().toString());
        }
        client.setClientName(client.getClientName() + "_" + client.getClientId());
        client.setClientType(CreateClientDto.ClientTypeEnum.NUMBER_1);

        List<CreateSecretDto> secretDtosCreate = new ArrayList<>();
        CreateSecretDto secretDto = new CreateSecretDto();
        secretDto.setType("Shared Secret");
        secretDto.setValue(UUID.randomUUID().toString());
        secretDtosCreate.add(secretDto);
        client.setClientSecrets(secretDtosCreate);

        clientsApi.clientsPost(client);

        //update secrets
        ClientDto retrievedClient = getClientByName(client.getClientName());
        List<SecretDto> secretDtosGet = retrievedClient.getClientSecrets();
        for (int i = 0; i < Math.min(secretDtosGet.size(), secretDtosCreate.size()); i++) {
            secretDtosGet.get(i).setValue(secretDtosCreate.get(i).getValue());
        }
        
        return retrievedClient;
    }

    public boolean deleteClientById(String id) throws ApiException {
        clientsApi.clientsByIdDelete(id);
        return true;
    }

    public boolean deleteClientByConsumerKey(String ConsumerKey) throws ApiException {
        ClientDto clientDto = getClientByConsumerKey(ConsumerKey);
        deleteClientById(clientDto.getId());
        return true;
    }

    public ClientDtoRet getAllClients() throws ApiException {
        return clientsApi.clientsGet(null);
    }

    public void updateClientById(String id, ClientDto clientDto) throws ApiException {
        clientsApi.clientsByIdPut(id, clientDto);
    }

    public ClientDto getClientByConsumerKey(String consumerKey) throws ApiException {
        return getClientByName(consumerKey);
    }

    public ClientDto updateRetrievedClientWithScopes (ClientDto clientDto, String[] scopes) throws ApiException {
        clientDto.setAllowedScopes(Arrays.asList(scopes));
        clientsApi.clientsByIdPut(clientDto.getId(), clientDto);
        return clientDto;
    }

    public ClientDto updateRetrievedClientWithGrantTypes (ClientDto clientDto, String[] grants) throws ApiException {
        clientDto.setAllowedGrantTypes(Arrays.asList(grants));
        clientsApi.clientsByIdPut(clientDto.getId(), clientDto);
        return clientDto;
    }

    public ProtectedResourceDto getProtectedResource(String key) throws ApiException {
        ProtectedResourceList resourceList = resourcesApi.protectedResourcesGet(key);
        if (resourceList != null && resourceList.size() > 0) {
            if (resourceList.size() > 1) {
                throw new ApiException("Resources with name " + key + " found more than once.");
            }
            return resourceList.get(0);
        }
        return null;
    }

    public void addProtectedResource(String key, String secret, String[] scopes) throws ApiException {
        CreateProtectedResourceDto protectedResourceDto = new CreateProtectedResourceDto();
        protectedResourceDto.setName(key);
        protectedResourceDto.setDisplayName(key);
        
        if (secret != null) {
            SecretDto secretDto = new SecretDto();
            secretDto.setType("SharedSecret");
            secretDto.setValue(secret);
        }

        resourcesApi.protectedResourcesPost(protectedResourceDto);
        updateProtectedResourceWithScopes(key, scopes);
    }

    public void updateProtectedResourceWithScopes(String key, String[] scopes) throws ApiException {
        String logPrefix = "[Updating protected resource " + key + " with scopes] ";
        
        //if scopes are null, all the scopes of the protected resource except the one with resource name should be 
        //  deleted. Hence, creating an empty scope array
        if (scopes == null) {
            scopes = new String[0];
        }
        
        log.debug(logPrefix + " Retrieving..");
        ProtectedResourceDto protectedResourceDto = getProtectedResource(key);
        if (protectedResourceDto != null) {
            log.debug(logPrefix + " Found..");
            log.debug(logPrefix + " Checking for new scopes.");
            for (String scope : scopes) {
                ScopeDto scopeDto = new ScopeDto();
                scopeDto.setName(scope);
                scopeDto.setDisplayName(scope);
                if (!containsScope(protectedResourceDto.getScopes(), scope)) {
                    log.debug(logPrefix + " Scope " + scope + " is not added. Hence adding..");
                    resourcesApi.protectedResourcesByIdScopesPost(protectedResourceDto.getId(), scopeDto);
                    log.debug(logPrefix + " Scope " + scope + " added.");
                } else {
                    log.debug(logPrefix + " Scope " + scope + " is already there.");
                }
            }
            log.debug(logPrefix + " Checking for new scopes completed.");
            log.debug(logPrefix + " Checking for deleted scopes.");
            for (ScopeDto scopeDto : protectedResourceDto.getScopes()) {
                //Need to skip deleting the scope with protected resource name
                if (!protectedResourceDto.getName().equals(scopeDto.getName())) {
                    if (!ArrayUtils.contains(scopes, scopeDto.getName())) {
                        log.debug(
                                logPrefix + " Scope " + scopeDto.getName() + " is not required. Hence deleting..");
                        resourcesApi.protectedResourcesByIdScopesByScopeIdDelete(protectedResourceDto.getId(),
                                scopeDto.getId());
                        log.debug(logPrefix + " Scope " + scopeDto.getName() + " deleted.");
                    } else {
                        log.debug(logPrefix + " Scope " + scopeDto.getName() + " is already there.");
                    }
                }
            }
            log.debug(logPrefix + " Checking for deleted completed.");
        } else {
            throw new ApiException("Protected resource with name " + key + " not found.");
        }
    }

    public void deleteProtectedResourceWithKey(String key) throws ApiException {
        ProtectedResourceDto protectedResourceDto = getProtectedResource(key);
        resourcesApi.protectedResourcesByIdDelete(protectedResourceDto.getId());
    }

    private ClientDto getClientByName(String name) throws ApiException {
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
    
    private boolean containsScope(List<ScopeDto> scopeDtoList, String scopeName) {
        for (ScopeDto aScopeDtoList : scopeDtoList) {
            if (aScopeDtoList.getName().equals(scopeName)) {
                return true;
            }
        }
        return false;
    }
}
