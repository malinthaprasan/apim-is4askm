
# ClientDto

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | **String** |  |  [optional]
**enabled** | **Boolean** |  |  [optional]
**clientId** | **String** |  |  [optional]
**description** | **String** |  |  [optional]
**protocolType** | **String** |  |  [optional]
**clientSecrets** | [**List&lt;SecretDto&gt;**](SecretDto.md) |  |  [optional]
**requireClientSecret** | **Boolean** |  |  [optional]
**clientName** | **String** |  |  [optional]
**clientUri** | **String** |  |  [optional]
**logoUri** | **String** |  |  [optional]
**requireConsent** | **Boolean** |  |  [optional]
**allowRememberConsent** | **Boolean** |  |  [optional]
**allowedGrantTypes** | **List&lt;String&gt;** |  |  [optional]
**requirePkce** | **Boolean** |  |  [optional]
**allowPlainTextPkce** | **Boolean** |  |  [optional]
**allowAccessTokensViaBrowser** | **Boolean** |  |  [optional]
**redirectUris** | **List&lt;String&gt;** |  |  [optional]
**postLogoutRedirectUris** | **List&lt;String&gt;** |  |  [optional]
**frontChannelLogoutUri** | **String** |  |  [optional]
**frontChannelLogoutSessionRequired** | **Boolean** |  |  [optional]
**allowOfflineAccess** | **Boolean** |  |  [optional]
**allowedScopes** | **List&lt;String&gt;** |  |  [optional]
**alwaysIncludeUserClaimsInIdToken** | **Boolean** |  |  [optional]
**identityTokenLifetime** | **Integer** |  |  [optional]
**accessTokenLifetime** | **Integer** |  |  [optional]
**authorizationCodeLifetime** | **Integer** |  |  [optional]
**absoluteRefreshTokenLifetime** | **Integer** |  |  [optional]
**slidingRefreshTokenLifetime** | **Integer** |  |  [optional]
**refreshTokenUsage** | [**RefreshTokenUsageEnum**](#RefreshTokenUsageEnum) |  |  [optional]
**updateAccessTokenClaimsOnRefresh** | **Boolean** |  |  [optional]
**refreshTokenExpiration** | [**RefreshTokenExpirationEnum**](#RefreshTokenExpirationEnum) |  |  [optional]
**accessTokenType** | [**AccessTokenTypeEnum**](#AccessTokenTypeEnum) |  |  [optional]
**enableLocalLogin** | **Boolean** |  |  [optional]
**identityProviderRestrictions** | **List&lt;String&gt;** |  |  [optional]
**includeJwtId** | **Boolean** |  |  [optional]
**claims** | [**List&lt;ClaimDto&gt;**](ClaimDto.md) |  |  [optional]
**alwaysSendClientClaims** | **Boolean** |  |  [optional]
**clientClaimsPrefix** | **String** |  |  [optional]
**allowedCorsOrigins** | **List&lt;String&gt;** |  |  [optional]
**properties** | [**List&lt;PropertyDto&gt;**](PropertyDto.md) |  |  [optional]
**pairWiseSubjectSalt** | **String** |  |  [optional]
**consentLifetime** | **Integer** |  |  [optional]
**backChannelLogoutSessionRequired** | **Boolean** |  |  [optional]
**backChannelLogoutUri** | **String** |  |  [optional]
**userSSOLifetime** | **Integer** |  |  [optional]
**nonEditable** | **Boolean** |  |  [optional]
**deviceCodeLifetime** | **Integer** |  |  [optional]
**userCodeType** | **String** |  |  [optional]


<a name="RefreshTokenUsageEnum"></a>
## Enum: RefreshTokenUsageEnum
Name | Value
---- | -----
NUMBER_0 | 0
NUMBER_1 | 1


<a name="RefreshTokenExpirationEnum"></a>
## Enum: RefreshTokenExpirationEnum
Name | Value
---- | -----
NUMBER_0 | 0
NUMBER_1 | 1


<a name="AccessTokenTypeEnum"></a>
## Enum: AccessTokenTypeEnum
Name | Value
---- | -----
NUMBER_0 | 0
NUMBER_1 | 1



