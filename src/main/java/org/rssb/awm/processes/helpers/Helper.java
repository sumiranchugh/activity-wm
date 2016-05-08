package org.rssb.awm.processes.helpers;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Sumiran Chugh on 5/8/2016.
 *
 * @copyright atlas
 */
@Component
public class Helper {

    @Autowired
    RuntimeService runtimeService;

    public String getBusinessKeyFromDelegateTask(DelegateTask task) {
        ProcessInstanceQuery processInstanceQuery = runtimeService.createProcessInstanceQuery();
        ProcessInstance result = processInstanceQuery.processInstanceId(task.getProcessInstanceId()).singleResult();
        return result == null ? "" : result.getBusinessKey();
    }
}
