
# CreateClientDto

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**clientType** | [**ClientTypeEnum**](#ClientTypeEnum) |  |  [optional]
**clientId** | **String** |  |  [optional]
**clientName** | **String** |  |  [optional]
**clientUri** | **String** |  |  [optional]
**logoUri** | **String** |  |  [optional]
**description** | **String** |  |  [optional]
**requireConsent** | **Boolean** |  |  [optional]
**redirectUris** | **List&lt;String&gt;** |  |  [optional]
**postLogoutRedirectUris** | **List&lt;String&gt;** |  |  [optional]
**allowedScopes** | **List&lt;String&gt;** |  |  [optional]
**allowedCorsOrigins** | **List&lt;String&gt;** |  |  [optional]
**clientSecrets** | [**List&lt;CreateSecretDto&gt;**](CreateSecretDto.md) |  |  [optional]


<a name="ClientTypeEnum"></a>
## Enum: ClientTypeEnum
Name | Value
---- | -----
NUMBER_0 | 0
NUMBER_1 | 1
NUMBER_2 | 2
NUMBER_3 | 3
NUMBER_4 | 4



