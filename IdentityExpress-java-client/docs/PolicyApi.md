# PolicyApi

All URIs are relative to *https://localhost*

Method | HTTP request | Description
------------- | ------------- | -------------
[**policyGet**](PolicyApi.md#policyGet) | **GET** /Policy | 
[**policyPut**](PolicyApi.md#policyPut) | **PUT** /Policy | 


<a name="policyGet"></a>
# **policyGet**
> policyGet()



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.PolicyApi;


PolicyApi apiInstance = new PolicyApi();
try {
    apiInstance.policyGet();
} catch (ApiException e) {
    System.err.println("Exception when calling PolicyApi#policyGet");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="policyPut"></a>
# **policyPut**
> policyPut(accessPolicy)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.PolicyApi;


PolicyApi apiInstance = new PolicyApi();
AccessPolicyDefinitionDto accessPolicy = new AccessPolicyDefinitionDto(); // AccessPolicyDefinitionDto | 
try {
    apiInstance.policyPut(accessPolicy);
} catch (ApiException e) {
    System.err.println("Exception when calling PolicyApi#policyPut");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **accessPolicy** | [**AccessPolicyDefinitionDto**](AccessPolicyDefinitionDto.md)|  | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json-patch+json, application/json, text/json, application/_*+json
 - **Accept**: Not defined

