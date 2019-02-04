# RolesApi

All URIs are relative to *https://localhost*

Method | HTTP request | Description
------------- | ------------- | -------------
[**rolesByIdDelete**](RolesApi.md#rolesByIdDelete) | **DELETE** /Roles/{id} | 
[**rolesByIdGet**](RolesApi.md#rolesByIdGet) | **GET** /Roles/{id} | 
[**rolesByIdPut**](RolesApi.md#rolesByIdPut) | **PUT** /Roles/{id} | 
[**rolesByIdUsersDelete**](RolesApi.md#rolesByIdUsersDelete) | **DELETE** /Roles/{id}/users | 
[**rolesByIdUsersPost**](RolesApi.md#rolesByIdUsersPost) | **POST** /Roles/{id}/users | 
[**rolesGet**](RolesApi.md#rolesGet) | **GET** /Roles | 
[**rolesPost**](RolesApi.md#rolesPost) | **POST** /Roles | 


<a name="rolesByIdDelete"></a>
# **rolesByIdDelete**
> rolesByIdDelete(id)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.RolesApi;


RolesApi apiInstance = new RolesApi();
String id = "id_example"; // String | 
try {
    apiInstance.rolesByIdDelete(id);
} catch (ApiException e) {
    System.err.println("Exception when calling RolesApi#rolesByIdDelete");
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

<a name="rolesByIdGet"></a>
# **rolesByIdGet**
> rolesByIdGet(id)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.RolesApi;


RolesApi apiInstance = new RolesApi();
String id = "id_example"; // String | 
try {
    apiInstance.rolesByIdGet(id);
} catch (ApiException e) {
    System.err.println("Exception when calling RolesApi#rolesByIdGet");
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

<a name="rolesByIdPut"></a>
# **rolesByIdPut**
> rolesByIdPut(id, role)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.RolesApi;


RolesApi apiInstance = new RolesApi();
String id = "id_example"; // String | 
RoleDto role = new RoleDto(); // RoleDto | 
try {
    apiInstance.rolesByIdPut(id, role);
} catch (ApiException e) {
    System.err.println("Exception when calling RolesApi#rolesByIdPut");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**|  |
 **role** | [**RoleDto**](RoleDto.md)|  | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json-patch+json, application/json, text/json, application/_*+json
 - **Accept**: Not defined

<a name="rolesByIdUsersDelete"></a>
# **rolesByIdUsersDelete**
> rolesByIdUsersDelete(id, users)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.RolesApi;


RolesApi apiInstance = new RolesApi();
String id = "id_example"; // String | 
List<UserDto> users = Arrays.asList(new UserDto()); // List<UserDto> | 
try {
    apiInstance.rolesByIdUsersDelete(id, users);
} catch (ApiException e) {
    System.err.println("Exception when calling RolesApi#rolesByIdUsersDelete");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**|  |
 **users** | [**List&lt;UserDto&gt;**](UserDto.md)|  | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json-patch+json, application/json, text/json, application/_*+json
 - **Accept**: Not defined

<a name="rolesByIdUsersPost"></a>
# **rolesByIdUsersPost**
> rolesByIdUsersPost(id, users)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.RolesApi;


RolesApi apiInstance = new RolesApi();
String id = "id_example"; // String | 
List<UserDto> users = Arrays.asList(new UserDto()); // List<UserDto> | 
try {
    apiInstance.rolesByIdUsersPost(id, users);
} catch (ApiException e) {
    System.err.println("Exception when calling RolesApi#rolesByIdUsersPost");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**|  |
 **users** | [**List&lt;UserDto&gt;**](UserDto.md)|  | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json-patch+json, application/json, text/json, application/_*+json
 - **Accept**: Not defined

<a name="rolesGet"></a>
# **rolesGet**
> rolesGet(name, sort, fields)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.RolesApi;


RolesApi apiInstance = new RolesApi();
String name = "name_example"; // String | 
String sort = "sort_example"; // String | 
String fields = "fields_example"; // String | 
try {
    apiInstance.rolesGet(name, sort, fields);
} catch (ApiException e) {
    System.err.println("Exception when calling RolesApi#rolesGet");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **name** | **String**|  | [optional]
 **sort** | **String**|  | [optional]
 **fields** | **String**|  | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="rolesPost"></a>
# **rolesPost**
> rolesPost(role)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.RolesApi;


RolesApi apiInstance = new RolesApi();
CreateRoleDto role = new CreateRoleDto(); // CreateRoleDto | 
try {
    apiInstance.rolesPost(role);
} catch (ApiException e) {
    System.err.println("Exception when calling RolesApi#rolesPost");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **role** | [**CreateRoleDto**](CreateRoleDto.md)|  | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json-patch+json, application/json, text/json, application/_*+json
 - **Accept**: Not defined

