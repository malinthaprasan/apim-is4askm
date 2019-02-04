# ClientsApi

All URIs are relative to *https://localhost*

Method | HTTP request | Description
------------- | ------------- | -------------
[**clientsByIdDelete**](ClientsApi.md#clientsByIdDelete) | **DELETE** /Clients/{id} | 
[**clientsByIdGet**](ClientsApi.md#clientsByIdGet) | **GET** /Clients/{id} | 
[**clientsByIdPut**](ClientsApi.md#clientsByIdPut) | **PUT** /Clients/{id} | 
[**clientsByIdSecretsBySecretIdDelete**](ClientsApi.md#clientsByIdSecretsBySecretIdDelete) | **DELETE** /Clients/{id}/secrets/{secretId} | 
[**clientsByIdSecretsBySecretIdPut**](ClientsApi.md#clientsByIdSecretsBySecretIdPut) | **PUT** /Clients/{id}/secrets/{secretId} | 
[**clientsByIdSecretsPost**](ClientsApi.md#clientsByIdSecretsPost) | **POST** /Clients/{id}/secrets | 
[**clientsGet**](ClientsApi.md#clientsGet) | **GET** /Clients | 
[**clientsPost**](ClientsApi.md#clientsPost) | **POST** /Clients | 


<a name="clientsByIdDelete"></a>
# **clientsByIdDelete**
> clientsByIdDelete(id)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.ClientsApi;


ClientsApi apiInstance = new ClientsApi();
String id = "id_example"; // String | 
try {
    apiInstance.clientsByIdDelete(id);
} catch (ApiException e) {
    System.err.println("Exception when calling ClientsApi#clientsByIdDelete");
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

<a name="clientsByIdGet"></a>
# **clientsByIdGet**
> clientsByIdGet(id)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.ClientsApi;


ClientsApi apiInstance = new ClientsApi();
String id = "id_example"; // String | 
try {
    apiInstance.clientsByIdGet(id);
} catch (ApiException e) {
    System.err.println("Exception when calling ClientsApi#clientsByIdGet");
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

<a name="clientsByIdPut"></a>
# **clientsByIdPut**
> clientsByIdPut(id, client)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.ClientsApi;


ClientsApi apiInstance = new ClientsApi();
String id = "id_example"; // String | 
ClientDto client = new ClientDto(); // ClientDto | 
try {
    apiInstance.clientsByIdPut(id, client);
} catch (ApiException e) {
    System.err.println("Exception when calling ClientsApi#clientsByIdPut");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**|  |
 **client** | [**ClientDto**](ClientDto.md)|  | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json-patch+json, application/json, text/json, application/_*+json
 - **Accept**: Not defined

<a name="clientsByIdSecretsBySecretIdDelete"></a>
# **clientsByIdSecretsBySecretIdDelete**
> clientsByIdSecretsBySecretIdDelete(id, secretId)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.ClientsApi;


ClientsApi apiInstance = new ClientsApi();
String id = "id_example"; // String | 
Integer secretId = 56; // Integer | 
try {
    apiInstance.clientsByIdSecretsBySecretIdDelete(id, secretId);
} catch (ApiException e) {
    System.err.println("Exception when calling ClientsApi#clientsByIdSecretsBySecretIdDelete");
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

<a name="clientsByIdSecretsBySecretIdPut"></a>
# **clientsByIdSecretsBySecretIdPut**
> clientsByIdSecretsBySecretIdPut(id, secretId, secret)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.ClientsApi;


ClientsApi apiInstance = new ClientsApi();
String id = "id_example"; // String | 
Integer secretId = 56; // Integer | 
SecretDto secret = new SecretDto(); // SecretDto | 
try {
    apiInstance.clientsByIdSecretsBySecretIdPut(id, secretId, secret);
} catch (ApiException e) {
    System.err.println("Exception when calling ClientsApi#clientsByIdSecretsBySecretIdPut");
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

<a name="clientsByIdSecretsPost"></a>
# **clientsByIdSecretsPost**
> clientsByIdSecretsPost(id, secret)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.ClientsApi;


ClientsApi apiInstance = new ClientsApi();
String id = "id_example"; // String | 
CreateSecretDto secret = new CreateSecretDto(); // CreateSecretDto | 
try {
    apiInstance.clientsByIdSecretsPost(id, secret);
} catch (ApiException e) {
    System.err.println("Exception when calling ClientsApi#clientsByIdSecretsPost");
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

<a name="clientsGet"></a>
# **clientsGet**
> clientsGet(name)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.ClientsApi;


ClientsApi apiInstance = new ClientsApi();
String name = "name_example"; // String | 
try {
    apiInstance.clientsGet(name);
} catch (ApiException e) {
    System.err.println("Exception when calling ClientsApi#clientsGet");
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

<a name="clientsPost"></a>
# **clientsPost**
> clientsPost(client)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.ClientsApi;


ClientsApi apiInstance = new ClientsApi();
CreateClientDto client = new CreateClientDto(); // CreateClientDto | 
try {
    apiInstance.clientsPost(client);
} catch (ApiException e) {
    System.err.println("Exception when calling ClientsApi#clientsPost");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **client** | [**CreateClientDto**](CreateClientDto.md)|  | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json-patch+json, application/json, text/json, application/_*+json
 - **Accept**: Not defined

