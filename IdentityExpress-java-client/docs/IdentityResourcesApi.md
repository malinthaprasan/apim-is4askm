# IdentityResourcesApi

All URIs are relative to *https://localhost*

Method | HTTP request | Description
------------- | ------------- | -------------
[**identityResourcesByIdDelete**](IdentityResourcesApi.md#identityResourcesByIdDelete) | **DELETE** /IdentityResources/{id} | 
[**identityResourcesByIdGet**](IdentityResourcesApi.md#identityResourcesByIdGet) | **GET** /IdentityResources/{id} | 
[**identityResourcesByIdPut**](IdentityResourcesApi.md#identityResourcesByIdPut) | **PUT** /IdentityResources/{id} | 
[**identityResourcesGet**](IdentityResourcesApi.md#identityResourcesGet) | **GET** /IdentityResources | 
[**identityResourcesPost**](IdentityResourcesApi.md#identityResourcesPost) | **POST** /IdentityResources | 


<a name="identityResourcesByIdDelete"></a>
# **identityResourcesByIdDelete**
> identityResourcesByIdDelete(id)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.IdentityResourcesApi;


IdentityResourcesApi apiInstance = new IdentityResourcesApi();
String id = "id_example"; // String | 
try {
    apiInstance.identityResourcesByIdDelete(id);
} catch (ApiException e) {
    System.err.println("Exception when calling IdentityResourcesApi#identityResourcesByIdDelete");
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

<a name="identityResourcesByIdGet"></a>
# **identityResourcesByIdGet**
> identityResourcesByIdGet(id)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.IdentityResourcesApi;


IdentityResourcesApi apiInstance = new IdentityResourcesApi();
String id = "id_example"; // String | 
try {
    apiInstance.identityResourcesByIdGet(id);
} catch (ApiException e) {
    System.err.println("Exception when calling IdentityResourcesApi#identityResourcesByIdGet");
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

<a name="identityResourcesByIdPut"></a>
# **identityResourcesByIdPut**
> identityResourcesByIdPut(id, resource)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.IdentityResourcesApi;


IdentityResourcesApi apiInstance = new IdentityResourcesApi();
String id = "id_example"; // String | 
IdentityResourceDto resource = new IdentityResourceDto(); // IdentityResourceDto | 
try {
    apiInstance.identityResourcesByIdPut(id, resource);
} catch (ApiException e) {
    System.err.println("Exception when calling IdentityResourcesApi#identityResourcesByIdPut");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**|  |
 **resource** | [**IdentityResourceDto**](IdentityResourceDto.md)|  | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json-patch+json, application/json, text/json, application/_*+json
 - **Accept**: Not defined

<a name="identityResourcesGet"></a>
# **identityResourcesGet**
> identityResourcesGet(name)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.IdentityResourcesApi;


IdentityResourcesApi apiInstance = new IdentityResourcesApi();
String name = "name_example"; // String | 
try {
    apiInstance.identityResourcesGet(name);
} catch (ApiException e) {
    System.err.println("Exception when calling IdentityResourcesApi#identityResourcesGet");
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

<a name="identityResourcesPost"></a>
# **identityResourcesPost**
> identityResourcesPost(resource)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.IdentityResourcesApi;


IdentityResourcesApi apiInstance = new IdentityResourcesApi();
CreateIdentityResourceDto resource = new CreateIdentityResourceDto(); // CreateIdentityResourceDto | 
try {
    apiInstance.identityResourcesPost(resource);
} catch (ApiException e) {
    System.err.println("Exception when calling IdentityResourcesApi#identityResourcesPost");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **resource** | [**CreateIdentityResourceDto**](CreateIdentityResourceDto.md)|  | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json-patch+json, application/json, text/json, application/_*+json
 - **Accept**: Not defined

