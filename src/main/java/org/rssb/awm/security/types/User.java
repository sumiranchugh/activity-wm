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
        "AreaId",
        "CentreId",
        "DeptName",
        "Permissions",
        "Roles",
        "UserId",
        "UserName",
        "ZonalSewadarId"
})
public class User {

    @JsonProperty("AreaId")
    private int AreaId;
    @JsonProperty("CentreId")
    private int CentreId;
    @JsonProperty("DeptName")
    private Object DeptName;
    @JsonProperty("Permissions")
    private List<Object> Permissions = new ArrayList<Object>();
    @JsonProperty("Roles")
    private List<Role> Roles = new ArrayList<Role>();
    @JsonProperty("UserId")
    private String UserId;
    @JsonProperty("UserName")
    private String UserName;
    @JsonProperty("ZonalSewadarId")
    private int ZonalSewadarId;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public User(String userId, String userName, List<Role> roles, Map<String, Object> additionalProperties) {
        UserId = userId;
        UserName = userName;
        Roles = roles;
        this.additionalProperties = additionalProperties;
    }

    public User(){
    }

    /**
     *
     * @return
     * The AreaId
     */
    @JsonProperty("AreaId")
    public int getAreaId() {
        return AreaId;
    }

    /**
     *
     * @param AreaId
     * The AreaId
     */
    @JsonProperty("AreaId")
    public void setAreaId(int AreaId) {
        this.AreaId = AreaId;
    }

    /**
     *
     * @return
     * The CentreId
     */
    @JsonProperty("CentreId")
    public int getCentreId() {
        return CentreId;
    }

    /**
     *
     * @param CentreId
     * The CentreId
     */
    @JsonProperty("CentreId")
    public void setCentreId(int CentreId) {
        this.CentreId = CentreId;
    }

    /**
     *
     * @return
     * The DeptName
     */
    @JsonProperty("DeptName")
    public Object getDeptName() {
        return DeptName;
    }

    /**
     *
     * @param DeptName
     * The DeptName
     */
    @JsonProperty("DeptName")
    public void setDeptName(Object DeptName) {
        this.DeptName = DeptName;
    }

    /**
     *
     * @return
     * The Permissions
     */
    @JsonProperty("Permissions")
    public List<Object> getPermissions() {
        return Permissions;
    }

    /**
     *
     * @param Permissions
     * The Permissions
     */
    @JsonProperty("Permissions")
    public void setPermissions(List<Object> Permissions) {
        this.Permissions = Permissions;
    }

    /**
     *
     * @return
     * The Roles
     */
    @JsonProperty("Roles")
    public List<Role> getRoles() {
        return Roles;
    }

    /**
     *
     * @param Roles
     * The Roles
     */
    @JsonProperty("Roles")
    public void setRoles(List<Role> Roles) {
        this.Roles = Roles;
    }

    /**
     *
     * @return
     * The UserId
     */
    @JsonProperty("UserId")
    public String getUserId() {
        return UserId;
    }

    /**
     *
     * @param UserId
     * The UserId
     */
    @JsonProperty("UserId")
    public void setUserId(String UserId) {
        this.UserId = UserId;
    }

    /**
     *
     * @return
     * The UserName
     */
    @JsonProperty("UserName")
    public String getUserName() {
        return UserName;
    }

    /**
     *
     * @param UserName
     * The UserName
     */
    @JsonProperty("UserName")
    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    /**
     *
     * @return
     * The ZonalSewadarId
     */
    @JsonProperty("ZonalSewadarId")
    public int getZonalSewadarId() {
        return ZonalSewadarId;
    }

    /**
     *
     * @param ZonalSewadarId
     * The ZonalSewadarId
     */
    @JsonProperty("ZonalSewadarId")
    public void setZonalSewadarId(int ZonalSewadarId) {
        this.ZonalSewadarId = ZonalSewadarId;
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