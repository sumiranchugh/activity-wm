package org.rssb.awm.conrollers.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.HistoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.*;
import org.activiti.engine.task.Comment;
import org.activiti.rest.common.api.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by Sumiran Chugh on 4/5/2016.
 *
 * @copyright atlas
 */
@RestController
public class ProcessInstanceInfoResource {


    @Autowired
    private HistoryService historyService;
    @Autowired
    private TaskService taskService;

    @RequestMapping(value = "/info/process-instance/{processInstanceId}", method = RequestMethod.GET)
@ResponseBody
    public ObjectNode getProcessInfo(@PathVariable String processInstanceId){
        ObjectNode responseJSON = new ObjectMapper().createObjectNode();
        try{
        HistoricProcessInstance instance =
                historyService
                        .createHistoricProcessInstanceQuery()
                        .processInstanceId(processInstanceId)
                        .singleResult();
        if(instance == null) return null;
        responseJSON.put("processInstanceId",
                instance.getId());
        responseJSON.put("businessKey",
                instance.getBusinessKey() != null ?
                        instance.getBusinessKey() : "null");
        responseJSON.put("processDefinitionId",
                instance.getProcessDefinitionId());
        responseJSON.put("startTime",
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mmX")
                        .withZone(ZoneOffset.UTC)
                        .format(Instant.ofEpochMilli(instance.getStartTime().getTime())));
        if(instance.getEndTime() == null) {
            responseJSON.put("completed", false);
        } else {
            responseJSON.put("completed", true);
            responseJSON.put("endTime",
                    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mmX")
                            .withZone(ZoneOffset.UTC)
                            .format(Instant.ofEpochMilli(instance.getEndTime().getTime())));
            responseJSON.put("duration",
                    instance.getDurationInMillis());
        }
        addTaskList(processInstanceId, responseJSON);
        addActivityList(processInstanceId, responseJSON);
        addVariableList(processInstanceId, responseJSON);
    } catch (Exception e) {
        throw new ActivitiException(
                "Failed to retrieve the process instance " +
                        " details for id " + processInstanceId, e);
    }
    return responseJSON;
}


    private void addTaskList(String processInstanceId,
                             ObjectNode responseJSON) {
        List<HistoricTaskInstance> taskList =
                historyService
                        .createHistoricTaskInstanceQuery()
                        .processInstanceId(processInstanceId)
                        .orderByTaskCreateTime()
                        .asc()
                        .list();

        if (taskList != null && taskList.size() > 0) {
            ArrayNode tasksJSON = new ObjectMapper()
                    .createArrayNode();
            responseJSON.set("tasks", tasksJSON);
            for (HistoricTaskInstance historicTaskInstance : taskList) {
                ObjectNode taskJSON = new ObjectMapper()
                        .createObjectNode();
                taskJSON.put("taskId", historicTaskInstance.getId());
                historicTaskInstance.getTaskLocalVariables().forEach((k,v)-> taskJSON.put(k,v ==null ? "null":v.toString()));
                taskJSON.put("taskName", historicTaskInstance.getName()
                        != null ? historicTaskInstance.getName() : "null");
                taskJSON.put("owner", historicTaskInstance.getOwner()
                        != null ? historicTaskInstance.getOwner() : "null");
                taskJSON.put("assignee",
                        historicTaskInstance.getAssignee() != null ?
                                historicTaskInstance.getAssignee() : "null");
                taskJSON.put("claimTime",
                        RequestUtil.dateToString(historicTaskInstance.getClaimTime()));
                taskJSON.put("startTime", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mmX")
                        .withZone(ZoneOffset.UTC)
                        .format(Instant.ofEpochMilli(historicTaskInstance.getStartTime().getTime())));
                if (historicTaskInstance.getEndTime() == null) {
                    taskJSON.put("completed", false);
                } else {
                    taskJSON.put("completed", true);
                    taskJSON.put("endTime", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mmX")
                            .withZone(ZoneOffset.UTC)
                            .format(Instant.ofEpochMilli(historicTaskInstance.getEndTime().getTime())));
                    taskJSON.put("duration", historicTaskInstance
                            .getDurationInMillis());
                }
                List<Comment> taskComments = taskService.getTaskComments(historicTaskInstance.getId());
                StringBuilder sb = new StringBuilder();
                taskComments.forEach((p)-> sb.append(p.getUserId()+":"+p.getFullMessage()+"; "));
                taskJSON.put("comments",sb.toString());
                tasksJSON.add(taskJSON);
            }
        }
    }


    private void addActivityList(String processInstanceId, ObjectNode responseJSON) {
        List<HistoricActivityInstance> activityList = historyService
                .createHistoricActivityInstanceQuery()
                .processInstanceId(processInstanceId)
                .orderByHistoricActivityInstanceStartTime()
                .asc()
                .list();

        if(activityList != null && activityList.size() > 0) {
            ArrayNode activitiesJSON = new ObjectMapper().createArrayNode();
            responseJSON.set("activities", activitiesJSON);
            for (HistoricActivityInstance historicActivityInstance : activityList) {
                ObjectNode activityJSON = new ObjectMapper().createObjectNode();
                activityJSON.put("activityId", historicActivityInstance.getActivityId());
                activityJSON.put("activityName", historicActivityInstance.getActivityName() != null ? historicActivityInstance.getActivityName() : "null");
                activityJSON.put("activityType", historicActivityInstance.getActivityType());
                activityJSON.put("startTime", RequestUtil.dateToString(historicActivityInstance.getStartTime()));
                if(historicActivityInstance.getEndTime() == null) {
                    activityJSON.put("completed", false);
                } else {
                    activityJSON.put("completed", true);
                    activityJSON.put("endTime", RequestUtil.dateToString(historicActivityInstance.getEndTime()));
                    activityJSON.put("duration", historicActivityInstance.getDurationInMillis());
                }
                activitiesJSON.add(activityJSON);
            }
        }
    }

    private void addVariableList(String processInstanceId, ObjectNode responseJSON) {
        List<HistoricVariableInstance> variableList = historyService.createHistoricVariableInstanceQuery().processInstanceId(processInstanceId)
                .list();

        if(variableList != null && variableList.size() > 0) {
            ArrayNode variablesJSON = new ObjectMapper().createArrayNode();
            responseJSON.set("variables", variablesJSON);
            for (HistoricVariableInstance historicDetail : variableList) {
                ObjectNode variableJSON = new ObjectMapper().createObjectNode();
                variableJSON.put("variableName", historicDetail.getVariableName());
                variableJSON.put("variableValue", historicDetail.getValue().toString());
                variableJSON.put("variableType", historicDetail.getVariableTypeName());
               variableJSON.put("variableTaskId", historicDetail.getTaskId());
                variableJSON.put("time", RequestUtil.dateToString(historicDetail.getTime()));

                variablesJSON.add(variableJSON);
            }
        }
    }


}
