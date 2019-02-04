# PermissionApi

All URIs are relative to *https://localhost*

Method | HTTP request | Description
------------- | ------------- | -------------
[**permissionAvaliableGet**](PermissionApi.md#permissionAvaliableGet) | **GET** /Permission/avaliable | 
[**permissionGet**](PermissionApi.md#permissionGet) | **GET** /Permission | 


<a name="permissionAvaliableGet"></a>
# **permissionAvaliableGet**
> permissionAvaliableGet()



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.PermissionApi;


PermissionApi apiInstance = new PermissionApi();
try {
    apiInstance.permissionAvaliableGet();
} catch (ApiException e) {
    System.err.println("Exception when calling PermissionApi#permissionAvaliableGet");
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

<a name="permissionGet"></a>
# **permissionGet**
> permissionGet()



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.PermissionApi;


PermissionApi apiInstance = new PermissionApi();
try {
    apiInstance.permissionGet();
} catch (ApiException e) {
    System.err.println("Exception when calling PermissionApi#permissionGet");
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

