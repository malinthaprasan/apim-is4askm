# ClaimTypesApi

All URIs are relative to *https://localhost*

Method | HTTP request | Description
------------- | ------------- | -------------
[**claimTypesByIdDelete**](ClaimTypesApi.md#claimTypesByIdDelete) | **DELETE** /ClaimTypes/{id} | 
[**claimTypesByIdGet**](ClaimTypesApi.md#claimTypesByIdGet) | **GET** /ClaimTypes/{id} | 
[**claimTypesByIdPut**](ClaimTypesApi.md#claimTypesByIdPut) | **PUT** /ClaimTypes/{id} | 
[**claimTypesGet**](ClaimTypesApi.md#claimTypesGet) | **GET** /ClaimTypes | 
[**claimTypesPost**](ClaimTypesApi.md#claimTypesPost) | **POST** /ClaimTypes | 


<a name="claimTypesByIdDelete"></a>
# **claimTypesByIdDelete**
> claimTypesByIdDelete(id)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.ClaimTypesApi;


ClaimTypesApi apiInstance = new ClaimTypesApi();
String id = "id_example"; // String | 
try {
    apiInstance.claimTypesByIdDelete(id);
} catch (ApiException e) {
    System.err.println("Exception when calling ClaimTypesApi#claimTypesByIdDelete");
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

<a name="claimTypesByIdGet"></a>
# **claimTypesByIdGet**
> claimTypesByIdGet(id)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.ClaimTypesApi;


ClaimTypesApi apiInstance = new ClaimTypesApi();
String id = "id_example"; // String | 
try {
    apiInstance.claimTypesByIdGet(id);
} catch (ApiException e) {
    System.err.println("Exception when calling ClaimTypesApi#claimTypesByIdGet");
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

<a name="claimTypesByIdPut"></a>
# **claimTypesByIdPut**
> claimTypesByIdPut(id, claimType)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.ClaimTypesApi;


ClaimTypesApi apiInstance = new ClaimTypesApi();
String id = "id_example"; // String | 
ClaimTypeDto claimType = new ClaimTypeDto(); // ClaimTypeDto | 
try {
    apiInstance.claimTypesByIdPut(id, claimType);
} catch (ApiException e) {
    System.err.println("Exception when calling ClaimTypesApi#claimTypesByIdPut");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**|  |
 **claimType** | [**ClaimTypeDto**](ClaimTypeDto.md)|  | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json-patch+json, application/json, text/json, application/_*+json
 - **Accept**: Not defined

<a name="claimTypesGet"></a>
# **claimTypesGet**
> claimTypesGet(name, sort)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.ClaimTypesApi;


ClaimTypesApi apiInstance = new ClaimTypesApi();
String name = "name_example"; // String | 
String sort = "sort_example"; // String | 
try {
    apiInstance.claimTypesGet(name, sort);
} catch (ApiException e) {
    System.err.println("Exception when calling ClaimTypesApi#claimTypesGet");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **name** | **String**|  | [optional]
 **sort** | **String**|  | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="claimTypesPost"></a>
# **claimTypesPost**
> claimTypesPost(claimType)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.ClaimTypesApi;


ClaimTypesApi apiInstance = new ClaimTypesApi();
CreateClaimTypeDto claimType = new CreateClaimTypeDto(); // CreateClaimTypeDto | 
try {
    apiInstance.claimTypesPost(claimType);
} catch (ApiException e) {
    System.err.println("Exception when calling ClaimTypesApi#claimTypesPost");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **claimType** | [**CreateClaimTypeDto**](CreateClaimTypeDto.md)|  | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json-patch+json, application/json, text/json, application/_*+json
 - **Accept**: Not defined

