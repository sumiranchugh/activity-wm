package org.rssb.awm.common.types;

import org.activiti.rest.service.api.form.RestFormProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sumiran Chugh on 4/5/2016.
 *
 * @copyright atlas
 */
public class TaskRequest extends org.activiti.rest.service.api.runtime.task.TaskRequest {

    private String comments;

   // private Map<String,String> variables = new HashMap<>();

    protected List<RestFormProperty> properties = new ArrayList<>();

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

/*    public Map<String, String> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, String> variables) {
        this.variables = variables;
    }*/

    public List<RestFormProperty> getProperties() {
        return properties;
    }

    public void setProperties(List<RestFormProperty> properties) {
        this.properties = properties;
    }
}
