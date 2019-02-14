package com.wso2.services.apim.extension;

public class Constants {
    public static final String ADMIN_API_BASE_PATH_DEFAULT = "http://localhost:5001";
    public static final String CALLBACK_URL_DEFAULT = "http://localhost/callback";
    public static final String IS4_APP_ATTR_PREFIX_DEFAULT = "is4.";
    public static final String IS4_TOKEN_SCOPE_DEFAULT = "api_gw";
    public static final String IS4_TOKEN_API_URL_DEFAULT = "http://ids:5003/connect/token";
    public static final String IS4_INTROSPECTION_API_URL_DEFAULT = "http://ids:5003/connect/introspect";
    public static final String IS4_CLIENT_ID = "ClientId";
    public static final String IS4_CLIENT_SECRET = "ClientSecret";
    public static final String IS4_USERNAME = "Username";
    public static final String IS4_PASSWORD = "Password";
    public static final String IS4_ADMIN_API_BASE_PATH = "AdminAPI";
    public static final String IS4_IS4_TOKEN_API_URL = "TokenAPI";
    public static final String IS4_INTROSPECTION_API = "IntrospectionAPI";

    public static final String APIM_TOKEN_SCOPE_DEFAULT = "default";
    public static final String JSON_CLIENT_ID = "client_id";
    public static final String JSON_CLIENT_SECRET = "client_secret";

    public static final String OAUTH_RESPONSE_ACCESSTOKEN = "access_token";
    public static final String OAUTH_RESPONSE_EXPIRY_TIME = "expires_in";
    public static final String GRANT_TYPE_VALUE = "client_credentials";
    public static final String GRANT_TYPE_PARAM_VALIDITY = "validity_period";
    
    public static final String CLIENT_REG_ENDPOINT = "RegistrationEndpoint";
    public static final String CLIENT_NAME = "name";

    public static final String UTF_8 = "UTF-8";
    public static final String CLIENT_SECRET = "secret";

    public static final String REGISTRAION_ACCESS_TOKEN = "AccessToken";
    public static final String CLIENT_ID = "clientId";

    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";
    public static final String APPLICATION_JSON_CONTENT_TYPE = "application/json";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String URL_ENCODED_CONTENT_TYPE = "application/x-www-form-urlencoded";
    public static final String CLIENT_SCOPE = "scopes";
    public static final String CLIENT_CONTACT_NAME = "contactName";
    public static final String CLIENT_CONTACT_EMAIL = "contactEmail";
    public static final String INTROSPECTION_CK = "ConsumerKey";
    public static final String INTROSPECTION_CS = "ConsumerSecret";
    
    
    
    public static final int API_AUTH_GENERAL_ERROR       = 900900;
    public static final int API_AUTH_INVALID_CREDENTIALS = 900901;
    public static final int API_AUTH_MISSING_CREDENTIALS = 900902;
    public static final int API_AUTH_ACCESS_TOKEN_EXPIRED = 900903;
    public static final int API_AUTH_ACCESS_TOKEN_INACTIVE = 900904;
    public static final int API_AUTH_INCORRECT_ACCESS_TOKEN_TYPE = 900905;
    public static final int API_AUTH_INCORRECT_API_RESOURCE = 900906;
    public static final int API_BLOCKED = 900907;
    public static final int API_AUTH_RESOURCE_FORBIDDEN = 900908;
    public static final int SUBSCRIPTION_INACTIVE = 900909;
    public static final int INVALID_SCOPE = 900910;
    
    
}
