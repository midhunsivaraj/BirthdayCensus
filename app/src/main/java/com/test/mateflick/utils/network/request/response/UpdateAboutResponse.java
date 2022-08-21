package com.test.mateflick.utils.network.request.response;


import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "status",
        "About"
})
public class UpdateAboutResponse {

    @JsonProperty("status")
    private String status;
    @JsonProperty("About")
    private String about;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The status
     */
    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     *
     * @return
     * The about
     */
    @JsonProperty("About")
    public String getAbout() {
        return about;
    }

    /**
     *
     * @param about
     * The About
     */
    @JsonProperty("About")
    public void setAbout(String about) {
        this.about = about;
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

