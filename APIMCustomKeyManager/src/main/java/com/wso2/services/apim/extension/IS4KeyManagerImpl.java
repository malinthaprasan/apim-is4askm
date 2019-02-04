package com.wso2.services.apim.extension;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.wso2.carbon.apimgt.api.APIManagementException;
import org.wso2.carbon.apimgt.api.model.*;
import org.wso2.carbon.apimgt.impl.factory.KeyManagerHolder;

import org.wso2.services.is4.api.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.*;

public class IS4KeyManagerImpl implements KeyManager {

    private static Log log = LogFactory.getLog(IS4KeyManagerImpl.class);

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

        if (oAuthApplication.getClientId() == null || oAuthApplication.getClientSecret() == null) {
            throw new APIManagementException("Consumer key or Consumer Secret missing.");
        }
        tokenRequest.setClientId(oAuthApplication.getClientId());
        tokenRequest.setClientSecret(oAuthApplication.getClientSecret());

        if (oAuthApplication.getParameter("tokenScope") != null) {
            String[] tokenScopes = (String[]) oAuthApplication.getParameter("tokenScope");
            tokenRequest.setScope(tokenScopes);
            oAuthApplication.addParameter("tokenScope", Arrays.toString(tokenScopes));
        }

        if (oAuthApplication.getParameter(ApplicationConstants.VALIDITY_PERIOD) != null) {
            tokenRequest.setValidityPeriod(
                    Long.parseLong((String) oAuthApplication.getParameter(ApplicationConstants.VALIDITY_PERIOD)));
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

        ClientsApi clientsApi = new org.wso2.services.is4.api.ClientsApi();
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

        log.debug("Creating a new oAuthApp in Authorization Server...");

        KeyManagerConfiguration config = KeyManagerHolder.getKeyManagerInstance().getKeyManagerConfiguration();

        // Getting Client Registration Url and Access Token from Config.
        String registrationEndpoint = config.getParameter(Constants.CLIENT_REG_ENDPOINT);
        String registrationToken = config.getParameter(Constants.REGISTRAION_ACCESS_TOKEN);

        HttpPut httpPut = new HttpPut(registrationEndpoint.trim());

        CloseableHttpClient httpClient = getCloseableHttpClient();

        BufferedReader reader = null;
        try {
            // Create the JSON Payload that should be sent to OAuth Server.
            String jsonPayload = createJsonPayloadFromOauthApplication(oAuthApplicationInfo);

            log.debug("Payload for creating new client : " + jsonPayload);

            httpPut.setEntity(new StringEntity(jsonPayload, Constants.UTF_8));
            httpPut.setHeader(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON_CONTENT_TYPE);

            // Setting Authorization Header, with Access Token
            httpPut.setHeader(Constants.AUTHORIZATION, Constants.BEARER + registrationToken);

            HttpResponse response = httpClient.execute(httpPut);
            int responseCode = response.getStatusLine().getStatusCode();

            JSONObject parsedObject;
            HttpEntity entity = response.getEntity();
            reader = new BufferedReader(new InputStreamReader(entity.getContent(), Constants.UTF_8));

            // If successful a 201 will be returned.
            if (HttpStatus.SC_CREATED == responseCode) {

                parsedObject = getParsedObjectByReader(reader);
                if (parsedObject != null) {
                    oAuthApplicationInfo = createOAuthAppfromResponse(parsedObject);

                    //TODO check what we can do for this
                    // We need the id when retrieving a single OAuth Client. So we have to maintain
                    // a mapping
                    // between the consumer key and the ID.
                    //nameIdMapping.put(oAuthApplicationInfo.getClientId(),
                    //		(Long) oAuthApplicationInfo.getParameter("id"));

                    return oAuthApplicationInfo;
                }
            } else {
                handleException("Some thing wrong here while registering the new client "
                        + "HTTP Error response code is " + responseCode);
            }

        } catch (UnsupportedEncodingException e) {
            handleException("Encoding for the Response not-supported.", e);
        } catch (ParseException e) {
            handleException("Error while parsing response json", e);
        } catch (IOException e) {
            handleException("Error while reading response body ", e);
        } finally {
            // close buffer reader.
            if (reader != null) {
                IOUtils.closeQuietly(reader);
            }
            try {
                httpClient.close();
            } catch (IOException e) {
                log.warn("Error while closing the HttpClient");
            }
        }
        return null;
    }

