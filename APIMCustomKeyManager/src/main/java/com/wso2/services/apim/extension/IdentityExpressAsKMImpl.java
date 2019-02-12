package com.wso2.services.apim.extension;

import com.squareup.okhttp.*;
import com.wso2.services.apim.extension.exception.TokenAPIException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.solr.common.StringUtils;
import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.wso2.carbon.apimgt.api.APIManagementException;
import org.wso2.carbon.apimgt.api.model.*;
import org.wso2.carbon.apimgt.impl.AbstractKeyManager;
import org.wso2.carbon.apimgt.impl.factory.KeyManagerHolder;
import org.wso2.services.is4.ApiClient;
import org.wso2.services.is4.ApiException;
import org.wso2.services.is4.api.ClientsApi;
import org.wso2.services.is4.model.ClientDto;
import org.wso2.services.is4.model.ProtectedResourceDto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class IdentityExpressAsKMImpl extends AbstractKeyManager {
    public static final String DEFAULT_SCOPE = "wso2_apim_default_scope";

    private static Log log = LogFactory.getLog(IdentityExpressAsKMImpl.class);
    private static final String ERR_MESSAGE = " Error occurred during operation.";

    private KeyManagerConfiguration keyManagerConfiguration;
    private IS4AdminAPIClient is4AdminAPIClient;
    private APIMClient apimClient;
    private IS4TokenAPIClient tokenAPIClient;

    public IdentityExpressAsKMImpl() {
        is4AdminAPIClient = new IS4AdminAPIClient();
        apimClient = new APIMClient();
        tokenAPIClient = new IS4TokenAPIClient();
    }

    public AccessTokenRequest buildAccessTokenRequestFromOAuthApp(OAuthApplicationInfo oAuthApplication,
                                                                  AccessTokenRequest tokenRequest) throws APIManagementException {
        if (oAuthApplication == null) {
            return tokenRequest;
        }
        if (tokenRequest == null) {
            tokenRequest = new AccessTokenRequest();
        }

        if (oAuthApplication.getClientId() == null || oAuthApplication.getClientSecret() == null) {
            throw new APIManagementException("Consumer key or Consumer Secret missing.");
        }
        tokenRequest.setClientId(oAuthApplication.getClientId());
        tokenRequest.setClientSecret(oAuthApplication.getClientSecret());

        Object tokenScopeObj = oAuthApplication.getParameter("tokenScope");
        if (tokenScopeObj != null) {
            if (tokenScopeObj instanceof String[]) {
                String[] tokenScopeArr = (String[]) tokenScopeObj;
                //change APIM default scope to is4 default scope
                for (int i = 0; i < tokenScopeArr.length; i++) {
                    if (Constants.APIM_TOKEN_SCOPE_DEFAULT.equals(tokenScopeArr[i])) {
                        tokenScopeArr[i] = Constants.IS4_TOKEN_SCOPE_DEFAULT;
                    }
                }
                tokenRequest.setScope((String[]) tokenScopeObj);
            } else if (tokenScopeObj instanceof String) {
                String tokenScope = (String) tokenScopeObj;
                //change APIM default scope to is4 default scope
                if (Constants.APIM_TOKEN_SCOPE_DEFAULT.equals(tokenScope)) {
                    tokenScope = Constants.IS4_TOKEN_SCOPE_DEFAULT;
                }
                String[] tokenScopes = new String[1];
                tokenScopes[0] = tokenScope;
                tokenRequest.setScope(tokenScopes);
            }
        } else {
            String[] tokenScopes = new String[1];
            tokenScopes[0] = Constants.IS4_TOKEN_SCOPE_DEFAULT;
            ;
            tokenRequest.setScope(tokenScopes);
        }

        if (oAuthApplication.getParameter(ApplicationConstants.VALIDITY_PERIOD) != null) {
            tokenRequest.setValidityPeriod(Long.parseLong((String) oAuthApplication.getParameter(ApplicationConstants
                    .VALIDITY_PERIOD)));
        }

        return tokenRequest;
    }

    protected void handleException(String msg) throws APIManagementException {
        log.error(msg);
        throw new APIManagementException(msg);
    }

    protected void handleException(String msg, Throwable e) throws APIManagementException {
        log.error(msg, e);
        throw new APIManagementException(msg, e);
    }

    private CloseableHttpClient getCloseableHttpClient() {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        return httpClient;
    }

    private JSONObject getParsedObjectByReader(BufferedReader reader) throws ParseException, IOException {
        JSONObject parsedObject = null;
        JSONParser parser = new JSONParser();
        if (reader != null) {
            parsedObject = (JSONObject) parser.parse(reader);
        }
        return parsedObject;
    }


    public OAuthApplicationInfo buildFromJSON(String json) throws APIManagementException {
        return null;
    }

    public OAuthApplicationInfo createApplication(OAuthAppRequest oauthAppRequest) throws APIManagementException {
        // Creating DCR Application
        String logPrefix = "[Create OAuth App:" + oauthAppRequest.getOAuthApplicationInfo().getClientName() + "] ";

        OAuthApplicationInfo oAuthApplicationInfo = oauthAppRequest.getOAuthApplicationInfo();
        log.debug(logPrefix + "Started");
        try {
            String appName = oAuthApplicationInfo.getClientName();
            //todo: check with application  sharing
            String appOwner = (String) oAuthApplicationInfo.getParameter("username");

            List<String> subscribedAPIIds = apimClient.getSubscribedAPIIds(appOwner, appName);
            List<String> scopes = is4AdminAPIClient.getScopeList(subscribedAPIIds);

            if (scopes.isEmpty()) {
                // This is an error condition in IS4 where it cannot issue an token for an empty scope.
                // Check whether the default scope exists in IS4
                List<String> existingScopes = is4AdminAPIClient.getScopeList(Collections.singletonList(DEFAULT_SCOPE));
                if (existingScopes.isEmpty() || !existingScopes.contains(DEFAULT_SCOPE)) {
                    is4AdminAPIClient.addProtectedResource(DEFAULT_SCOPE, DEFAULT_SCOPE, new String[]{DEFAULT_SCOPE});
                }
                scopes.add(DEFAULT_SCOPE);
            }

            // Create API request
            ClientDto dto = is4AdminAPIClient
                    .addClient(appName, oAuthApplicationInfo.getCallBackURL());

            //todo: set grant types from app request
            dto.setAllowedGrantTypes(new ArrayList<String>() {{
                add("client_credentials");
            }});

            dto.setAllowedScopes(scopes);
            is4AdminAPIClient.updateClientById(dto.getId(), dto);

            ClientDto updatedDto = is4AdminAPIClient.getClientById(dto.getId());
            MappingUtil.setSecrets(dto, updatedDto);

            oAuthApplicationInfo = MappingUtil.getOAuthAppInfoFromIS4Client(updatedDto, oAuthApplicationInfo);
            log.debug(logPrefix + "Completed");
            return oAuthApplicationInfo;
        } catch (LinkageError e) { //can execute when dependencies are not added properly
            handleException(logPrefix + ERR_MESSAGE + " Please make sure "
                    + "correct dependencies are added to the runtime", e);
        } catch (ApiException e) {
            handleException(logPrefix + ERR_MESSAGE, e);
        }
        return null;
    }

    public OAuthApplicationInfo updateApplication(OAuthAppRequest oauthAppRequest) throws APIManagementException {
        log.debug("Updating OAuth Client.." + oauthAppRequest.getOAuthApplicationInfo().getClientId());

        OAuthApplicationInfo oAuthApplicationInfo = oauthAppRequest.getOAuthApplicationInfo();

        // We have to send the id with the update request.
        String consumerKey = oAuthApplicationInfo.getClientId();

        if (consumerKey == null || consumerKey.isEmpty()) {
            return oauthAppRequest.getOAuthApplicationInfo();
        }

        KeyManagerConfiguration config = KeyManagerHolder.getKeyManagerInstance().getKeyManagerConfiguration();

        // Getting Client Registration url and Access Token from config.
        String registrationEndpoint = config.getParameter(Constants.CLIENT_REG_ENDPOINT);
//        String registrationToken = config.getParameter(Constants.REGISTRAION_ACCESS_TOKEN);

        try {
            // Generate UUIDs as Client Id and Secret
//            oAuthApplicationInfo.setClientId(generateUUID());
//            oAuthApplicationInfo.setClientSecret(generateUUID());
            // Create API request
            ApiClient apiClient = new ApiClient();
            apiClient.setConnectTimeout(10000);
            apiClient.setReadTimeout(10000);
            apiClient.setWriteTimeout(10000);
            apiClient.setBasePath(registrationEndpoint);

            ClientsApi clientsApi = new ClientsApi(apiClient);

            // Get the client object from back end
            ClientDto clientDto = getClientFromBE(consumerKey);
            // update the values
            clientDto.setClientName(oAuthApplicationInfo.getClientName());
            // Call the back end
            clientsApi.clientsByIdPut(consumerKey, clientDto);
            log.debug("BE updateApplication success");
            return oAuthApplicationInfo;
        } catch (ApiException e) {
            log.error("Error in connection to Backend API in updateApplication", e);
        }
        return null;
    }

    public void deleteApplication(String consumerKey) throws APIManagementException {
        String logPrefix = "[Deleting App:" + consumerKey + "] ";
        log.debug(logPrefix + "Started");
        try {
            is4AdminAPIClient.deleteClientByConsumerKey(consumerKey);
            log.debug(logPrefix + "Completed");
        } catch (ApiException e) {
            handleException(logPrefix + ERR_MESSAGE, e);
        }
    }

    public OAuthApplicationInfo retrieveApplication(String consumerKey) throws APIManagementException {
        String logPrefix = "[Retrieving App:" + consumerKey + "] ";
        log.debug(logPrefix + " Started");
        try {
//            String is4AppId = apimClient.getIS4AppIdFromConsumerKey(consumerKey);
            ClientDto clientDto = is4AdminAPIClient.getClientByConsumerKey(consumerKey);
            OAuthApplicationInfo oAuthApplicationInfo = MappingUtil.getOAuthAppInfoFromIS4Client(clientDto);
            log.debug(logPrefix + " Completed");
            return oAuthApplicationInfo;
        } catch (ApiException e) {
            handleException(logPrefix + ERR_MESSAGE, e);
        }
        return null;
    }

    public void deleteMappedApplication(String applicationKey) throws APIManagementException {
        log.info("WARNING : request to deleteMappedApplication > " + applicationKey);
    }

    public void deleteRegisteredResourceByAPIId(String APIId) throws APIManagementException {
        log.info("WARNING : request to deleteRegisteredResourceByAPIId > " + APIId);
    }

    public AccessTokenInfo getAccessTokenByConsumerKey(String consumerKey) throws APIManagementException {
        // TODO: This is required
        log.info("WARNING : request to getAccessTokenByConsumerKey > " + consumerKey);
        return null;
    }

    public Set<String> getActiveTokensByConsumerKey(String consumerKey) throws APIManagementException {
        log.info("WARNING : request to getActiveTokensByConsumerKey > " + consumerKey);
        return null;
    }

    public KeyManagerConfiguration getKeyManagerConfiguration() throws APIManagementException {
        log.debug("request to getKeyManagerConfiguration");
        return keyManagerConfiguration;
    }

    public AccessTokenInfo getNewApplicationAccessToken(AccessTokenRequest accessTokenRequest)
            throws APIManagementException {
        String logPrefix = "[Getting new access token for App:" + accessTokenRequest.getClientId() + "] ";
        log.debug(logPrefix + "Started");
        AccessTokenInfo tokenInfo = new AccessTokenInfo();
        try {
            JSONObject tokenResponse = tokenAPIClient
                    .getNewAccessTokenWithClientCredentials(accessTokenRequest.getClientId(),
                            accessTokenRequest.getClientSecret());
            String accessToken = tokenResponse.get(Constants.OAUTH_RESPONSE_ACCESSTOKEN).toString();
            long validityPeriod = Long.parseLong(tokenResponse.get(Constants.OAUTH_RESPONSE_EXPIRY_TIME).toString());
            if (tokenResponse.get("scope") != null) {
                tokenInfo.setScope(((String) tokenResponse.get("scope")).split(" "));
            }
            tokenInfo.setAccessToken(accessToken);
            tokenInfo.setValidityPeriod(validityPeriod);
            tokenInfo.setConsumerKey(accessTokenRequest.getClientId());
            tokenInfo.setTokenValid(true);
        } catch (TokenAPIException e) {
            handleException("Error while parsing response from token api", e);
        }
        log.debug(logPrefix + "Completed");
        return tokenInfo;
    }

    public String getNewApplicationConsumerSecret(AccessTokenRequest accessTokenRequest) throws APIManagementException {
        log.info("WARNING : request to getNewApplicationConsumerSecret > " + accessTokenRequest);
        return null;
    }

    public Map getResourceByApiId(String apiId) throws APIManagementException {
        String logPrefix = "[Retrieving protected resource for api:" + apiId + "] ";
        log.debug(logPrefix + "Started");
        try {
            ProtectedResourceDto resourceDto = is4AdminAPIClient.getProtectedResource(apiId);
            if (resourceDto != null) {
                log.debug(logPrefix + "Found.");
                Map<String, String> resourceMap = new HashMap<>();
                resourceMap.put("resourceId", resourceDto.getId());
                return resourceMap;
            }
        } catch (ApiException e) {
            handleException(logPrefix + "Error while getting resource " + apiId, e);
        }
        log.debug(logPrefix + "Not found.");
        log.debug(logPrefix + "Completed.");
        return null;
    }

    public boolean registerNewResource(API api, Map data) throws APIManagementException {
        String logPrefix = "[Registering a new protected resource for api:" + api.getId().toString() + "] ";
        log.debug(logPrefix + "Started");
        String[] scopes = getScopeArray(api);
        try {
            is4AdminAPIClient.addProtectedResource(api.getId().toString(), api.getId().toString(), scopes);
        } catch (ApiException e) {
            handleException(logPrefix + "Error while registering resource " + api.getId().toString() + " with scopes.",
                    e);
        }
        log.debug(logPrefix + "Completed");
        return true;
    }

    public boolean updateRegisteredResource(API api, Map data) throws APIManagementException {
        String logPrefix = "[Updating protected resource for api with scopes:" + api.getId().toString() + "] ";
        log.debug(logPrefix + "Started");
        try {
            is4AdminAPIClient.updateProtectedResourceWithScopes(api.getId().toString(), getScopeArray(api));
        } catch (ApiException e) {
            handleException(logPrefix + "Error while updating resource " + api.getId().toString(), e);
        }
        log.debug(logPrefix + "Completed");
        return true;
    }

    public Map<String, Set<Scope>> getScopesForAPIS(String apis) throws APIManagementException {
        log.warn("WARNING : request to getScopesForAPIS > " + apis);
        return null;
    }

    public AccessTokenInfo getTokenMetaData(String accessToken) throws APIManagementException {
        AccessTokenInfo tokenInfo = new AccessTokenInfo();

        KeyManagerConfiguration config = KeyManagerHolder.getKeyManagerInstance().getKeyManagerConfiguration();

        String introspectionURL = config.getParameter(Constants.INTROSPECTION_URL);
        String introspectionConsumerKey = config.getParameter(Constants.INTROSPECTION_CK);
        String introspectionConsumerSecret = config.getParameter(Constants.INTROSPECTION_CS);
        String encodedSecret = new String(Base64.getEncoder()
                .encode((introspectionConsumerKey + ":" + introspectionConsumerSecret)
                        .getBytes(Charset.defaultCharset())), Charset.defaultCharset());

        BufferedReader reader = null;
        CloseableHttpClient client = getCloseableHttpClient();

        try {
            URIBuilder uriBuilder = new URIBuilder(introspectionURL);
            uriBuilder.addParameter("access_token", accessToken);
            uriBuilder.build();

            HttpGet httpGet = new HttpGet(uriBuilder.build());

            httpGet.setHeader("Authorization", "Basic " + encodedSecret);
            HttpResponse response = client.execute(httpGet);
            int responseCode = response.getStatusLine().getStatusCode();

            if (log.isDebugEnabled()) {
                log.debug("HTTP Response code : " + responseCode);
            }

            // {"audience":"MappedClient","scopes":["test"],"principal":{"name":"mappedclient","roles":[],"groups":[],"adminPrincipal":false,
            // "attributes":{}},"expires_in":1433059160531}
            HttpEntity entity = response.getEntity();
            JSONObject parsedObject;
            reader = new BufferedReader(new InputStreamReader(entity.getContent(), StandardCharsets.UTF_8));

            if (HttpStatus.SC_OK == responseCode) {
                // pass bufferReader object and get read it and retrieve the parsedJson object
                parsedObject = getParsedObjectByReader(reader);
                if (parsedObject != null) {

                    Object principal = ((Map) parsedObject).get("principal");

                    if (principal == null) {
                        tokenInfo.setTokenValid(false);
                        return tokenInfo;
                    }
                    Map principalMap = (Map) principal;
                    String clientId = (String) principalMap.get("name");
                    Long expiryTimeString = (Long) ((Map) parsedObject).get("expires_in");

                    // Returning false if mandatory attributes are missing.
                    if (clientId == null || expiryTimeString == null) {
                        tokenInfo.setTokenValid(false);
                        tokenInfo.setErrorcode(Constants.API_AUTH_ACCESS_TOKEN_EXPIRED);
                        return tokenInfo;
                    }

                    long currentTime = System.currentTimeMillis();
                    long expiryTime = expiryTimeString;
                    if (expiryTime > currentTime) {
                        tokenInfo.setTokenValid(true);
                        tokenInfo.setConsumerKey(clientId);
                        tokenInfo.setValidityPeriod(expiryTime - currentTime);
                        // Considering Current Time as the issued time.
                        tokenInfo.setIssuedTime(currentTime);
                        JSONArray scopesArray = (JSONArray) ((Map) parsedObject).get("scopes");

                        if (scopesArray != null && !scopesArray.isEmpty()) {

                            String[] scopes = new String[scopesArray.size()];
                            for (int i = 0; i < scopes.length; i++) {
                                scopes[i] = (String) scopesArray.get(i);
                            }
                            tokenInfo.setScope(scopes);
                        }
                    } else {
                        tokenInfo.setTokenValid(false);
                        tokenInfo.setErrorcode(Constants.API_AUTH_ACCESS_TOKEN_INACTIVE);
                        return tokenInfo;
                    }

                } else {
                    log.error("Invalid Token " + accessToken);
                    tokenInfo.setTokenValid(false);
                    tokenInfo.setErrorcode(Constants.API_AUTH_ACCESS_TOKEN_INACTIVE);
                    return tokenInfo;
                }
            } // for other HTTP error codes we just pass generic message.
            else {
                log.error("Invalid Token " + accessToken);
                tokenInfo.setTokenValid(false);
                tokenInfo.setErrorcode(Constants.API_AUTH_ACCESS_TOKEN_INACTIVE);
                return tokenInfo;
            }

        } catch (UnsupportedEncodingException e) {
            handleException("The Character Encoding is not supported. " + e.getMessage(), e);
        } catch (ClientProtocolException e) {
            handleException(
                    "HTTP request error has occurred while sending request  to OAuth Provider. " + e.getMessage(), e);
        } catch (IOException e) {
            handleException("Error has occurred while reading or closing buffer reader. " + e.getMessage(), e);
        } catch (URISyntaxException e) {
            handleException("Error occurred while building URL with params." + e.getMessage(), e);
        } catch (ParseException e) {
            handleException("Error while parsing response json " + e.getMessage(), e);
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                log.warn("Error while closing the HttpClient");
            }
            IOUtils.closeQuietly(reader);
        }

        return tokenInfo;
    }

    public void loadConfiguration(KeyManagerConfiguration keyManagerConfiguration) throws APIManagementException {
        log.debug("loadConfiguration executed with : " + keyManagerConfiguration);
        this.keyManagerConfiguration = keyManagerConfiguration;

        String tokenAPIUrl = keyManagerConfiguration.getParameter(Constants.IS4_IS4_TOKEN_API_URL);
        if (!StringUtils.isEmpty(tokenAPIUrl)) {
            tokenAPIClient.setTokenAPIUrl(tokenAPIUrl);
        }

        is4AdminAPIClient.init(
                tokenAPIClient,
                keyManagerConfiguration.getParameter(Constants.IS4_CLIENT_ID),
                keyManagerConfiguration.getParameter(Constants.IS4_CLIENT_SECRET),
                keyManagerConfiguration.getParameter(Constants.IS4_USERNAME),
                keyManagerConfiguration.getParameter(Constants.IS4_PASSWORD)
        );

        String adminAPIUrl = keyManagerConfiguration.getParameter(Constants.IS4_ADMIN_API_BASE_PATH);

        if (!StringUtils.isEmpty(adminAPIUrl)) {
            is4AdminAPIClient.setBasePath(adminAPIUrl);
        }
    }

    public OAuthApplicationInfo mapOAuthApplication(OAuthAppRequest oAuthAppRequest) throws APIManagementException {
        return oAuthAppRequest.getOAuthApplicationInfo();
    }

    private String generateUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    private ClientDto getClientFromBE(String id) {
        try {

            KeyManagerConfiguration config = KeyManagerHolder.getKeyManagerInstance().getKeyManagerConfiguration();

            // Getting Client Registration Url and Access Token from Config.
            String registrationEndpoint = config.getParameter(Constants.CLIENT_REG_ENDPOINT);

            ClientDto clientDto = new ClientDto();

            // Create API request
            ApiClient apiClient = new ApiClient();
            apiClient.setConnectTimeout(10000);
            apiClient.setReadTimeout(10000);
            apiClient.setWriteTimeout(10000);
            apiClient.setBasePath(registrationEndpoint);

            ClientsApi clientsApi = new ClientsApi(apiClient);

            // Call the backend
            clientsApi.clientsByIdGet(id);

            // TODO update the client dto object

            log.debug("Retrieving Client From BE successful");
            return clientDto;
        } catch (Exception e) {
            log.error("Error in connection to Backend API in getClientFromBE", e);
        }
        return null;
    }

    private String[] getScopeArray(API api) {
        String[] scopes = null;
        if (api.getScopes() != null && api.getScopes().size() > 0) {
            scopes = new String[api.getScopes().size()];
            int i = 0;
            for (Scope s : api.getScopes()) {
                scopes[i++] = s.getName();
            }
        }
        return scopes;
    }
}
