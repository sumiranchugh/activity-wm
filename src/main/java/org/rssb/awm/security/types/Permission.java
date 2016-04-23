package org.rssb.awm.security.types;
import com.fasterxml.jackson.annotation.*;

import javax.annotation.Generated;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "PermissionDescription",
        "PermissionId",
        "PermissionName"
})
public class Permission {

    @JsonProperty("PermissionDescription")
    private String PermissionDescription;
    @JsonProperty("PermissionId")
    private int PermissionId;
    @JsonProperty("PermissionName")
    private String PermissionName;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The PermissionDescription
     */
    @JsonProperty("PermissionDescription")
    public String getPermissionDescription() {
        return PermissionDescription;
    }

    /**
     * @param PermissionDescription The PermissionDescription
     */
    @JsonProperty("PermissionDescription")
    public void setPermissionDescription(String PermissionDescription) {
        this.PermissionDescription = PermissionDescription;
    }

    /**
     * @return The PermissionId
     */
    @JsonProperty("PermissionId")
    public int getPermissionId() {
        return PermissionId;
    }

    /**
     * @param PermissionId The PermissionId
     */
    @JsonProperty("PermissionId")
    public void setPermissionId(int PermissionId) {
        this.PermissionId = PermissionId;
    }

    /**
     * @return The PermissionName
     */
    @JsonProperty("PermissionName")
    public String getPermissionName() {
        return PermissionName;
    }

    /**
     * @param PermissionName The PermissionName
     */
    @JsonProperty("PermissionName")
    public void setPermissionName(String PermissionName) {
        this.PermissionName = PermissionName;
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