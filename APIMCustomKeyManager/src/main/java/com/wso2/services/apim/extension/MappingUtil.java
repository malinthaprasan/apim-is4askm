package com.wso2.services.apim.extension;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.apimgt.api.model.ApplicationConstants;
import org.wso2.carbon.apimgt.api.model.OAuthApplicationInfo;
import org.wso2.services.is4.model.ClientDto;
import org.wso2.services.is4.model.SecretDto;

import java.util.List;

public class MappingUtil {

    private static Log log = LogFactory.getLog(MappingUtil.class);

    public static OAuthApplicationInfo getOAuthAppInfoFromIS4Client(ClientDto dto) {
        OAuthApplicationInfo oAuthApplicationInfo = new OAuthApplicationInfo();
        return getOAuthAppInfoFromIS4Client(dto, oAuthApplicationInfo);
    }

    public static OAuthApplicationInfo getOAuthAppInfoFromIS4Client(ClientDto dto, OAuthApplicationInfo oAuthAppInfo) {
        if (dto == null) {
            return null;
        }
        String logPrefix = "[Getting OAuth App from IS4 Client" + dto.getClientName() + "] ";
        
        oAuthAppInfo.setClientId(dto.getClientId());
        if (dto.getClientSecrets() != null && dto.getClientSecrets().size() > 0) {
            oAuthAppInfo.setClientSecret(dto.getClientSecrets().get(0).getValue());

            if (dto.getClientSecrets().size() > 1) {
                log.warn(logPrefix + "Created OAuth Client in IS4 is having multiple client secrets. "
                        + "APIM will take the first secret only..");
            }
        } else {
            log.warn(logPrefix + "Created OAuth Client in IS4 is having no client secrets");
        }

        oAuthAppInfo.setClientName(dto.getClientName());
        oAuthAppInfo.addParameter(ApplicationConstants.OAUTH_CLIENT_NAME, dto.getClientName());

        if (dto.getRedirectUris() != null && dto.getRedirectUris().size() > 0) {
            oAuthAppInfo.setCallBackURL(dto.getRedirectUris().get(0));

            //todo add multiple
            oAuthAppInfo.addParameter(ApplicationConstants.
                    OAUTH_REDIRECT_URIS, dto.getRedirectUris().get(0));
        }

        //todo add properly with response grants
        oAuthAppInfo.addParameter(ApplicationConstants.OAUTH_CLIENT_GRANT, "client_credentials");
        
        return oAuthAppInfo;
    }

    public static ClientDto setSecrets(ClientDto from, ClientDto to) {
        List<SecretDto> targetSecretDtos = to.getClientSecrets();
        List<SecretDto> sourceSecretDtos = from.getClientSecrets();
        
        if(targetSecretDtos != null && sourceSecretDtos != null) {
            for (int i = 0; i < Math.min(targetSecretDtos.size(), sourceSecretDtos.size()); i++) {
                targetSecretDtos.get(i).setValue(sourceSecretDtos.get(i).getValue());
            }
        }
        return to;
    }
    
}
