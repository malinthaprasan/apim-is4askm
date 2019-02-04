package com.wso2.services.apim.extension;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.apimgt.keymgt.APIKeyMgtException;
import org.wso2.carbon.apimgt.keymgt.handlers.KeyValidationHandler;
import org.wso2.carbon.apimgt.keymgt.service.TokenValidationContext;

public class IdentityExpressKeyValidationHandlerImpl implements KeyValidationHandler {
	private static Log log = LogFactory.getLog(IdentityExpressKeyValidationHandlerImpl.class);

	public boolean generateConsumerToken(TokenValidationContext validationContext) throws APIKeyMgtException {
		// TODO Auto-generated method stub
		log.debug("Invoking generateConsumerToken");
		return false;
	}

	public boolean validateScopes(TokenValidationContext validationContext) throws APIKeyMgtException {
		// TODO Auto-generated method stub
		log.debug("Invoking validateScopes");
		return false;
	}

	public boolean validateSubscription(TokenValidationContext validationContext) throws APIKeyMgtException {
		// TODO Auto-generated method stub
		log.debug("Invoking validateSubscription");
		return false;
	}

	public boolean validateToken(TokenValidationContext validationContext) throws APIKeyMgtException {
		// TODO Auto-generated method stub
		log.debug("Invoking validateToken");
		return false;
	}

}
