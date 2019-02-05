package com.wso2.services.apim.extension;

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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.wso2.carbon.apimgt.api.APIManagementException;
import org.wso2.carbon.apimgt.api.model.*;
import org.wso2.carbon.apimgt.impl.factory.KeyManagerHolder;
import org.wso2.services.is4.ApiClient;
import org.wso2.services.is4.api.ClientsApi;
import org.wso2.services.is4.model.ClientDto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.*;

public class IdentityExpressAsKMImpl implements KeyManager {

    private static Log log = LogFactory.getLog(IdentityExpressAsKMImpl.class);

    private KeyManagerConfiguration keyManagerConfiguration;

    public AccessTokenRequest buildAccessTokenRequestFromJSON(String jsonInput, AccessTokenRequest tokenRequest)
            throws APIManagementException {

        if (jsonInput == null || jsonInput.isEmpty()) {
            log.debug("JsonInput is null or Empty.");
            return tokenRequest;
        }

        JSONParser parser = new JSONParser();
        JSONObject jsonObject;

        if (tokenRequest == null) {
            log.debug("Input request is null. Creating a new Request Object.");
            tokenRequest = new AccessTokenRequest();
        }

        try {
            jsonObject = (JSONObject) parser.parse(jsonInput);
            // Getting parameters from input string and setting in TokenRequest.
            if (jsonObject != null && !jsonObject.isEmpty()) {
                Map<String, Object> params = (Map<String, Object>) jsonObject;

                if (null != params.get(ApplicationConstants.OAUTH_CLIENT_ID)) {
                    tokenRequest.setClientId((String) params.get(ApplicationConstants.OAUTH_CLIENT_ID));
                }

                if (null != params.get(ApplicationConstants.OAUTH_CLIENT_SECRET)) {
                    tokenRequest.setClientSecret((String) params.get(ApplicationConstants.OAUTH_CLIENT_SECRET));
                }

                if (null != params.get(ApplicationConstants.VALIDITY_PERIOD)) {
                    tokenRequest.setValidityPeriod(
                            Long.parseLong((String) params.get(ApplicationConstants.VALIDITY_PERIOD)));
                }

                return tokenRequest;
            }
        } catch (ParseException e) {
            handleException("Error occurred while parsing JSON String", e);
        }
        return null;
    }

    /**
     * This method will accept json String and will do the json parse will set oAuth
     * application properties to OAuthApplicationInfo object.
     *
     * @param jsonInput
     *            this jsonInput will contain set of oAuth application properties.
     * @return OAuthApplicationInfo object will be return.
     * @throws APIManagementException
     */
    public OAuthApplicationInfo buildFromJSON(OAuthApplicationInfo oAuthApplicationInfo, String jsonInput)
            throws APIManagementException {
        // initiate json parser.
        JSONParser parser = new JSONParser();
        JSONObject jsonObject;

        try {
            // parse json String
            jsonObject = (JSONObject) parser.parse(jsonInput);
            if (jsonObject != null) {
                // create a map to hold json parsed objects.
                Map<String, Object> params = (Map) jsonObject;

                // set client Id
                if (params.get(Constants.JSON_CLIENT_ID) != null) {
                    oAuthApplicationInfo.setClientId((String) params.get(Constants.JSON_CLIENT_ID));
                }
                // set client secret
                if (params.get(Constants.JSON_CLIENT_SECRET) != null) {
                    oAuthApplicationInfo.setClientSecret((String) params.get(Constants.JSON_CLIENT_SECRET));
                }
                // copy all params map in to OAuthApplicationInfo's Map object.
                oAuthApplicationInfo.putAll(params);
                return oAuthApplicationInfo;
            }
        } catch (ParseException e) {
            handleException("Error occurred while parsing JSON String", e);
        }
        return null;
    }

