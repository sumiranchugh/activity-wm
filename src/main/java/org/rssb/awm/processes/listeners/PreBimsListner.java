package org.rssb.awm.processes.listeners;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateTask;
import org.rssb.awm.entity.Approvers;
import org.rssb.awm.entity.GetUsersResult;
import org.rssb.awm.security.types.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

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


    @Value("${bims.prebims.user}")
    String bimsUser;

    @Value("${bims.prebims.pass}")
    String bimsPass;

    @Value("${bims.token}")
    String bimsTokenurl;

    @Value("${bims.approval.users}")
    String bimsApprovalsUsers;


    @Autowired
    TaskService taskService;

    @Autowired
    RuntimeService runtimeService;

    RepositoryService repositoryService;

    private long tokenCreationTime = 0l;
    private String bimsToken = "";


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

    public void getCandidateUsers(DelegateTask task, int level) {
        Map<String,Object> inputMap = task.getVariables();
        if( inputMap.get("areaid") !=null  ||  inputMap.get("areaid")!=null) {

            String areaId = inputMap.get("areaid").toString();
            String centerId = inputMap.get("centerid").toString();
            List<String> approvers = getApprovalIds(areaId, centerId, level);
            task.addCandidateUsers(approvers);
        }
        else throw new ActivitiException("error getting area / center data");

    }

    public List<String> getApprovalIds(String area , String center , int level){

        List<String> userss = new ArrayList();
        String token = getValidBimsToken();
        HttpHeaders headers = new HttpHeaders();
        headers.set("access_token", token);
        HttpEntity<String> entity = new HttpEntity<String>( headers);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(bimsApprovalsUsers)
            .queryParam("areaId",area)
            .queryParam("centerId",center)
            .queryParam("roleId",level);

         ResponseEntity<Approvers> response = restTemplate.exchange(builder.build().encode().toUri(),
                    HttpMethod.GET, entity, Approvers.class);

        if(response.getStatusCode() != HttpStatus.OK ){
            throw new ActivitiException("Error fetching user list");
        }
        else {

            for(GetUsersResult approver : response.getBody().getGetUsersResult()){
                    userss.add(approver.getUserId());
            }
        }
        return userss;
    }

    public String getValidBimsToken(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.set("userid", bimsUser);
        headers.set("password", bimsPass);
        HttpEntity<String> entity = new HttpEntity<String>( headers);
        if(bimsToken==null || bimsToken.trim().isEmpty()||System.currentTimeMillis() - tokenCreationTime > 7200000 ){
            ResponseEntity<String> response = restTemplate.exchange(bimsTokenurl, HttpMethod.GET, entity, String.class);

            String result = response.getBody();
                return result.substring(1,result.length()-1);
        }

        else return bimsToken;
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


