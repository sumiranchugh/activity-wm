package org.rssb.awm.security.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "AreaId",
        "AreaName",
        "Areacode",
        "IsActive",
        "ZoneId"
})
public class GetAreasResult {

    @JsonProperty("AreaId")
    private String AreaId;
    @JsonProperty("AreaName")
    private String AreaName;
    @JsonProperty("Areacode")
    private Object Areacode;
    @JsonProperty("IsActive")
    private boolean IsActive;
    @JsonProperty("ZoneId")
    private long ZoneId;

    /**
     *
     * @return
     * The AreaId
     */
    @JsonProperty("AreaId")
    public String getAreaId() {
        return AreaId;
    }

    /**
     *
     * @param AreaId
     * The AreaId
     */
    @JsonProperty("AreaId")
    public void setAreaId(String AreaId) {
        this.AreaId = AreaId;
    }

    /**
     *
     * @return
     * The AreaName
     */
    @JsonProperty("AreaName")
    public String getAreaName() {
        return AreaName;
    }

    /**
     *
     * @param AreaName
     * The AreaName
     */
    @JsonProperty("AreaName")
    public void setAreaName(String AreaName) {
        this.AreaName = AreaName;
    }

    /**
     *
     * @return
     * The Areacode
     */
    @JsonProperty("Areacode")
    public Object getAreacode() {
        return Areacode;
    }

    /**
     *
     * @param Areacode
     * The Areacode
     */
    @JsonProperty("Areacode")
    public void setAreacode(Object Areacode) {
        this.Areacode = Areacode;
    }

    /**
     *
     * @return
     * The IsActive
     */
    @JsonProperty("IsActive")
    public boolean isIsActive() {
        return IsActive;
    }

    /**
     *
     * @param IsActive
     * The IsActive
     */
    @JsonProperty("IsActive")
    public void setIsActive(boolean IsActive) {
        this.IsActive = IsActive;
    }

    /**
     *
     * @return
     * The ZoneId
     */
    @JsonProperty("ZoneId")
    public long getZoneId() {
        return ZoneId;
    }

    /**
     *
     * @param ZoneId
     * The ZoneId
     */
    @JsonProperty("ZoneId")
    public void setZoneId(long ZoneId) {
        this.ZoneId = ZoneId;
    }

}