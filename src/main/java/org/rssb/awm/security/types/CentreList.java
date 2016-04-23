package org.rssb.awm.security.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "GetCentresResult"
})
public class CentreList {

    @JsonProperty("GetCentresResult")
    private List<GetCentresResult> GetCentresResult = new ArrayList<GetCentresResult>();

    /**
     *
     * @return
     * The GetCentresResult
     */
    @JsonProperty("GetCentresResult")
    public List<GetCentresResult> getGetCentresResult() {
        return GetCentresResult;
    }

    /**
     *
     * @param GetCentresResult
     * The GetCentresResult
     */
    @JsonProperty("GetCentresResult")
    public void setGetCentresResult(List<GetCentresResult> GetCentresResult) {
        this.GetCentresResult = GetCentresResult;
    }

}