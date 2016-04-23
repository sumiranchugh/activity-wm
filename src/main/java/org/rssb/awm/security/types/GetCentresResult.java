package org.rssb.awm.security.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "CentreId",
        "CentreName"
})
public class GetCentresResult {

    @JsonProperty("CentreId")
    private String CentreId;
    @JsonProperty("CentreName")
    private String CentreName;

    /**
     *
     * @return
     * The CentreId
     */
    @JsonProperty("CentreId")
    public String getCentreId() {
        return CentreId;
    }

    /**
     *
     * @param CentreId
     * The CentreId
     */
    @JsonProperty("CentreId")
    public void setCentreId(String CentreId) {
        this.CentreId = CentreId;
    }

    /**
     *
     * @return
     * The CentreName
     */
    @JsonProperty("CentreName")
    public String getCentreName() {
        return CentreName;
    }

    /**
     *
     * @param CentreName
     * The CentreName
     */
    @JsonProperty("CentreName")
    public void setCentreName(String CentreName) {
        this.CentreName = CentreName;
    }

}