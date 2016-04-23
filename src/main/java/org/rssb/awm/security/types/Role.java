package org.rssb.awm.security.types;


import com.fasterxml.jackson.annotation.*;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "Permissions",
        "RoleDescription",
        "RoleId",
        "RoleName"
})
public class Role {

    @JsonProperty("Permissions")
    private List<Permission> Permissions = new ArrayList<Permission>();
    @JsonProperty("RoleDescription")
    private String RoleDescription;
    @JsonProperty("RoleId")
    private int RoleId;
    @JsonProperty("RoleName")
    private String RoleName;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The Permissions
     */
    @JsonProperty("Permissions")
    public List<Permission> getPermissions() {
        return Permissions;
    }

    /**
     * @param Permissions The Permissions
     */
    @JsonProperty("Permissions")
    public void setPermissions(List<Permission> Permissions) {
        this.Permissions = Permissions;
    }

    /**
     * @return The RoleDescription
     */
    @JsonProperty("RoleDescription")
    public String getRoleDescription() {
        return RoleDescription;
    }

    /**
     * @param RoleDescription The RoleDescription
     */
    @JsonProperty("RoleDescription")
    public void setRoleDescription(String RoleDescription) {
        this.RoleDescription = RoleDescription;
    }

    /**
     * @return The RoleId
     */
    @JsonProperty("RoleId")
    public int getRoleId() {
        return RoleId;
    }

    /**
     * @param RoleId The RoleId
     */
    @JsonProperty("RoleId")
    public void setRoleId(int RoleId) {
        this.RoleId = RoleId;
    }

    /**
     * @return The RoleName
     */
    @JsonProperty("RoleName")
    public String getRoleName() {
        return RoleName;
    }

    /**
     * @param RoleName The RoleName
     */
    @JsonProperty("RoleName")
    public void setRoleName(String RoleName) {
        this.RoleName = RoleName;
    }


    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
