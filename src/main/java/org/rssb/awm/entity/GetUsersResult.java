package org.rssb.awm.entity;

/**
 * Created by aman on 24/4/16.
 */
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

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
public class GetUsersResult {

  @JsonProperty("AreaId")
  private Integer AreaId;
  @JsonProperty("CentreId")
  private Integer CentreId;
  @JsonProperty("DeptName")
  private String DeptName;
  @JsonProperty("Permissions")
  private String Permissions;
  @JsonProperty("Roles")
  private String Roles;
  @JsonProperty("UserId")
  private String UserId;
  @JsonProperty("UserName")
  private String UserName;
  @JsonProperty("ZonalSewadarId")
  private Integer ZonalSewadarId;
  @JsonIgnore
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  /**
   *
   * @return
   * The AreaId
   */
  @JsonProperty("AreaId")
  public Integer getAreaId() {
    return AreaId;
  }

  /**
   *
   * @param AreaId
   * The AreaId
   */
  @JsonProperty("AreaId")
  public void setAreaId(Integer AreaId) {
    this.AreaId = AreaId;
  }

  /**
   *
   * @return
   * The CentreId
   */
  @JsonProperty("CentreId")
  public Integer getCentreId() {
    return CentreId;
  }

  /**
   *
   * @param CentreId
   * The CentreId
   */
  @JsonProperty("CentreId")
  public void setCentreId(Integer CentreId) {
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
  public void setDeptName(String DeptName) {
    this.DeptName = DeptName;
  }

  /**
   *
   * @return
   * The Permissions
   */
  @JsonProperty("Permissions")
  public String getPermissions() {
    return Permissions;
  }

  /**
   *
   * @param Permissions
   * The Permissions
   */
  @JsonProperty("Permissions")
  public void setPermissions(String Permissions) {
    this.Permissions = Permissions;
  }

  /**
   *
   * @return
   * The Roles
   */
  @JsonProperty("Roles")
  public String getRoles() {
    return Roles;
  }

  /**
   *
   * @param Roles
   * The Roles
   */
  @JsonProperty("Roles")
  public void setRoles(String Roles) {
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
  public Integer getZonalSewadarId() {
    return ZonalSewadarId;
  }

  /**
   *
   * @param ZonalSewadarId
   * The ZonalSewadarId
   */
  @JsonProperty("ZonalSewadarId")
  public void setZonalSewadarId(Integer ZonalSewadarId) {
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
