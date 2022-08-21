package com.test.mateflick.utils.network.request.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "status",
        "Coverimage"
})
public class UpdateCoverResponse {

    @JsonProperty("status")
    private String status;
    @JsonProperty("Coverimage")
    private String coverimage;

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
     * The coverimage
     */
    @JsonProperty("Coverimage")
    public String getCoverimage() {
        return coverimage;
    }

    /**
     *
     * @param coverimage
     * The Coverimage
     */
    @JsonProperty("Coverimage")
    public void setCoverimage(String coverimage) {
        this.coverimage = coverimage;
    }

}