    public AccessTokenRequest buildAccessTokenRequestFromOAuthApp(OAuthApplicationInfo oAuthApplication,
                                                                  AccessTokenRequest tokenRequest) throws APIManagementException {
            if (oAuthApplication == null) {
                return tokenRequest;
            }
            if (tokenRequest == null) {
                tokenRequest = new AccessTokenRequest();
            }

            if (oAuthApplication.getClientId() == null) {
                throw new APIManagementException("Consumer key is missing.");
            }

            tokenRequest.setClientId(oAuthApplication.getClientId().split("###")[1]);
            
            if (oAuthApplication.getParameter("tokenScope") != null) {
                String[] tokenScopes = (String[]) oAuthApplication.getParameter("tokenScope");
                tokenRequest.setScope(tokenScopes);
                oAuthApplication.addParameter("tokenScope", Arrays.toString(tokenScopes));
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

    protected void handleException(String msg, Exception e) throws APIManagementException {
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

    private String createJsonPayloadFromOauthApplication(OAuthApplicationInfo oAuthApplicationInfo)
            throws APIManagementException {

        Map<String, Object> paramMap = new HashMap<String, Object>();

        log.debug("Creating JSON with DCR Details -------- ");
        log.debug("Client Name : " + oAuthApplicationInfo.getClientName());
        log.debug(Constants.CLIENT_CONTACT_NAME + " : " + oAuthApplicationInfo.getParameter(Constants.CLIENT_CONTACT_NAME));
        log.debug(Constants.CLIENT_SCOPE + " : " + oAuthApplicationInfo.getParameter(Constants.CLIENT_SCOPE));
        log.debug(Constants.CLIENT_CONTACT_EMAIL + " : " + oAuthApplicationInfo.getParameter(Constants.CLIENT_CONTACT_EMAIL));

        // TODO Update this according to the IS4 requirements
		/*if (oAuthApplicationInfo.getClientName() == null
				|| oAuthApplicationInfo.getParameter(Constants.CLIENT_CONTACT_NAME) == null
				|| oAuthApplicationInfo.getParameter(Constants.CLIENT_SCOPE) == null
				|| oAuthApplicationInfo.getParameter(Constants.CLIENT_CONTACT_EMAIL) == null) {
			throw new APIManagementException("Mandatory parameters missing");
		}*/

        paramMap.put(Constants.CLIENT_NAME, oAuthApplicationInfo.getClientName());

        JSONArray scopes = (JSONArray) oAuthApplicationInfo.getParameter(Constants.CLIENT_SCOPE);
        paramMap.put("scopes", scopes);

        paramMap.put(Constants.CLIENT_CONTACT_NAME, oAuthApplicationInfo.getParameter(Constants.CLIENT_CONTACT_NAME));
        paramMap.put(Constants.CLIENT_CONTACT_EMAIL, oAuthApplicationInfo.getParameter(Constants.CLIENT_CONTACT_EMAIL));
        if (oAuthApplicationInfo.getParameter("id") != null) {
            paramMap.put("id", oAuthApplicationInfo.getParameter("id"));
        }

        return JSONObject.toJSONString(paramMap);
    }

    private OAuthApplicationInfo createOAuthAppfromResponse(Map responseMap) {

        // Sample response returned by client registration endpoint.
        // {"id":305,"creationDate":1430486098086,"modificationDate":1430486098086,"name":"TestClient_2",
        // "clientId":"testclient_2","secret":"3b4dbfb6-0ad9-403e-8ed6-715459fc8c78",
        // "description":null,"contactName":"John Doe","contactEmail":"john@doe.com",
        // "scopes":["scope1"],"attributes":{},"thumbNailUrl":null,"redirectUris":[],
        // "skipConsent":false,"includePrincipal":false,"expireDuration":0,"useRefreshTokens":false,
        // "allowedImplicitGrant":false,"allowedClientCredentials":false}

        OAuthApplicationInfo info = new OAuthApplicationInfo();
        Object clientId = responseMap.get(Constants.CLIENT_ID);
        info.setClientId((String) clientId);

        Object clientSecret = responseMap.get(Constants.CLIENT_SECRET);
        info.setClientSecret((String) clientSecret);

        Object id = responseMap.get("id");
        info.addParameter("id", id);

        Object contactName = responseMap.get(Constants.CLIENT_CONTACT_NAME);
        if (contactName != null) {
            info.addParameter("contactName", contactName);
        }

        Object contactMail = responseMap.get(Constants.CLIENT_CONTACT_EMAIL);
        if (contactMail != null) {
            info.addParameter("contactMail", contactMail);
        }

        Object scopes = responseMap.get(Constants.CLIENT_SCOPE);
        if (scopes != null) {
            info.addParameter("scopes", scopes);
        }

        return info;
    }

    public OAuthApplicationInfo buildFromJSON(String json) throws APIManagementException {
        return null;
    }

    public OAuthApplicationInfo createApplication(OAuthAppRequest oauthAppRequest) throws APIManagementException {
        // Creating DCR Application
        OAuthApplicationInfo oAuthApplicationInfo = oauthAppRequest.getOAuthApplicationInfo();

        log.debug("Creating a new OAuthApp in Authorization Server...");

        try {        
            IS4AdminAPIClient adminAPIClient = new IS4AdminAPIClient();
            
            // Create API request
            ClientDto dto = adminAPIClient
                    .addClient(oAuthApplicationInfo.getClientName(), oAuthApplicationInfo.getCallBackURL());

            oAuthApplicationInfo.setClientId(dto.getId() + "###" + dto.getClientId());
            log.debug("BE createApplication success");
            return oAuthApplicationInfo;
        } catch (Throwable e) {
            log.error("Error in connection to Backend API in createApplication", e);
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
        String registrationToken = config.getParameter(Constants.REGISTRAION_ACCESS_TOKEN);

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
        } catch (Exception e) {
            log.error("Error in connection to Backend API in updateApplication", e);
        }
        return null;
    }

    public void deleteApplication(String consumerKey) throws APIManagementException {
        log.debug("Deleting a OAuth Client (" + consumerKey + ") in Authorization Server..");

        try {
            IS4AdminAPIClient adminAPIClient = new IS4AdminAPIClient();
            adminAPIClient.deleteClientByClientId(consumerKey.split("###")[0]);
            log.debug("BE deleteApplication success : " + consumerKey);
        } catch (Exception e) {
            log.error("Error in connection to Backend API in deleteApplication", e);
        }
    }

    public OAuthApplicationInfo retrieveApplication(String consumerKey) throws APIManagementException {
        log.debug("Retriving OAuth Client (" + consumerKey + ") in Authorization Server..");
        try {

            IS4AdminAPIClient adminAPIClient = new IS4AdminAPIClient();
            ClientDto clientDto = adminAPIClient.getClientById(consumerKey.split("###")[0]);

            OAuthApplicationInfo oAuthApplicationInfo = new OAuthApplicationInfo();
            oAuthApplicationInfo.setClientId(clientDto.getClientId());
            oAuthApplicationInfo.setClientName(clientDto.getClientName());
            oAuthApplicationInfo.setClientSecret("hidden-secret");
            oAuthApplicationInfo.setCallBackURL(clientDto.getRedirectUris().get(0));

            log.debug("retrieveApplication success for " + consumerKey);
            return oAuthApplicationInfo;
        } catch (Exception e) {
            log.error("Error in connection to Backend API in updateApplication", e);
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

    public AccessTokenInfo getNewApplicationAccessToken(AccessTokenRequest accessTokenRequest) throws APIManagementException {
        log.info("WARNING : request to getNewApplicationAccessToken > " + accessTokenRequest);
        return null;
    }

    public String getNewApplicationConsumerSecret(AccessTokenRequest accessTokenRequest) throws APIManagementException {
        log.info("WARNING : request to getNewApplicationConsumerSecret > " + accessTokenRequest);
        return null;
    }

    public Map getResourceByApiId(String APIId) throws APIManagementException {
        log.info("WARNING : request to getResourceByApiId > " + APIId);
        return null;
    }

    public Map<String, Set<Scope>> getScopesForAPIS(String apis) throws APIManagementException {
        log.info("WARNING : request to getScopesForAPIS > " + apis);
        return null;
    }

    public AccessTokenInfo getTokenMetaData(String accessToken) throws APIManagementException {
        AccessTokenInfo tokenInfo = new AccessTokenInfo();

        KeyManagerConfiguration config = KeyManagerHolder.getKeyManagerInstance().getKeyManagerConfiguration();

        String introspectionURL = config.getParameter(Constants.INTROSPECTION_URL);
        String introspectionConsumerKey = config.getParameter(Constants.INTROSPECTION_CK);
        String introspectionConsumerSecret = config.getParameter(Constants.INTROSPECTION_CS);
        String encodedSecret = new String(Base64.getEncoder()
                .encode(new String(introspectionConsumerKey + ":" + introspectionConsumerSecret).getBytes()));

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
            reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));

            if (HttpStatus.SC_OK == responseCode) {
                // pass bufferReader object and get read it and retrieve the parsedJson object
                parsedObject = getParsedObjectByReader(reader);
                if (parsedObject != null) {

                    Map valueMap = parsedObject;
                    Object principal = valueMap.get("principal");

                    if (principal == null) {
                        tokenInfo.setTokenValid(false);
                        return tokenInfo;
                    }
                    Map principalMap = (Map) principal;
                    String clientId = (String) principalMap.get("name");
                    Long expiryTimeString = (Long) valueMap.get("expires_in");

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
                        JSONArray scopesArray = (JSONArray) valueMap.get("scopes");

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
    }

    public OAuthApplicationInfo mapOAuthApplication(OAuthAppRequest oAuthAppRequest) throws APIManagementException {
        OAuthApplicationInfo oAuthApplicationInfo = oAuthAppRequest.getOAuthApplicationInfo();
        return oAuthApplicationInfo;
    }

    public boolean registerNewResource(API api, Map data) throws APIManagementException {
        log.info("WARNING : request to register new resource > " + api.getId() + " : " + data);
        return true;
    }

    public boolean updateRegisteredResource(API api, Map data) throws APIManagementException {
        log.info("WARNING : request to unregister new resource > " + api.getId() + " : " + data);
        return true;
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

            log.debug("Retriving Client From BE successful");
            return clientDto;
        } catch (Exception e) {
            log.error("Error in connection to Backend API in getClientFromBE", e);
        }
        return null;
    }

}
