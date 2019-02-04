# ProtectedResourcesApi

All URIs are relative to *https://localhost*

Method | HTTP request | Description
------------- | ------------- | -------------
[**protectedResourcesByIdDelete**](ProtectedResourcesApi.md#protectedResourcesByIdDelete) | **DELETE** /ProtectedResources/{id} | 
[**protectedResourcesByIdGet**](ProtectedResourcesApi.md#protectedResourcesByIdGet) | **GET** /ProtectedResources/{id} | 
[**protectedResourcesByIdPut**](ProtectedResourcesApi.md#protectedResourcesByIdPut) | **PUT** /ProtectedResources/{id} | 
[**protectedResourcesByIdScopesByScopeIdDelete**](ProtectedResourcesApi.md#protectedResourcesByIdScopesByScopeIdDelete) | **DELETE** /ProtectedResources/{id}/scopes/{scopeId} | 
[**protectedResourcesByIdScopesByScopeIdPut**](ProtectedResourcesApi.md#protectedResourcesByIdScopesByScopeIdPut) | **PUT** /ProtectedResources/{id}/scopes/{scopeId} | 
[**protectedResourcesByIdScopesPost**](ProtectedResourcesApi.md#protectedResourcesByIdScopesPost) | **POST** /ProtectedResources/{id}/scopes | 
[**protectedResourcesByIdSecretsBySecretIdDelete**](ProtectedResourcesApi.md#protectedResourcesByIdSecretsBySecretIdDelete) | **DELETE** /ProtectedResources/{id}/secrets/{secretId} | 
[**protectedResourcesByIdSecretsBySecretIdPut**](ProtectedResourcesApi.md#protectedResourcesByIdSecretsBySecretIdPut) | **PUT** /ProtectedResources/{id}/secrets/{secretId} | 
[**protectedResourcesByIdSecretsPost**](ProtectedResourcesApi.md#protectedResourcesByIdSecretsPost) | **POST** /ProtectedResources/{id}/secrets | 
[**protectedResourcesGet**](ProtectedResourcesApi.md#protectedResourcesGet) | **GET** /ProtectedResources | 
[**protectedResourcesPost**](ProtectedResourcesApi.md#protectedResourcesPost) | **POST** /ProtectedResources | 


<a name="protectedResourcesByIdDelete"></a>
# **protectedResourcesByIdDelete**
> protectedResourcesByIdDelete(id)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.ProtectedResourcesApi;


ProtectedResourcesApi apiInstance = new ProtectedResourcesApi();
String id = "id_example"; // String | 
try {
    apiInstance.protectedResourcesByIdDelete(id);
} catch (ApiException e) {
    System.err.println("Exception when calling ProtectedResourcesApi#protectedResourcesByIdDelete");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="protectedResourcesByIdGet"></a>
# **protectedResourcesByIdGet**
> protectedResourcesByIdGet(id)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.ProtectedResourcesApi;


ProtectedResourcesApi apiInstance = new ProtectedResourcesApi();
String id = "id_example"; // String | 
try {
    apiInstance.protectedResourcesByIdGet(id);
} catch (ApiException e) {
    System.err.println("Exception when calling ProtectedResourcesApi#protectedResourcesByIdGet");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="protectedResourcesByIdPut"></a>
# **protectedResourcesByIdPut**
> protectedResourcesByIdPut(id, resource)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.ProtectedResourcesApi;


ProtectedResourcesApi apiInstance = new ProtectedResourcesApi();
String id = "id_example"; // String | 
ProtectedResourceDto resource = new ProtectedResourceDto(); // ProtectedResourceDto | 
try {
    apiInstance.protectedResourcesByIdPut(id, resource);
} catch (ApiException e) {
    System.err.println("Exception when calling ProtectedResourcesApi#protectedResourcesByIdPut");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**|  |
 **resource** | [**ProtectedResourceDto**](ProtectedResourceDto.md)|  | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json-patch+json, application/json, text/json, application/_*+json
 - **Accept**: Not defined

<a name="protectedResourcesByIdScopesByScopeIdDelete"></a>
# **protectedResourcesByIdScopesByScopeIdDelete**
> protectedResourcesByIdScopesByScopeIdDelete(id, scopeId)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.ProtectedResourcesApi;


ProtectedResourcesApi apiInstance = new ProtectedResourcesApi();
String id = "id_example"; // String | 
Integer scopeId = 56; // Integer | 
try {
    apiInstance.protectedResourcesByIdScopesByScopeIdDelete(id, scopeId);
} catch (ApiException e) {
    System.err.println("Exception when calling ProtectedResourcesApi#protectedResourcesByIdScopesByScopeIdDelete");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**|  |
 **scopeId** | **Integer**|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="protectedResourcesByIdScopesByScopeIdPut"></a>
# **protectedResourcesByIdScopesByScopeIdPut**
> protectedResourcesByIdScopesByScopeIdPut(id, scopeId, scope)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.ProtectedResourcesApi;


ProtectedResourcesApi apiInstance = new ProtectedResourcesApi();
String id = "id_example"; // String | 
Integer scopeId = 56; // Integer | 
ScopeDto scope = new ScopeDto(); // ScopeDto | 
try {
    apiInstance.protectedResourcesByIdScopesByScopeIdPut(id, scopeId, scope);
} catch (ApiException e) {
    System.err.println("Exception when calling ProtectedResourcesApi#protectedResourcesByIdScopesByScopeIdPut");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**|  |
 **scopeId** | **Integer**|  |
 **scope** | [**ScopeDto**](ScopeDto.md)|  | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json-patch+json, application/json, text/json, application/_*+json
 - **Accept**: Not defined

<a name="protectedResourcesByIdScopesPost"></a>
# **protectedResourcesByIdScopesPost**
> protectedResourcesByIdScopesPost(id, scope)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.ProtectedResourcesApi;


ProtectedResourcesApi apiInstance = new ProtectedResourcesApi();
String id = "id_example"; // String | 
ScopeDto scope = new ScopeDto(); // ScopeDto | 
try {
    apiInstance.protectedResourcesByIdScopesPost(id, scope);
} catch (ApiException e) {
    System.err.println("Exception when calling ProtectedResourcesApi#protectedResourcesByIdScopesPost");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**|  |
 **scope** | [**ScopeDto**](ScopeDto.md)|  | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json-patch+json, application/json, text/json, application/_*+json
 - **Accept**: Not defined

<a name="protectedResourcesByIdSecretsBySecretIdDelete"></a>
# **protectedResourcesByIdSecretsBySecretIdDelete**
> protectedResourcesByIdSecretsBySecretIdDelete(id, secretId)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.ProtectedResourcesApi;


ProtectedResourcesApi apiInstance = new ProtectedResourcesApi();
String id = "id_example"; // String | 
Integer secretId = 56; // Integer | 
try {
    apiInstance.protectedResourcesByIdSecretsBySecretIdDelete(id, secretId);
} catch (ApiException e) {
    System.err.println("Exception when calling ProtectedResourcesApi#protectedResourcesByIdSecretsBySecretIdDelete");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**|  |
 **secretId** | **Integer**|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="protectedResourcesByIdSecretsBySecretIdPut"></a>
# **protectedResourcesByIdSecretsBySecretIdPut**
> protectedResourcesByIdSecretsBySecretIdPut(id, secretId, secret)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.ProtectedResourcesApi;


ProtectedResourcesApi apiInstance = new ProtectedResourcesApi();
String id = "id_example"; // String | 
Integer secretId = 56; // Integer | 
SecretDto secret = new SecretDto(); // SecretDto | 
try {
    apiInstance.protectedResourcesByIdSecretsBySecretIdPut(id, secretId, secret);
} catch (ApiException e) {
    System.err.println("Exception when calling ProtectedResourcesApi#protectedResourcesByIdSecretsBySecretIdPut");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**|  |
 **secretId** | **Integer**|  |
 **secret** | [**SecretDto**](SecretDto.md)|  | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json-patch+json, application/json, text/json, application/_*+json
 - **Accept**: Not defined

<a name="protectedResourcesByIdSecretsPost"></a>
# **protectedResourcesByIdSecretsPost**
> protectedResourcesByIdSecretsPost(id, secret)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.ProtectedResourcesApi;


ProtectedResourcesApi apiInstance = new ProtectedResourcesApi();
String id = "id_example"; // String | 
CreateSecretDto secret = new CreateSecretDto(); // CreateSecretDto | 
try {
    apiInstance.protectedResourcesByIdSecretsPost(id, secret);
} catch (ApiException e) {
    System.err.println("Exception when calling ProtectedResourcesApi#protectedResourcesByIdSecretsPost");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**|  |
 **secret** | [**CreateSecretDto**](CreateSecretDto.md)|  | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json-patch+json, application/json, text/json, application/_*+json
 - **Accept**: Not defined

<a name="protectedResourcesGet"></a>
# **protectedResourcesGet**
> protectedResourcesGet(name)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.ProtectedResourcesApi;


ProtectedResourcesApi apiInstance = new ProtectedResourcesApi();
String name = "name_example"; // String | 
try {
    apiInstance.protectedResourcesGet(name);
} catch (ApiException e) {
    System.err.println("Exception when calling ProtectedResourcesApi#protectedResourcesGet");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **name** | **String**|  | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="protectedResourcesPost"></a>
# **protectedResourcesPost**
> protectedResourcesPost(resource)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.ProtectedResourcesApi;


ProtectedResourcesApi apiInstance = new ProtectedResourcesApi();
CreateProtectedResourceDto resource = new CreateProtectedResourceDto(); // CreateProtectedResourceDto | 
try {
    apiInstance.protectedResourcesPost(resource);
} catch (ApiException e) {
    System.err.println("Exception when calling ProtectedResourcesApi#protectedResourcesPost");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **resource** | [**CreateProtectedResourceDto**](CreateProtectedResourceDto.md)|  | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json-patch+json, application/json, text/json, application/_*+json
 - **Accept**: Not defined

