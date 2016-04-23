package org.rssb.awm.processes.listeners;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateTask;
import org.rssb.awm.common.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * Created by Sumiran Chugh on 3/28/2016.
 *
 * @copyright atlas
 */
@Component("preBimsListner")
public class PreBimsListner {

    RestTemplate restTemplate = new RestTemplate();
    @Value("${bims.prebims.basicauth.username}")
    String basicauthusername;

    @Value("${bims.prebims.basicauth.passsword}")
    String basicauthpassword;

    @Value("${bims.prebims.fetchusers}")
    String fetchuserurl;

    @Value("${bims.prebims.notify}")
    String notifyurl;

    @Value("${bims.prebims.attachmentType}")
    String attachmentType;

    @Value("${bims.prebims.attachmentName}")
    String attachmentName;

    @Value("${bims.prebims.attachmentDesc}")
    String attachmentDesc;

    @Autowired
    TaskService taskService;

    @Autowired
    RuntimeService runtimeService;

    RepositoryService repositoryService;

    public void notifyBims(DelegateTask task, String eventName) {
        Map<String, String> params = new HashMap<>();
        params.put("processInstanceId", task.getProcessInstanceId());
        params.put("processDefinitionId", task.getProcessDefinitionId());
     //   params.putAll(task.getVariables());

        //HttpEntity<String> entity = Util.addTokenToHeader(basicauthusername, basicauthpassword);
        /*MultiValueMap<String,String> m =new LinkedMultiValueMap<>();
        m.put("Authorization", );
        m.put("Accept")*/
        HttpEntity<Map<String,String>> param = new HttpEntity<>(params,new LinkedMultiValueMap<>());
        //restTemplate.put(notifyurl,params,params);
        ResponseEntity<Object> response = restTemplate.exchange(notifyurl, HttpMethod.PUT, param, Object.class, params);
       /* if (response.getStatusCode() != HttpStatus.OK) {
            throw new ActivitiException("Error notifying bims");
        }*/
    }

    public void getCandidateUsers(DelegateTask task, String eventName, int level) {

       /* try {

           if( taskService.getTaskAttachments(task.getId())==null) {
            runtimeService.
               byte[] content = Util.loadFile((String) task.getVariable("attachment"));
               taskService.createAttachment(attachmentType, task.getId(), task.getProcessInstanceId(), attachmentName, attachmentDesc, new ByteArrayInputStream(content));
           }
        } catch (IOException e) {
            Map<String,Object> map =new HashMap<>();
            map.put("areapproval",new Boolean(false));
            map.put("sspproval",new Boolean(false));
            taskService.claim(task.getId(), "SYSTEM");
            taskService.complete(task.getId(),map);
        }*/

        HttpEntity<String> entity = Util.addTokenToHeader(basicauthusername, basicauthpassword);

        ResponseEntity<Users> response = restTemplate.exchange(fetchuserurl, HttpMethod.POST, entity, Users.class, level);

        if (response.getStatusCode() != HttpStatus.OK)
            throw new ActivitiException("Error etching user list");

        List<String> users = new ArrayList<>();

        Map<String, Set<String>> userList = response.getBody().getMap();
        boolean a =false;
        if(userList.size() > 0)
        switch (LEVEL.getLevel(level)) {

            case CENTER:
               a =  userList.containsKey(LEVEL.CENTER.toString()) ?
                       users.addAll(userList.get(LEVEL.CENTER.toString())) : false;
                break;
            case AREA:
                a =  userList.containsKey(LEVEL.AREA.toString()) ?
                        users.addAll(userList.get(LEVEL.AREA.toString())) : false;
                break;
            case ZONE:
                a =  userList.containsKey(LEVEL.ZONE.toString()) ?
                        users.addAll(userList.get(LEVEL.ZONE.toString())) : false;
                break;
        }
        if (users.isEmpty())
            throw new ActivitiException("Users not found for task");
//        taskService.getIdentityLinksForTask("").forEach((p)->p.getGroupId());
        task.addCandidateUsers(users);
        System.out.println("in here");
    }

    public static class Users{

        Map<String,Set<String>> map = new HashMap<>();

        public Map<String, Set<String>> getMap() {
            return map;
        }

        public void setMap(Map<String, Set<String>> map) {
            this.map = map;
        }
    }






}


