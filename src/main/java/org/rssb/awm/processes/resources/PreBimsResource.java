package org.rssb.awm.processes.resources;

import org.activiti.engine.*;
import org.activiti.rest.service.api.RestResponseFactory;
import org.activiti.rest.service.api.engine.variable.RestVariable;
import org.activiti.rest.service.api.runtime.process.ProcessInstanceCollectionResource;
import org.activiti.rest.service.api.runtime.process.ProcessInstanceCreateRequest;
import org.activiti.rest.service.api.runtime.process.ProcessInstanceResponse;
import org.activiti.rest.service.api.runtime.task.TaskActionRequest;
import org.activiti.rest.service.api.runtime.task.TaskResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sumiran Chugh on 3/27/2016.
 *
 * @copyright atlas
 */
@RestController
public class PreBimsResource {

    @Autowired
    protected RestResponseFactory restResponseFactory;
    @Autowired
    IdentityService identityService;
    @Autowired
    RuntimeService runtimService;
    @Autowired
    TaskService taskService;
    @Autowired
    ProcessInstanceCollectionResource processInstanceCollectionResource;
    @Autowired
    TaskResource taskResource;

    @RequestMapping(value = "/runtime/process-instances/create", method = RequestMethod.POST, produces = "application/json")
    public ProcessInstanceResponse createProcessInstance(@RequestBody ProcessInstanceCreateRequest request,
                                                         HttpServletRequest httpRequest, HttpServletResponse response) {
        Map<String, Object> startVariables = null;
        if (request.getVariables() != null) {
            startVariables = new HashMap<String, Object>();
            for (RestVariable variable : request.getVariables()) {
                if (variable.getName() == null) {
                    throw new ActivitiIllegalArgumentException("Variable name is required.");
                }
                startVariables.put(variable.getName(), restResponseFactory.getVariableValue(variable));
            }
        }

        return restResponseFactory.createProcessInstanceResponse(runtimService.startProcessInstanceByKey(request.getProcessDefinitionKey(), startVariables));

    }


    @RequestMapping(value = "/runtime/tasks/{taskId}/complete", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void completeTask(@PathVariable String taskId, @RequestBody TaskActionRequest actionRequest) {
        if (actionRequest.getAssignee() == null) {
            throw new ActivitiException("task should be claimed before approval");
        }
        taskResource.executeTaskAction(taskId, actionRequest);
    }

}
