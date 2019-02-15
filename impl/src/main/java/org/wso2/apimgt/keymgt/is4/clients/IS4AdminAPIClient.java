package org.wso2.apimgt.keymgt.is4.clients;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONObject;
import org.wso2.apimgt.keymgt.is4.Constants;
import org.wso2.apimgt.keymgt.is4.exception.TokenAPIException;
import org.wso2.services.is4.ApiClient;
import org.wso2.services.is4.ApiException;
import org.wso2.services.is4.api.ClientsApi;
import org.wso2.services.is4.api.ProtectedResourcesApi;
import org.wso2.services.is4.model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class IS4AdminAPIClient {

    private final ClientsApi clientsApi;
    private final ProtectedResourcesApi resourcesApi;
    private static Log log = LogFactory.getLog(IS4AdminAPIClient.class);

    public IS4AdminAPIClient() {

        clientsApi = new ClientsApi();
        ApiClient client = clientsApi.getApiClient();
        client.setDebugging(true);

        resourcesApi = new ProtectedResourcesApi();
        ApiClient resourcesApiClient = resourcesApi.getApiClient();
        resourcesApiClient.setBasePath(Constants.ADMIN_API_BASE_PATH_DEFAULT);
        resourcesApiClient.setDebugging(true);

        clientsApi.getApiClient().setBasePath(Constants.ADMIN_API_BASE_PATH_DEFAULT);
        resourcesApi.getApiClient().setBasePath(Constants.ADMIN_API_BASE_PATH_DEFAULT);
    }

    public void init(IS4TokenAPIClient tokenAPIClient, String clientId, String clientSecret) {
        Interceptor renewTokenInterceptor = new Interceptor() {
            String accessToken = null;
            IS4TokenAPIClient tokenApi = tokenAPIClient;

            public Response intercept(Chain chain) throws IOException {
                if (accessToken == null) {
                    getAccessToken();
                }
                Request originalRequest = chain.request().newBuilder().addHeader(Constants.AUTHORIZATION, accessToken)
                        .build();
                Response response = chain.proceed(originalRequest);
                if (!response.isSuccessful()) {
                    getAccessToken();
                    Request newRequest = originalRequest.newBuilder().removeHeader(Constants.AUTHORIZATION)
                            .addHeader(Constants.AUTHORIZATION, accessToken).build();
                    response = chain.proceed(newRequest);
                }
                return response;
            }

            private void getAccessToken() throws IOException {
                JSONObject tokenResponse;
                try {
                    tokenResponse = tokenApi.getNewAccessTokenWithClientCredentials(clientId, clientSecret);
                    accessToken = Constants.BEARER + tokenResponse.get(Constants.OAUTH_RESPONSE_ACCESSTOKEN);
                } catch (TokenAPIException e) {
                    throw new IOException("Error while getting an access token for " + clientId);
                }
            }
        };

        resourcesApi.getApiClient().getHttpClient().interceptors().add(renewTokenInterceptor);
        clientsApi.getApiClient().getHttpClient().interceptors().add(renewTokenInterceptor);
    }

    public void setBasePath(String basePath) {
        clientsApi.getApiClient().setBasePath(basePath);
        resourcesApi.getApiClient().setBasePath(basePath);
    }

    public ClientDto getClientById(String id) throws ApiException {
        return clientsApi.clientsByIdGet(id);
    }

    /**
     * Creates a client with {name}_{consumer-key} format
     *
     * @param namePrefix  Name prefix
     * @param callbackURI Callback URL
     * @return {@link ClientDto} with the information of the created client
     * @throws ApiException if error occurs
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
     * @param client A {@link ClientDto} with contains the information of the client
     * @return {@link ClientDto} with the response of the application creation
     * @throws ApiException if error occurs
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

        if (retrievedClient == null) {
            String msg = "Unable to retrieve client information for client name : '" + client.getClientName() + "'";
            log.error(msg);
            throw new ApiException(msg);
        }

        List<SecretDto> retrievedClientSecrets = retrievedClient.getClientSecrets();
        for (int i = 0; i < Math.min(retrievedClientSecrets.size(), secretDtosCreate.size()); i++) {
            retrievedClientSecrets.get(i).setValue(secretDtosCreate.get(i).getValue());
        }

        return retrievedClient;
    }

    public void deleteClientById(String id) throws ApiException {
        clientsApi.clientsByIdDelete(id);
    }

    public void deleteClientByConsumerKey(String ConsumerKey) throws ApiException {
        ClientDto clientDto = getClientByConsumerKey(ConsumerKey);
        deleteClientById(clientDto.getId());
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

    /**
     * This method returns the protected resources information from the IS4 server, for the given key.
     * Current implementation uses the APIID(provider_name_version) as the protected resource name(key).
     *
     * @param key Protected resource name
     * @return {@link ProtectedResourceDto} with the information of the protected resource.
     * @throws ApiException if any error happens.
     */
    public ProtectedResourceDto getProtectedResource(String key) throws ApiException {

        // This is a search. hence there can be responses with partially mapped names.
        ProtectedResourceList resourceList = resourcesApi.protectedResourcesGet(key);

        //Removing the partially mapped resources.
        if (resourceList != null && resourceList.size() > 0) {
            for (ProtectedResourceDto protectedResourceDto : resourceList) {
                if (key.equals(protectedResourceDto.getName())) {
                    return protectedResourceDto;
                }
            }
        }
        return null;
    }

    /**
     * This method returns the {@link List} of scopes from the IS4 server, for the given list of protected resources names
     * The current implementation contains uses the APIID((provider_name_version) as the protected resource name(key).
     *
     * @param protectedResourceKeys {@link List} of protected resource names to get the scopes
     * @return {@link List} of the scope names associated with all the protected resources given in the input
     * @throws ApiException if any error occurs.
     */
    public List<String> getScopeList(List<String> protectedResourceKeys) throws ApiException {
        List<String> scopeList = new ArrayList<>();
        for (String resourceKey : protectedResourceKeys) {
            ProtectedResourceDto protectedResourceDto = getProtectedResource(resourceKey);

            if (protectedResourceDto == null) {
                log.warn("Unable to get the protected resource details for key : " + resourceKey);
                continue;
            }
            List<ScopeDto> scopes = protectedResourceDto.getScopes();

            // There will not be any duplicates since both API Manager and IS4 does not allow same name to be reused.
            for (ScopeDto scopeDto : scopes) {
                scopeList.add(scopeDto.getName());
            }
        }
        return scopeList;
    }

    /**
     * This method creates the protected resources in the IS4 server.
     * The current implementation only supports the use of shared secrets with a protected resource.
     *
     * @param key    the name of the protected resource.
     * @param secret the client secret to be associated with the protected resource.
     * @param scopes the list of scopes to be associated with the protected resource.
     * @throws ApiException if error occurs.
     */
    public void addProtectedResource(String key, String secret, String[] scopes) throws ApiException {
        CreateProtectedResourceDto protectedResourceDto = new CreateProtectedResourceDto();
        protectedResourceDto.setName(key);
        protectedResourceDto.setDisplayName(key);

        resourcesApi.protectedResourcesPost(protectedResourceDto);

        ProtectedResourceDto addedResource = getProtectedResource(key);
        updateProtectedResourceWithScopes(addedResource, scopes);

        if (secret != null) {
            CreateSecretDto secretDto = new CreateSecretDto();
            secretDto.setType("SharedSecret");
            secretDto.setValue(secret);
            resourcesApi.protectedResourcesByIdSecretsPost(addedResource.getId(), secretDto);
        }
    }

    /**
     * This method associates the given list of scopes to the given protected resource.
     * This method will add new scopes as well as remove any scopes that have been removed in API Manager
     *
     * @param key    the name of the protected resource
     * @param scopes the list of scopes that needs to be associated with the given protected resource
     * @throws ApiException if an error occurs or if the given protected resource is not in IS4
     */
    public Map<String, List<String>> updateProtectedResourceWithScopes(String key, String[] scopes) throws ApiException {
        ProtectedResourceDto protectedResourceDto = getProtectedResource(key);
        return updateProtectedResourceWithScopes(protectedResourceDto, scopes);
    }

    private Map<String, List<String>> updateProtectedResourceWithScopes(ProtectedResourceDto protectedResourceDto, String[] scopes)
            throws ApiException {
        String logPrefix =
                "[Updating protected resource : '" + (protectedResourceDto != null ? protectedResourceDto.getName() :
                        "") + "' with scopes] ";

        //if scopes are null, all the scopes of the protected resource except the one with resource name should be 
        //  deleted. Hence, creating an empty scope array
        if (scopes == null) {
            scopes = new String[0];
        }

        Map<String, List<String>> changes = new HashMap<>();

        List<String> addedScopes = new ArrayList<>();
        List<String> removedScopes = new ArrayList<>();
        
        log.debug(logPrefix + " Retrieving..");
        if (protectedResourceDto != null) {
            log.debug(logPrefix + " Found. Checking for new scopes.");

            for (String scope : scopes) {
                if (!containsScope(protectedResourceDto.getScopes(), scope)) {
                    if (log.isDebugEnabled()) {
                        log.debug(logPrefix + " Scope : '" + scope + "' is not added. Hence adding..");
                    }

                    ScopeDto scopeDto = new ScopeDto();
                    scopeDto.setName(scope);
                    scopeDto.setDisplayName(scope);
                    resourcesApi.protectedResourcesByIdScopesPost(protectedResourceDto.getId(), scopeDto);
                    
                    addedScopes.add(scope);
                    if (log.isDebugEnabled()) {
                        log.debug(logPrefix + " Scope : '" + scope + "' added.");
                    }
                } else {
                    if (log.isDebugEnabled()) {
                        log.debug(logPrefix + " Scope : '" + scope + "' is already there.");
                    }
                }
            }
            log.debug(logPrefix + " Checking for new scopes completed. Checking for deleted scopes.");
            for (ScopeDto scopeDto : protectedResourceDto.getScopes()) {
                //Need to skip deleting the scope with protected resource name
                if (!protectedResourceDto.getName().equals(scopeDto.getName())) {
                    if (!ArrayUtils.contains(scopes, scopeDto.getName())) {
                        if (log.isDebugEnabled()) {
                            log.debug(logPrefix + " Scope : '" + scopeDto.getName() + "' is not required. Hence deleting.");
                        }

                        // Removing the scope since it was not found.
                        resourcesApi.protectedResourcesByIdScopesByScopeIdDelete(protectedResourceDto.getId(),
                                scopeDto.getId());

                        removedScopes.add(scopeDto.getName());
                        if (log.isDebugEnabled()) {
                            log.debug(logPrefix + " Scope : '" + scopeDto.getName() + "' deleted.");
                        }
                    } else {
                        if (log.isDebugEnabled()) {
                            log.debug(logPrefix + " Scope : '" + scopeDto.getName() + "' is already present.");
                        }
                    }
                }
            }
            log.debug(logPrefix + " Checking for deleted completed.");
            
            changes.put(Constants.ADDED_SCOPES, addedScopes);
            changes.put(Constants.REMOVED_SCOPES, removedScopes);
            
            return changes;
        } else {
            throw new ApiException(
                    "Protected resource not found.");
        }
    }

    /**
     * This method removes the protected resource from IS4 server
     *
     * @param key the name of the protected resource to be removed.
     * @throws ApiException if an error occurs.
     */
    public void deleteProtectedResourceWithKey(String key) throws ApiException {
        ProtectedResourceDto protectedResourceDto = getProtectedResource(key);
        if (protectedResourceDto != null) {
            resourcesApi.protectedResourcesByIdDelete(protectedResourceDto.getId());
        } else {
            log.warn("Unable to find the protected resource details for name : '" + key + "'");
        }
    }

    public void updateScopesOfRetrievedClient(ClientDto clientDto, List<String> scopesToAdd, List<String> scopesToRemove)
            throws ApiException {
        List<String> scopes = clientDto.getAllowedScopes();
        boolean changeDone = false;

        if (scopesToAdd != null && scopesToAdd.size() > 0) {
            scopes.addAll(scopesToAdd);
            changeDone = true;
        }

        if (scopesToRemove != null && scopesToRemove.size() > 0) {
            scopes.removeAll(scopesToRemove);
            changeDone = true;
        }

        if (changeDone) {
            clientDto.setAllowedScopes(scopes);
            clientsApi.clientsByIdPut(clientDto.getId(), clientDto);
        } else {
            log.debug("Scopes not updated in client " + clientDto.getClientName()
                    + "as no changes requested to remove or add scopes.");
        }
        return;
    }

    /**
     * This method returns the client information from the IS4 server, for the given name.
     * Current implementation uses the 'ApplicationName_ConsumerKey' as the client name(name).
     *
     * @param name the name of the OAuth client in IS4
     * @return {@link ClientDto} with the information of the IS4 client
     * @throws ApiException if any error happens.
     */

    private ClientDto getClientByName(String name) throws ApiException {
        ClientDtoRet clientDtoRet = clientsApi.clientsGet(name);

        if (clientDtoRet != null && clientDtoRet.size() > 0) {
            for (ClientDto clientDto : clientDtoRet) {
                if (clientDto.getClientName().endsWith(name)) {
                    return clientDto;
                }
            }
        }
        return null;
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
