package com.wso2.services.apim.extension.workflow;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.apimgt.api.WorkflowResponse;
import org.wso2.carbon.apimgt.impl.dto.ApplicationRegistrationWorkflowDTO;
import org.wso2.carbon.apimgt.impl.dto.WorkflowDTO;
import org.wso2.carbon.apimgt.impl.workflow.ApplicationRegistrationSimpleWorkflowExecutor;
import org.wso2.carbon.apimgt.impl.workflow.GeneralWorkflowResponse;
import org.wso2.carbon.apimgt.impl.workflow.WorkflowException;

public class IS4ApplicationRegistrationWorkflow extends ApplicationRegistrationSimpleWorkflowExecutor{

    private static Log log = LogFactory.getLog(IS4ApplicationRegistrationWorkflow.class);

    @Override
    public WorkflowResponse complete(WorkflowDTO workFlowDTO) throws WorkflowException {
        ApplicationRegistrationWorkflowDTO appRegWorkflowDTO = (ApplicationRegistrationWorkflowDTO) workFlowDTO;
        super.complete(workFlowDTO);
        String logPrefix = "[Application Reg. workflow for  " + appRegWorkflowDTO.getApplication().getName() + "] ";
        log.debug(logPrefix + "Started");
        
        log.debug(logPrefix + "Completed");
        return new GeneralWorkflowResponse();
    }
}
