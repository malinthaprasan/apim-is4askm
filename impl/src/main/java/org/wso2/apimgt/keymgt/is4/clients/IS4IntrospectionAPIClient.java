package org.wso2.apimgt.keymgt.is4.clients;

import com.squareup.okhttp.*;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.wso2.apimgt.keymgt.is4.Constants;
import org.wso2.apimgt.keymgt.is4.exception.IntrospectionAPIException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class IS4IntrospectionAPIClient {

    private static Log log = LogFactory.getLog(IS4IntrospectionAPIClient.class);
    private OkHttpClient client;
    private String introspectionAPIUrl = Constants.IS4_INTROSPECTION_API_URL_DEFAULT;

    public IS4IntrospectionAPIClient() {
        client = new OkHttpClient();

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client.interceptors().add(loggingInterceptor);
    }

    public void setIntrospectionAPIUrl(String introspectionAPIUrl) {
        this.introspectionAPIUrl = introspectionAPIUrl;
    }

    public JSONObject getIntrospectionResponse(String accessToken, String resourceId, String resourceSecret)
            throws IntrospectionAPIException {
        Response response;
        JSONObject jsonObject;

        String basicHeader = constructBasicHeader(resourceId, resourceSecret);
        RequestBody formEncoding = new FormEncodingBuilder().add("token", accessToken).build();

        Request request = new Request.Builder().url(introspectionAPIUrl).post(formEncoding)
                .addHeader("Authorization", "Basic " + basicHeader).build();
        try {
            response = client.newCall(request).execute();
            if (response.code() < 200 || response.code() > 299) {
                throw new IntrospectionAPIException("Error while invoking token endpoint. Response code:"
                        + response.code());
            }

            String introspectionResponse = response.body().string();
            log.debug("Introspection response: " + introspectionResponse);

            JSONParser parser = new JSONParser();
            jsonObject = (JSONObject) parser.parse(introspectionResponse);
            return jsonObject;
        } catch (ParseException | IOException e) {
            throw new IntrospectionAPIException("Error while invoking token endpoint", e);
        }
    }

    private String constructBasicHeader(String resourceId, String resourceSecret) {
        String basicHeader = resourceId + ":" + resourceSecret;
        byte[] encoded = Base64.getEncoder().encode(basicHeader.getBytes(StandardCharsets.UTF_8));
        basicHeader = new String(encoded);
        return basicHeader;
    }
}
