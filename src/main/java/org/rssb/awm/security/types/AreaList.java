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
        "GetAreasResult"
})
public class AreaList {

    @JsonProperty("GetAreasResult")
    private List<GetAreasResult> GetAreasResult = new ArrayList<GetAreasResult>();

    /**
     *
     * @return
     * The GetAreasResult
     */
    @JsonProperty("GetAreasResult")
    public List<GetAreasResult> getGetAreasResult() {
        return GetAreasResult;
    }

    /**
     *
     * @param GetAreasResult
     * The GetAreasResult
     */
    @JsonProperty("GetAreasResult")
    public void setGetAreasResult(List<GetAreasResult> GetAreasResult) {
        this.GetAreasResult = GetAreasResult;
    }

}