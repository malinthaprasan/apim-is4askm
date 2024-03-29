package org.wso2.apimgt.keymgt.is4.handler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpHeaders;
import org.apache.synapse.MessageContext;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import org.apache.synapse.rest.AbstractHandler;
import org.apache.synapse.rest.RESTConstants;
import org.wso2.apimgt.keymgt.is4.Constants;

import java.util.Map;

public class InjectIS4ResourceHandler extends AbstractHandler {
    private static Log log = LogFactory.getLog(InjectIS4ResourceHandler.class);

    @Override
    public boolean handleRequest(MessageContext messageContext) {
        String apiName = (String) messageContext.getProperty(RESTConstants.SYNAPSE_REST_API);
        log.debug("API Name: '" + apiName + "'");

        String apiId = constructAPIId(apiName);

        log.debug("Updated API Name: '" + apiId + "'");

        Map headers = (Map) ((Axis2MessageContext) messageContext).getAxis2MessageContext().
                getProperty(org.apache.axis2.context.MessageContext.TRANSPORT_HEADERS);


        String authHeader = (String) headers.get(HttpHeaders.AUTHORIZATION);
        if (authHeader != null) {
            headers.put(HttpHeaders.AUTHORIZATION, authHeader + Constants.AUTHORIZATION_HEADER_SPLITTER + apiId);
        } else {
            //OPTIONS call (CORS) might not contain the "Authorization" header.
            log.debug("Unable to find the authorization header for request : '" + messageContext.getMessageID() + "'");
        }

        log.debug("Proceeding Auth header: " + headers.get(HttpHeaders.AUTHORIZATION));
        return true;
    }

    @Override
    public boolean handleResponse(MessageContext messageContext) {
        return true;
    }

    private String constructAPIId(String apiName) {
        return apiName.replace("--", "-").replace(":v", "-");
    }
}
