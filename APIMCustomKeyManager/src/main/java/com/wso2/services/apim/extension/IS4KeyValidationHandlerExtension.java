package com.wso2.services.apim.extension;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.apimgt.keymgt.APIKeyMgtException;
import org.wso2.carbon.apimgt.keymgt.handlers.DefaultKeyValidationHandler;
import org.wso2.carbon.apimgt.keymgt.service.TokenValidationContext;

public class IS4KeyValidationHandlerExtension extends DefaultKeyValidationHandler {

    private static Log log = LogFactory.getLog(IS4KeyValidationHandlerExtension.class);

    public boolean validateScopes(TokenValidationContext validationContext) throws APIKeyMgtException {

        log.debug("Validating scopes from the IS4KeyValidationHandlerExtension");
        return true;
    }
}
