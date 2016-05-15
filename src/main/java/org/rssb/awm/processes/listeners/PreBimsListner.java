package org.rssb.awm.processes.listeners;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateTask;
import org.rssb.awm.common.Constants;
import org.rssb.awm.common.types.NotifyRequest;
import org.rssb.awm.entity.Approvers;
import org.rssb.awm.entity.GetUsersResult;
import org.rssb.awm.processes.helpers.Helper;
import org.rssb.awm.security.types.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

    @Value("${bims.token.service}")
    String bimsTokenurl;

    @Value("${bims.approval.users}")
    String bimsApprovalsUsers;


    @Autowired
    TaskService taskService;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    Helper helper;

    RepositoryService repositoryService;

    private long tokenCreationTime = 0l;
    private String bimsToken = "";

    public void notifyBims(DelegateTask task, String eventName) {
        NotifyRequest params = new NotifyRequest();
        Object obj = null;
        params.setZonalSewadarId(Integer.valueOf(task.getVariable(Constants.ZONALSWID).toString()));
        params.setWorkflowInstanceId(task.getProcessInstanceId());

        boolean approval = false;
        if ((obj = task.getVariable(Constants.SSAPPROVAL)) != null) {
            approval = Boolean.valueOf(obj.toString());
        } else if ((obj = task.getVariable(Constants.ASAPPROVAL)) != null) {
            approval = Boolean.valueOf(obj.toString());
            if (approval)
                return; //nothin to do if approved at area secretary level
        }
        params.setStatus(approval == true ? "Approved" : "Rejected");
        params.setUpdatedBy(getLoggedInUser());
        params.setRemarks("Task " + taskService.getProcessInstanceComments(task.getProcessInstanceId()));
        //Add Token to header
        String token = getValidBimsToken();
        HttpHeaders headers = new HttpHeaders();
        headers.set("access_token", token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        System.out.println(params);

        HttpEntity<NotifyRequest> entity = new HttpEntity<NotifyRequest>( params,headers);

        try {
            ResponseEntity<Object> response = restTemplate.exchange(notifyurl, HttpMethod.POST, entity, Object.class);
        }
        catch (Exception e) {
            throw new ActivitiException("Error notifying Bims");
        }

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
                userss.add(String.valueOf(approver.getZonalSewadarId()));
                System.out.println(approver.getZonalSewadarId());
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

    private Integer getLoggedInUser() {
    UserDetails usd = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return usd.getUser().getZonalSewadarId();
}


}


