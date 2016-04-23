package org.rssb.awm.conrollers.rest;

import org.activiti.engine.*;
import org.activiti.engine.task.Task;
import org.rssb.awm.common.types.TaskRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sumiran Chugh on 4/5/2016.
 *
 * @copyright atlas
 */
@RestController
public class TaskSubmitResource {

    @Autowired
    TaskService taskService;

    @Autowired
    FormService formService;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    IdentityService identityService;

    @RequestMapping(value = "/runtime/tasks/submit/{taskId}", method = RequestMethod.POST)
    public void submitTask(@PathVariable String taskId, @RequestBody TaskRequest request){

        if(taskId==null){
            throw new ActivitiException("task id cannot be null");
        }
        if(request==null|| !request.isAssigneeSet())
            throw new ActivitiException("Assignee Not set. Please claim task before submission");

        taskService.claim(taskId,request.getAssignee());

        if(request.getComments()!=null && !request.getComments().isEmpty()) {
            Task taskEntity = taskService.createTaskQuery().taskId(taskId).singleResult();
            String processInstanceId = taskEntity.getProcessInstanceId();
            identityService.setAuthenticatedUserId(((UserDetails)(SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getUsername());
            taskService.addComment(taskId,processInstanceId,request.getComments());
        }

        Map<String, String> map = new HashMap<>();
        if(request.getProperties().size()>0) {
            request.getProperties().forEach((p) -> map.put(p.getId(), p.getValue()));
            formService.saveFormData(taskId, map);
        }


        Map<String,Object> map2 = new HashMap<>();
       // request.getProperties().forEach((k)->map.put(k.getName(),k.getValue()));
        map2.putAll(map);
        taskService.complete(taskId,map2,true);
    }


}