    public OAuthApplicationInfo updateApplication(OAuthAppRequest oauthAppRequest) throws APIManagementException {
        log.debug("Updating OAuth Client.." + oauthAppRequest.getOAuthApplicationInfo().getClientId());

        // We have to send the id with the update request.
        String consumerKey = oauthAppRequest.getOAuthApplicationInfo().getClientId();

        //TODO Long id = nameIdMapping.get(consumerKey);

        if (consumerKey == null || consumerKey.isEmpty()) {
            return oauthAppRequest.getOAuthApplicationInfo();
        }

        keyManagerConfiguration.getParameter(Constants.CLIENT_REG_ENDPOINT);
/*
        String registrationUrl = configuration.getParameter(Constants.CLIENT_REG_ENDPOINT);
        String accessToken = configuration.getParameter(Constants.REGISTRAION_ACCESS_TOKEN);
        BufferedReader reader = null;
        //oauthAppRequest.getOAuthApplicationInfo().addParameter("id", id);

        //registrationUrl += "/" + id.toString();
        registrationUrl += "/" + consumerKey;
        
        CloseableHttpClient client = getCloseableHttpClient();
        try {
            String jsonPayload = createJsonPayloadFromOauthApplication(oauthAppRequest.getOAuthApplicationInfo());

            log.debug("JSON Payload for update method : " + jsonPayload);

            HttpPost httpPost = new HttpPost(registrationUrl);
            httpPost.setEntity(new StringEntity(jsonPayload, "UTF8"));
            httpPost.setHeader(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON_CONTENT_TYPE);
            httpPost.setHeader(Constants.AUTHORIZATION, Constants.BEARER + accessToken);
            HttpResponse response = client.execute(httpPost);

            int responseCode = response.getStatusLine().getStatusCode();

            log.debug("Response Code from Server: " + responseCode);

            JSONObject parsedObject;

            HttpEntity entity = response.getEntity();
            reader = new BufferedReader(new InputStreamReader(entity.getContent(), Constants.UTF_8));

            if (responseCode == HttpStatus.SC_CREATED || responseCode == HttpStatus.SC_OK) {
                parsedObject = getParsedObjectByReader(reader);
                if (parsedObject != null) {
                    return createOAuthAppfromResponse(parsedObject);
                } else {
                    handleException("ParseObject is empty. Can not return oAuthApplicationInfo.");
                }
            } else {
                handleException("Some thing wrong here when updating the Client for key." + oauthAppRequest
                        .getOAuthApplicationInfo().getClientId() + ". Error " + "code" + responseCode);
            }

        } catch (UnsupportedEncodingException e) {
            handleException("Some thing wrong here when Updating a Client for key " + oauthAppRequest
                    .getOAuthApplicationInfo().getClientId(), e);
        } catch (ParseException e) {
            handleException("Error while parsing response json", e);
        } catch (IOException e) {
            handleException("Error while reading response body from Server ", e);
        } finally {
            if (reader != null) {
                IOUtils.closeQuietly(reader);
            }
            client.close();
        }*/
        return null;
    }

