# UserSettingsApi

All URIs are relative to *https://localhost*

Method | HTTP request | Description
------------- | ------------- | -------------
[**userSettingsBySubjectGet**](UserSettingsApi.md#userSettingsBySubjectGet) | **GET** /UserSettings/{subject} | 
[**userSettingsPut**](UserSettingsApi.md#userSettingsPut) | **PUT** /UserSettings | 


<a name="userSettingsBySubjectGet"></a>
# **userSettingsBySubjectGet**
> userSettingsBySubjectGet(subject)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.UserSettingsApi;


UserSettingsApi apiInstance = new UserSettingsApi();
String subject = "subject_example"; // String | 
try {
    apiInstance.userSettingsBySubjectGet(subject);
} catch (ApiException e) {
    System.err.println("Exception when calling UserSettingsApi#userSettingsBySubjectGet");
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

<a name="userSettingsPut"></a>
# **userSettingsPut**
> userSettingsPut(userClaims)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.UserSettingsApi;


UserSettingsApi apiInstance = new UserSettingsApi();
UserClaimDto userClaims = new UserClaimDto(); // UserClaimDto | 
try {
    apiInstance.userSettingsPut(userClaims);
} catch (ApiException e) {
    System.err.println("Exception when calling UserSettingsApi#userSettingsPut");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userClaims** | [**UserClaimDto**](UserClaimDto.md)|  | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json-patch+json, application/json, text/json, application/_*+json
 - **Accept**: Not defined

