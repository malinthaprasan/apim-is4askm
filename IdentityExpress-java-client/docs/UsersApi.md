# UsersApi

All URIs are relative to *https://localhost*

Method | HTTP request | Description
------------- | ------------- | -------------
[**usersBySubjectAppsByClientIdDelete**](UsersApi.md#usersBySubjectAppsByClientIdDelete) | **DELETE** /Users/{subject}/apps/{clientId} | 
[**usersBySubjectAppsDelete**](UsersApi.md#usersBySubjectAppsDelete) | **DELETE** /Users/{subject}/apps | 
[**usersBySubjectAppsGet**](UsersApi.md#usersBySubjectAppsGet) | **GET** /Users/{subject}/apps | 
[**usersBySubjectClaimsDelete**](UsersApi.md#usersBySubjectClaimsDelete) | **DELETE** /Users/{subject}/claims | 
[**usersBySubjectClaimsPost**](UsersApi.md#usersBySubjectClaimsPost) | **POST** /Users/{subject}/claims | 
[**usersBySubjectClaimsPut**](UsersApi.md#usersBySubjectClaimsPut) | **PUT** /Users/{subject}/claims | 
[**usersBySubjectDelete**](UsersApi.md#usersBySubjectDelete) | **DELETE** /Users/{subject} | 
[**usersBySubjectGet**](UsersApi.md#usersBySubjectGet) | **GET** /Users/{subject} | 
[**usersBySubjectLoginsDelete**](UsersApi.md#usersBySubjectLoginsDelete) | **DELETE** /Users/{subject}/logins | 
[**usersBySubjectLoginsGet**](UsersApi.md#usersBySubjectLoginsGet) | **GET** /Users/{subject}/logins | 
[**usersBySubjectPasswordResetGet**](UsersApi.md#usersBySubjectPasswordResetGet) | **GET** /Users/{subject}/password/reset | 
[**usersBySubjectPut**](UsersApi.md#usersBySubjectPut) | **PUT** /Users/{subject} | 
[**usersBySubjectRolesDelete**](UsersApi.md#usersBySubjectRolesDelete) | **DELETE** /Users/{subject}/roles | 
[**usersBySubjectRolesPost**](UsersApi.md#usersBySubjectRolesPost) | **POST** /Users/{subject}/roles | 
[**usersGet**](UsersApi.md#usersGet) | **GET** /Users | 
[**usersLightweightGet**](UsersApi.md#usersLightweightGet) | **GET** /Users/lightweight | 
[**usersPost**](UsersApi.md#usersPost) | **POST** /Users | 


<a name="usersBySubjectAppsByClientIdDelete"></a>
# **usersBySubjectAppsByClientIdDelete**
> usersBySubjectAppsByClientIdDelete(subject, clientId)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.UsersApi;


UsersApi apiInstance = new UsersApi();
String subject = "subject_example"; // String | 
String clientId = "clientId_example"; // String | 
try {
    apiInstance.usersBySubjectAppsByClientIdDelete(subject, clientId);
} catch (ApiException e) {
    System.err.println("Exception when calling UsersApi#usersBySubjectAppsByClientIdDelete");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **subject** | **String**|  |
 **clientId** | **String**|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="usersBySubjectAppsDelete"></a>
# **usersBySubjectAppsDelete**
> usersBySubjectAppsDelete(subject)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.UsersApi;


UsersApi apiInstance = new UsersApi();
String subject = "subject_example"; // String | 
try {
    apiInstance.usersBySubjectAppsDelete(subject);
} catch (ApiException e) {
    System.err.println("Exception when calling UsersApi#usersBySubjectAppsDelete");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **subject** | **String**|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="usersBySubjectAppsGet"></a>
# **usersBySubjectAppsGet**
> usersBySubjectAppsGet(subject)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.UsersApi;


UsersApi apiInstance = new UsersApi();
String subject = "subject_example"; // String | 
try {
    apiInstance.usersBySubjectAppsGet(subject);
} catch (ApiException e) {
    System.err.println("Exception when calling UsersApi#usersBySubjectAppsGet");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **subject** | **String**|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="usersBySubjectClaimsDelete"></a>
# **usersBySubjectClaimsDelete**
> usersBySubjectClaimsDelete(subject, claim)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.UsersApi;


UsersApi apiInstance = new UsersApi();
String subject = "subject_example"; // String | 
ClaimDto claim = new ClaimDto(); // ClaimDto | 
try {
    apiInstance.usersBySubjectClaimsDelete(subject, claim);
} catch (ApiException e) {
    System.err.println("Exception when calling UsersApi#usersBySubjectClaimsDelete");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **subject** | **String**|  |
 **claim** | [**ClaimDto**](ClaimDto.md)|  | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json-patch+json, application/json, text/json, application/_*+json
 - **Accept**: Not defined

<a name="usersBySubjectClaimsPost"></a>
# **usersBySubjectClaimsPost**
> usersBySubjectClaimsPost(subject, claim)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.UsersApi;


UsersApi apiInstance = new UsersApi();
String subject = "subject_example"; // String | 
ClaimDto claim = new ClaimDto(); // ClaimDto | 
try {
    apiInstance.usersBySubjectClaimsPost(subject, claim);
} catch (ApiException e) {
    System.err.println("Exception when calling UsersApi#usersBySubjectClaimsPost");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **subject** | **String**|  |
 **claim** | [**ClaimDto**](ClaimDto.md)|  | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json-patch+json, application/json, text/json, application/_*+json
 - **Accept**: Not defined

<a name="usersBySubjectClaimsPut"></a>
# **usersBySubjectClaimsPut**
> usersBySubjectClaimsPut(subject, dto)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.UsersApi;


UsersApi apiInstance = new UsersApi();
String subject = "subject_example"; // String | 
EditClaimDto dto = new EditClaimDto(); // EditClaimDto | 
try {
    apiInstance.usersBySubjectClaimsPut(subject, dto);
} catch (ApiException e) {
    System.err.println("Exception when calling UsersApi#usersBySubjectClaimsPut");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **subject** | **String**|  |
 **dto** | [**EditClaimDto**](EditClaimDto.md)|  | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json-patch+json, application/json, text/json, application/_*+json
 - **Accept**: Not defined

<a name="usersBySubjectDelete"></a>
# **usersBySubjectDelete**
> usersBySubjectDelete(subject)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.UsersApi;


UsersApi apiInstance = new UsersApi();
String subject = "subject_example"; // String | 
try {
    apiInstance.usersBySubjectDelete(subject);
} catch (ApiException e) {
    System.err.println("Exception when calling UsersApi#usersBySubjectDelete");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **subject** | **String**|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="usersBySubjectGet"></a>
# **usersBySubjectGet**
> usersBySubjectGet(subject)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.UsersApi;


UsersApi apiInstance = new UsersApi();
String subject = "subject_example"; // String | 
try {
    apiInstance.usersBySubjectGet(subject);
} catch (ApiException e) {
    System.err.println("Exception when calling UsersApi#usersBySubjectGet");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **subject** | **String**|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="usersBySubjectLoginsDelete"></a>
# **usersBySubjectLoginsDelete**
> usersBySubjectLoginsDelete(subject, userLogin)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.UsersApi;


UsersApi apiInstance = new UsersApi();
String subject = "subject_example"; // String | 
UserLoginDto userLogin = new UserLoginDto(); // UserLoginDto | 
try {
    apiInstance.usersBySubjectLoginsDelete(subject, userLogin);
} catch (ApiException e) {
    System.err.println("Exception when calling UsersApi#usersBySubjectLoginsDelete");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **subject** | **String**|  |
 **userLogin** | [**UserLoginDto**](UserLoginDto.md)|  | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json-patch+json, application/json, text/json, application/_*+json
 - **Accept**: Not defined

<a name="usersBySubjectLoginsGet"></a>
# **usersBySubjectLoginsGet**
> usersBySubjectLoginsGet(subject)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.UsersApi;


UsersApi apiInstance = new UsersApi();
String subject = "subject_example"; // String | 
try {
    apiInstance.usersBySubjectLoginsGet(subject);
} catch (ApiException e) {
    System.err.println("Exception when calling UsersApi#usersBySubjectLoginsGet");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **subject** | **String**|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="usersBySubjectPasswordResetGet"></a>
# **usersBySubjectPasswordResetGet**
> usersBySubjectPasswordResetGet(subject)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.UsersApi;


UsersApi apiInstance = new UsersApi();
String subject = "subject_example"; // String | 
try {
    apiInstance.usersBySubjectPasswordResetGet(subject);
} catch (ApiException e) {
    System.err.println("Exception when calling UsersApi#usersBySubjectPasswordResetGet");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **subject** | **String**|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="usersBySubjectPut"></a>
# **usersBySubjectPut**
> usersBySubjectPut(subject, user)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.UsersApi;


UsersApi apiInstance = new UsersApi();
String subject = "subject_example"; // String | 
UserDto user = new UserDto(); // UserDto | 
try {
    apiInstance.usersBySubjectPut(subject, user);
} catch (ApiException e) {
    System.err.println("Exception when calling UsersApi#usersBySubjectPut");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **subject** | **String**|  |
 **user** | [**UserDto**](UserDto.md)|  | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json-patch+json, application/json, text/json, application/_*+json
 - **Accept**: Not defined

<a name="usersBySubjectRolesDelete"></a>
# **usersBySubjectRolesDelete**
> usersBySubjectRolesDelete(subject, roles)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.UsersApi;


UsersApi apiInstance = new UsersApi();
String subject = "subject_example"; // String | 
List<String> roles = Arrays.asList(new List<String>()); // List<String> | 
try {
    apiInstance.usersBySubjectRolesDelete(subject, roles);
} catch (ApiException e) {
    System.err.println("Exception when calling UsersApi#usersBySubjectRolesDelete");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **subject** | **String**|  |
 **roles** | **List&lt;String&gt;**|  | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json-patch+json, application/json, text/json, application/_*+json
 - **Accept**: Not defined

<a name="usersBySubjectRolesPost"></a>
# **usersBySubjectRolesPost**
> usersBySubjectRolesPost(subject, roles)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.UsersApi;


UsersApi apiInstance = new UsersApi();
String subject = "subject_example"; // String | 
List<String> roles = Arrays.asList(new List<String>()); // List<String> | 
try {
    apiInstance.usersBySubjectRolesPost(subject, roles);
} catch (ApiException e) {
    System.err.println("Exception when calling UsersApi#usersBySubjectRolesPost");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **subject** | **String**|  |
 **roles** | **List&lt;String&gt;**|  | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json-patch+json, application/json, text/json, application/_*+json
 - **Accept**: Not defined

<a name="usersGet"></a>
# **usersGet**
> usersGet(page, pageSize, sort, username, email, name, id, state, q)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.UsersApi;


UsersApi apiInstance = new UsersApi();
Integer page = 56; // Integer | 
Integer pageSize = 56; // Integer | 
String sort = "sort_example"; // String | 
String username = "username_example"; // String | 
String email = "email_example"; // String | 
String name = "name_example"; // String | 
String id = "id_example"; // String | 
String state = "state_example"; // String | 
String q = "q_example"; // String | 
try {
    apiInstance.usersGet(page, pageSize, sort, username, email, name, id, state, q);
} catch (ApiException e) {
    System.err.println("Exception when calling UsersApi#usersGet");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **page** | **Integer**|  | [optional]
 **pageSize** | **Integer**|  | [optional]
 **sort** | **String**|  | [optional]
 **username** | **String**|  | [optional]
 **email** | **String**|  | [optional]
 **name** | **String**|  | [optional]
 **id** | **String**|  | [optional]
 **state** | **String**|  | [optional]
 **q** | **String**|  | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="usersLightweightGet"></a>
# **usersLightweightGet**
> usersLightweightGet(page, pageSize, state, q)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.UsersApi;


UsersApi apiInstance = new UsersApi();
Integer page = 56; // Integer | 
Integer pageSize = 56; // Integer | 
String state = "state_example"; // String | 
String q = "q_example"; // String | 
try {
    apiInstance.usersLightweightGet(page, pageSize, state, q);
} catch (ApiException e) {
    System.err.println("Exception when calling UsersApi#usersLightweightGet");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **page** | **Integer**|  | [optional]
 **pageSize** | **Integer**|  | [optional]
 **state** | **String**|  | [optional]
 **q** | **String**|  | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="usersPost"></a>
# **usersPost**
> usersPost(user)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.UsersApi;


UsersApi apiInstance = new UsersApi();
CreateUserDto user = new CreateUserDto(); // CreateUserDto | 
try {
    apiInstance.usersPost(user);
} catch (ApiException e) {
    System.err.println("Exception when calling UsersApi#usersPost");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **user** | [**CreateUserDto**](CreateUserDto.md)|  | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json-patch+json, application/json, text/json, application/_*+json
 - **Accept**: Not defined

