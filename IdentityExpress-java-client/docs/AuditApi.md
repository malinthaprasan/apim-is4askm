# AuditApi

All URIs are relative to *https://localhost*

Method | HTTP request | Description
------------- | ------------- | -------------
[**auditedEventsGet**](AuditApi.md#auditedEventsGet) | **GET** /auditedEvents | 
[**auditedEventsPost**](AuditApi.md#auditedEventsPost) | **POST** /auditedEvents | 
[**clientsByClientIdAuditedEventsGet**](AuditApi.md#clientsByClientIdAuditedEventsGet) | **GET** /clients/{clientId}/auditedEvents | 
[**clientsByClientIdAuditedEventsPost**](AuditApi.md#clientsByClientIdAuditedEventsPost) | **POST** /clients/{clientId}/auditedEvents | 
[**usersByUserIdAuditedEventsGet**](AuditApi.md#usersByUserIdAuditedEventsGet) | **GET** /users/{userId}/auditedEvents | 
[**usersByUserIdAuditedEventsPost**](AuditApi.md#usersByUserIdAuditedEventsPost) | **POST** /users/{userId}/auditedEvents | 


<a name="auditedEventsGet"></a>
# **auditedEventsGet**
> auditedEventsGet(from, to, page, pageSize, sort, subject, source, auditAction, resource, resourceType, success)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.AuditApi;


AuditApi apiInstance = new AuditApi();
String from = "from_example"; // String | 
String to = "to_example"; // String | 
Integer page = 56; // Integer | 
Integer pageSize = 56; // Integer | 
String sort = "sort_example"; // String | 
String subject = "subject_example"; // String | 
String source = "source_example"; // String | 
String auditAction = "auditAction_example"; // String | 
String resource = "resource_example"; // String | 
String resourceType = "resourceType_example"; // String | 
Boolean success = true; // Boolean | 
try {
    apiInstance.auditedEventsGet(from, to, page, pageSize, sort, subject, source, auditAction, resource, resourceType, success);
} catch (ApiException e) {
    System.err.println("Exception when calling AuditApi#auditedEventsGet");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **from** | **String**|  | [optional]
 **to** | **String**|  | [optional]
 **page** | **Integer**|  | [optional]
 **pageSize** | **Integer**|  | [optional]
 **sort** | **String**|  | [optional]
 **subject** | **String**|  | [optional]
 **source** | **String**|  | [optional]
 **auditAction** | **String**|  | [optional]
 **resource** | **String**|  | [optional]
 **resourceType** | **String**|  | [optional]
 **success** | **Boolean**|  | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="auditedEventsPost"></a>
# **auditedEventsPost**
> auditedEventsPost(from, to, page, pageSize, sort, subject, source, auditAction, resource, resourceType, success)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.AuditApi;


AuditApi apiInstance = new AuditApi();
String from = "from_example"; // String | 
String to = "to_example"; // String | 
Integer page = 56; // Integer | 
Integer pageSize = 56; // Integer | 
String sort = "sort_example"; // String | 
String subject = "subject_example"; // String | 
String source = "source_example"; // String | 
String auditAction = "auditAction_example"; // String | 
String resource = "resource_example"; // String | 
String resourceType = "resourceType_example"; // String | 
Boolean success = true; // Boolean | 
try {
    apiInstance.auditedEventsPost(from, to, page, pageSize, sort, subject, source, auditAction, resource, resourceType, success);
} catch (ApiException e) {
    System.err.println("Exception when calling AuditApi#auditedEventsPost");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **from** | **String**|  | [optional]
 **to** | **String**|  | [optional]
 **page** | **Integer**|  | [optional]
 **pageSize** | **Integer**|  | [optional]
 **sort** | **String**|  | [optional]
 **subject** | **String**|  | [optional]
 **source** | **String**|  | [optional]
 **auditAction** | **String**|  | [optional]
 **resource** | **String**|  | [optional]
 **resourceType** | **String**|  | [optional]
 **success** | **Boolean**|  | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="clientsByClientIdAuditedEventsGet"></a>
# **clientsByClientIdAuditedEventsGet**
> clientsByClientIdAuditedEventsGet(clientId, from, to, page, pageSize, sort, subject, source, auditAction, resource, resourceType, success)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.AuditApi;


AuditApi apiInstance = new AuditApi();
String clientId = "clientId_example"; // String | 
String from = "from_example"; // String | 
String to = "to_example"; // String | 
Integer page = 56; // Integer | 
Integer pageSize = 56; // Integer | 
String sort = "sort_example"; // String | 
String subject = "subject_example"; // String | 
String source = "source_example"; // String | 
String auditAction = "auditAction_example"; // String | 
String resource = "resource_example"; // String | 
String resourceType = "resourceType_example"; // String | 
Boolean success = true; // Boolean | 
try {
    apiInstance.clientsByClientIdAuditedEventsGet(clientId, from, to, page, pageSize, sort, subject, source, auditAction, resource, resourceType, success);
} catch (ApiException e) {
    System.err.println("Exception when calling AuditApi#clientsByClientIdAuditedEventsGet");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **clientId** | **String**|  |
 **from** | **String**|  | [optional]
 **to** | **String**|  | [optional]
 **page** | **Integer**|  | [optional]
 **pageSize** | **Integer**|  | [optional]
 **sort** | **String**|  | [optional]
 **subject** | **String**|  | [optional]
 **source** | **String**|  | [optional]
 **auditAction** | **String**|  | [optional]
 **resource** | **String**|  | [optional]
 **resourceType** | **String**|  | [optional]
 **success** | **Boolean**|  | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="clientsByClientIdAuditedEventsPost"></a>
# **clientsByClientIdAuditedEventsPost**
> clientsByClientIdAuditedEventsPost(clientId, from, to, page, pageSize, sort, subject, source, auditAction, resource, resourceType, success)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.AuditApi;


AuditApi apiInstance = new AuditApi();
String clientId = "clientId_example"; // String | 
String from = "from_example"; // String | 
String to = "to_example"; // String | 
Integer page = 56; // Integer | 
Integer pageSize = 56; // Integer | 
String sort = "sort_example"; // String | 
String subject = "subject_example"; // String | 
String source = "source_example"; // String | 
String auditAction = "auditAction_example"; // String | 
String resource = "resource_example"; // String | 
String resourceType = "resourceType_example"; // String | 
Boolean success = true; // Boolean | 
try {
    apiInstance.clientsByClientIdAuditedEventsPost(clientId, from, to, page, pageSize, sort, subject, source, auditAction, resource, resourceType, success);
} catch (ApiException e) {
    System.err.println("Exception when calling AuditApi#clientsByClientIdAuditedEventsPost");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **clientId** | **String**|  |
 **from** | **String**|  | [optional]
 **to** | **String**|  | [optional]
 **page** | **Integer**|  | [optional]
 **pageSize** | **Integer**|  | [optional]
 **sort** | **String**|  | [optional]
 **subject** | **String**|  | [optional]
 **source** | **String**|  | [optional]
 **auditAction** | **String**|  | [optional]
 **resource** | **String**|  | [optional]
 **resourceType** | **String**|  | [optional]
 **success** | **Boolean**|  | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="usersByUserIdAuditedEventsGet"></a>
# **usersByUserIdAuditedEventsGet**
> usersByUserIdAuditedEventsGet(userId, from, to, page, pageSize, sort, subject, source, auditAction, resource, resourceType, success)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.AuditApi;


AuditApi apiInstance = new AuditApi();
String userId = "userId_example"; // String | 
String from = "from_example"; // String | 
String to = "to_example"; // String | 
Integer page = 56; // Integer | 
Integer pageSize = 56; // Integer | 
String sort = "sort_example"; // String | 
String subject = "subject_example"; // String | 
String source = "source_example"; // String | 
String auditAction = "auditAction_example"; // String | 
String resource = "resource_example"; // String | 
String resourceType = "resourceType_example"; // String | 
Boolean success = true; // Boolean | 
try {
    apiInstance.usersByUserIdAuditedEventsGet(userId, from, to, page, pageSize, sort, subject, source, auditAction, resource, resourceType, success);
} catch (ApiException e) {
    System.err.println("Exception when calling AuditApi#usersByUserIdAuditedEventsGet");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userId** | **String**|  |
 **from** | **String**|  | [optional]
 **to** | **String**|  | [optional]
 **page** | **Integer**|  | [optional]
 **pageSize** | **Integer**|  | [optional]
 **sort** | **String**|  | [optional]
 **subject** | **String**|  | [optional]
 **source** | **String**|  | [optional]
 **auditAction** | **String**|  | [optional]
 **resource** | **String**|  | [optional]
 **resourceType** | **String**|  | [optional]
 **success** | **Boolean**|  | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="usersByUserIdAuditedEventsPost"></a>
# **usersByUserIdAuditedEventsPost**
> usersByUserIdAuditedEventsPost(userId, from, to, page, pageSize, sort, subject, source, auditAction, resource, resourceType, success)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.AuditApi;


AuditApi apiInstance = new AuditApi();
String userId = "userId_example"; // String | 
String from = "from_example"; // String | 
String to = "to_example"; // String | 
Integer page = 56; // Integer | 
Integer pageSize = 56; // Integer | 
String sort = "sort_example"; // String | 
String subject = "subject_example"; // String | 
String source = "source_example"; // String | 
String auditAction = "auditAction_example"; // String | 
String resource = "resource_example"; // String | 
String resourceType = "resourceType_example"; // String | 
Boolean success = true; // Boolean | 
try {
    apiInstance.usersByUserIdAuditedEventsPost(userId, from, to, page, pageSize, sort, subject, source, auditAction, resource, resourceType, success);
} catch (ApiException e) {
    System.err.println("Exception when calling AuditApi#usersByUserIdAuditedEventsPost");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userId** | **String**|  |
 **from** | **String**|  | [optional]
 **to** | **String**|  | [optional]
 **page** | **Integer**|  | [optional]
 **pageSize** | **Integer**|  | [optional]
 **sort** | **String**|  | [optional]
 **subject** | **String**|  | [optional]
 **source** | **String**|  | [optional]
 **auditAction** | **String**|  | [optional]
 **resource** | **String**|  | [optional]
 **resourceType** | **String**|  | [optional]
 **success** | **Boolean**|  | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

