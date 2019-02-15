package org.wso2.apimgt.keymgt.is4.clients;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import org.wso2.apimgt.keymgt.is4.Constants;
import org.wso2.apimgt.keymgt.is4.exception.TokenAPIException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class IS4TokenAPIClient {

    private static Log log = LogFactory.getLog(IS4TokenAPIClient.class);
    private OkHttpClient client;
    private String tokenAPIUrl = Constants.IS4_TOKEN_API_URL_DEFAULT;

    public IS4TokenAPIClient() {
        client = new OkHttpClient();
    }

    public void setTokenAPIUrl(String tokenAPIUrl) {
        this.tokenAPIUrl = tokenAPIUrl;
    }

    public JSONObject getNewAccessTokenWithPasswordGrant(String clientId, String clientSecret, String username,
                                                         String password) throws TokenAPIException {
        String logPrefix = "[Getting new access token for App:" + clientId + " for user " + username + "] ";
        log.debug(logPrefix + " Started");

        RequestBody formEncoding = getPasswordGrantTokenRequestBody(clientId, clientSecret, username, password);
        JSONObject response = getTokenResponse(logPrefix, formEncoding, tokenAPIUrl);

        log.debug(logPrefix + " Completed");
        return response;
    }

    public JSONObject getNewAccessTokenWithClientCredentials(String clientId, String clientSecret)
            throws TokenAPIException {
        String logPrefix = "[Getting new access token for App:" + clientId + "] ";
        log.debug(logPrefix + " Started");

        RequestBody formEncoding = getClientCredentialsTokenRequestBody(clientId, clientSecret);
        JSONObject response = getTokenResponse(logPrefix, formEncoding, tokenAPIUrl);

        log.debug(logPrefix + " Completed");
        return response;
    }

    private JSONObject getTokenResponse(String logPrefix, RequestBody formEncoding, String tokenAPIUrl)
            throws TokenAPIException {
        Response response;
        JSONObject jsonObject;

        Request request = new Request.Builder().url(tokenAPIUrl).post(formEncoding).build();
        try {
            response = client.newCall(request).execute();
            String accessTokenResponse = response.body().string();
            log.debug(logPrefix + "Response: " + accessTokenResponse);

            if (!response.isSuccessful()) {
                throw new TokenAPIException("Error while invoking token endpoint. Status: " + response.code()
                        + ", body: "+ accessTokenResponse);
            }

            JSONParser parser = new JSONParser();
            jsonObject = (JSONObject) parser.parse(accessTokenResponse);
        } catch (IOException | ParseException e) {
            throw new TokenAPIException("Error while invoking token endpoint", e);
        }
        return jsonObject;
    }

    private RequestBody getPasswordGrantTokenRequestBody(String clientId, String clientSecret, String username,
                                                         String password) {
        return new FormEncodingBuilder()
                .add("client_id", clientId)
                .add("client_secret", clientSecret)
                .add("grant_type", "password")
                .add("username", username)
                .add("password", password)
                .build();
    }

    private RequestBody getClientCredentialsTokenRequestBody(String clientId, String clientSecret) {
        return new FormEncodingBuilder()
                .add("client_id", clientId)
                .add("client_secret", clientSecret)
                .add("grant_type", "client_credentials")
                .build();
    }
}
