/*
 * IdentityExpress Management API
 * No description provided (generated by Swagger Codegen https://github.com/swagger-api/swagger-codegen)
 *
 * OpenAPI spec version: v1
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package com.nanthealth.identityexpress.api.client.test;

import org.junit.Test;

import com.nanthealth.identityexpress.api.client.ApiException;
import com.nanthealth.identityexpress.api.client.api.UserSettingsApi;
import com.nanthealth.identityexpress.api.client.model.UserClaimDto;

import org.junit.Ignore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for UserSettingsApi
 */
@Ignore
public class UserSettingsApiTest {

    private final UserSettingsApi api = new UserSettingsApi();

    
    /**
     * 
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void userSettingsBySubjectGetTest() throws ApiException {
        String subject = null;
        api.userSettingsBySubjectGet(subject);

        // TODO: test validations
    }
    
    /**
     * 
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void userSettingsPutTest() throws ApiException {
        UserClaimDto userClaims = null;
        api.userSettingsPut(userClaims);

        // TODO: test validations
    }
    
}
