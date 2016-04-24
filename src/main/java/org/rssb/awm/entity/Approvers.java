package org.rssb.awm.entity;

/**
 * Created by aman on 24/4/16.
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    "GetUsersResult"
})
public class Approvers {

  @JsonProperty("GetUsersResult")
  private List<GetUsersResult> GetUsersResult = new ArrayList<GetUsersResult>();
  @JsonIgnore
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  /**
   *
   * @return
   * The GetUsersResult
   */
  @JsonProperty("GetUsersResult")
  public List<GetUsersResult> getGetUsersResult() {
    return GetUsersResult;
  }

  /**
   *
   * @param GetUsersResult
   * The GetUsersResult
   */
  @JsonProperty("GetUsersResult")
  public void setGetUsersResult(List<GetUsersResult> GetUsersResult) {
    this.GetUsersResult = GetUsersResult;
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