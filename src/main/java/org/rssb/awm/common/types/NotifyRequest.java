package org.rssb.awm.common.types;

import com.fasterxml.jackson.annotation.*;

import javax.annotation.Generated;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sumiran Chugh on 4/30/2016.
 *
 * @copyright atlas
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "ZonalSewadarId",
        "WorkflowInstanceId",
        "Status",
        "UpdatedBy",
        "Remarks"
})
public class NotifyRequest {



        @JsonProperty("ZonalSewadarId")
        private Integer ZonalSewadarId;
        @JsonProperty("WorkflowInstanceId")
        private String WorkflowInstanceId;
        @JsonProperty("Status")
        private String Status;
        @JsonProperty("UpdatedBy")
        private Integer UpdatedBy;
        @JsonProperty("Remarks")
        private String Remarks;
        @JsonIgnore
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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

        /**
         *
         * @return
         * The WorkflowInstanceId
         */
        @JsonProperty("WorkflowInstanceId")
        public String getWorkflowInstanceId() {
            return WorkflowInstanceId;
        }

        /**
         *
         * @param WorkflowInstanceId
         * The WorkflowInstanceId
         */
        @JsonProperty("WorkflowInstanceId")
        public void setWorkflowInstanceId(String WorkflowInstanceId) {
            this.WorkflowInstanceId = WorkflowInstanceId;
        }

        /**
         *
         * @return
         * The Status
         */
        @JsonProperty("Status")
        public String getStatus() {
            return Status;
        }

        /**
         *
         * @param Status
         * The Status
         */
        @JsonProperty("Status")
        public void setStatus(String Status) {
            this.Status = Status;
        }

        /**
         *
         * @return
         * The UpdatedBy
         */
        @JsonProperty("UpdatedBy")
        public Integer getUpdatedBy() {
            return UpdatedBy;
        }

        /**
         *
         * @param UpdatedBy
         * The UpdatedBy
         */
        @JsonProperty("UpdatedBy")
        public void setUpdatedBy(Integer UpdatedBy) {
            this.UpdatedBy = UpdatedBy;
        }

        /**
         *
         * @return
         * The Remarks
         */
        @JsonProperty("Remarks")
        public String getRemarks() {
            return Remarks;
        }

        /**
         *
         * @param Remarks
         * The Remarks
         */
        @JsonProperty("Remarks")
        public void setRemarks(String Remarks) {
            this.Remarks = Remarks;
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