    public void deleteApplication(String consumerKey) throws APIManagementException {
        log.debug("Deleting a OAuth Client (" + consumerKey + ") in Authorization Server..");

        // TODO Long id = nameIdMapping.get(consumerKey);

        /*String configURL = configuration.getParameter(Constants.CLIENT_REG_ENDPOINT);
        String configURLsAccessToken = configuration.getParameter(Constants.REGISTRAION_ACCESS_TOKEN);
        CloseableHttpClient client = getCloseableHttpClient();

        try {

            // Deletion has to be called providing the ID. If we don't have the ID we can't proceed with Delete.
            if (id != null) {
                configURL += "/" + id.toString();
                HttpDelete httpDelete = new HttpDelete(configURL);

                // Set Authorization Header
                httpDelete.addHeader(Constants.AUTHORIZATION, Constants.BEARER + configURLsAccessToken);
                HttpResponse response = client.execute(httpDelete);
                int responseCode = response.getStatusLine().getStatusCode();
                if (log.isDebugEnabled()) {
                    log.debug("Delete application response code :  " + responseCode);
                }
                if (responseCode == HttpStatus.SC_OK ||
                    responseCode == HttpStatus.SC_NO_CONTENT) {
                    log.info("OAuth Client for consumer Id " + consumerKey + " has been successfully deleted");
                    nameIdMapping.remove(consumerKey);
                } else {
                    handleException("Problem occurred while deleting client for Consumer Key " + consumerKey);
                }
            }

        } catch (IOException e) {
            handleException("Error while reading response body from Server ", e);
        } finally {
            client.close();
        }*/
    }

    public OAuthApplicationInfo retrieveApplication(String consumerKey) throws APIManagementException {
        log.debug("Retriving OAuth Client (" + consumerKey + ") in Authorization Server..");

        CloseableHttpClient client = getCloseableHttpClient();

        // First get the Id corresponding to consumerKey
        /*String registrationURL = configuration.getParameter(Constants.CLIENT_REG_ENDPOINT);
        String accessToken = configuration.getParameter(Constants.REGISTRAION_ACCESS_TOKEN);
        BufferedReader reader = null;

        try {

            if (consumerKey != null) {
                // To get the specific client, we have to call like
                // http://192.168.52.5:8080/admin/resourceServer/251/client/355
                log.debug("Found id : " + consumerKey + " for consumer key :" + consumerKey);
                registrationURL += "/" + consumerKey;
            }

            HttpGet request = new HttpGet(registrationURL);
            //set authorization header.
            request.addHeader(Constants.AUTHORIZATION, Constants.BEARER + accessToken);
            HttpResponse response = client.execute(request);

            int responseCode = response.getStatusLine().getStatusCode();
            Object parsedObject;

            HttpEntity entity = response.getEntity();

            reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));

            if (responseCode == HttpStatus.SC_CREATED || responseCode == HttpStatus.SC_OK) {
                JSONParser parser = new JSONParser();
                if (reader != null) {
                    parsedObject = parser.parse(reader);

                    // If we have appended the ID, then the response is a JSONObject if not the response is a JSONArray.
                    if (parsedObject instanceof JSONArray) {
                        // If the response is a JSONArray, then we prime the nameId map,
                        // with the response received. And then return details of the specific client.
                        
                    	
                    	//TODO Edited : addToNameIdMap((JSONArray) parsedObject);
                        for (Object object : (JSONArray) parsedObject) {
                            JSONObject jsonObject = (JSONObject) object;
                            if ((jsonObject.get(Constants.CLIENT_ID)).equals
                                    (consumerKey)) {
                                return createOAuthAppfromResponse(jsonObject);
                            }
                        }
                    } else {
                        return createOAuthAppfromResponse((JSONObject) parsedObject);
                    }
                }

            } else {
                handleException("Something went wrong while retrieving client for consumer key " + consumerKey);
            }

        } catch (ParseException e) {
            handleException("Error while parsing response json.", e);
        } catch (IOException e) {
            handleException("Error while reading response body.", e);
        } finally {
            client.close();
            IOUtils.closeQuietly(reader);
        }*/

        return null;
    }

    public void deleteMappedApplication(String applicationKey) throws APIManagementException {
        log.info("WARNING : request to deleteMappedApplication > " + applicationKey);
    }

    public void deleteRegisteredResourceByAPIId(String APIId) throws APIManagementException {
        log.info("WARNING : request to deleteRegisteredResourceByAPIId > " + APIId);
    }

    public AccessTokenInfo getAccessTokenByConsumerKey(String consumerKey) throws APIManagementException {
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

}
