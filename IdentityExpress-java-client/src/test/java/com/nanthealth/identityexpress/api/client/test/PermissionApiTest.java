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
import com.nanthealth.identityexpress.api.client.api.PermissionApi;

import org.junit.Ignore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for PermissionApi
 */
@Ignore
public class PermissionApiTest {

    private final PermissionApi api = new PermissionApi();

    
    /**
     * 
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void permissionAvaliableGetTest() throws ApiException {
        api.permissionAvaliableGet();

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
    public void permissionGetTest() throws ApiException {
        api.permissionGet();

        // TODO: test validations
    }
    
}
