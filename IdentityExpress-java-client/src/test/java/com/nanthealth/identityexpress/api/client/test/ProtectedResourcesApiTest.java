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
import com.nanthealth.identityexpress.api.client.api.ProtectedResourcesApi;
import com.nanthealth.identityexpress.api.client.model.CreateProtectedResourceDto;
import com.nanthealth.identityexpress.api.client.model.CreateSecretDto;
import com.nanthealth.identityexpress.api.client.model.ProtectedResourceDto;
import com.nanthealth.identityexpress.api.client.model.ScopeDto;
import com.nanthealth.identityexpress.api.client.model.SecretDto;

import org.junit.Ignore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for ProtectedResourcesApi
 */
@Ignore
public class ProtectedResourcesApiTest {

    private final ProtectedResourcesApi api = new ProtectedResourcesApi();

    
    /**
     * 
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void protectedResourcesByIdDeleteTest() throws ApiException {
        String id = null;
        api.protectedResourcesByIdDelete(id);

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
    public void protectedResourcesByIdGetTest() throws ApiException {
        String id = null;
        api.protectedResourcesByIdGet(id);

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
    public void protectedResourcesByIdPutTest() throws ApiException {
        String id = null;
        ProtectedResourceDto resource = null;
        api.protectedResourcesByIdPut(id, resource);

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
    public void protectedResourcesByIdScopesByScopeIdDeleteTest() throws ApiException {
        String id = null;
        Integer scopeId = null;
        api.protectedResourcesByIdScopesByScopeIdDelete(id, scopeId);

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
    public void protectedResourcesByIdScopesByScopeIdPutTest() throws ApiException {
        String id = null;
        Integer scopeId = null;
        ScopeDto scope = null;
        api.protectedResourcesByIdScopesByScopeIdPut(id, scopeId, scope);

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
    public void protectedResourcesByIdScopesPostTest() throws ApiException {
        String id = null;
        ScopeDto scope = null;
        api.protectedResourcesByIdScopesPost(id, scope);

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
    public void protectedResourcesByIdSecretsBySecretIdDeleteTest() throws ApiException {
        String id = null;
        Integer secretId = null;
        api.protectedResourcesByIdSecretsBySecretIdDelete(id, secretId);

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
    public void protectedResourcesByIdSecretsBySecretIdPutTest() throws ApiException {
        String id = null;
        Integer secretId = null;
        SecretDto secret = null;
        api.protectedResourcesByIdSecretsBySecretIdPut(id, secretId, secret);

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
    public void protectedResourcesByIdSecretsPostTest() throws ApiException {
        String id = null;
        CreateSecretDto secret = null;
        api.protectedResourcesByIdSecretsPost(id, secret);

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
    public void protectedResourcesGetTest() throws ApiException {
        String name = null;
        api.protectedResourcesGet(name);

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
    public void protectedResourcesPostTest() throws ApiException {
        CreateProtectedResourceDto resource = null;
        api.protectedResourcesPost(resource);

        // TODO: test validations
    }
    
}