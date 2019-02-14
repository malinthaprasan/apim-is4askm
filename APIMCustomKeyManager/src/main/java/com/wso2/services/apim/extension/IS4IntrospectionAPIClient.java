package com.wso2.services.apim.extension;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;
import com.wso2.services.apim.extension.exception.IntrospectionAPIException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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

        String basicHeader = resourceId + ":" + resourceSecret;
        byte[] encoded = Base64.getEncoder().encode(basicHeader.getBytes(StandardCharsets.UTF_8));
        basicHeader = new String(encoded);

        RequestBody formEncoding = new FormEncodingBuilder()
                .add("token", accessToken)
                .build();

        Request request = new Request.Builder()
                .url(Constants.IS4_INTROSPECTION_API_URL_DEFAULT)
                .post(formEncoding)
                .addHeader("Authorization", "Basic " + basicHeader)
                .build();

        Response response;
        JSONObject jsonObject;
        try {
            response = client.newCall(request).execute();
            
            if (response.code() < 200 || response.code() > 299 ) {
                throw new IntrospectionAPIException(
                        "Error while invoking token endpoint. Response code:" + response.code());
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
}
