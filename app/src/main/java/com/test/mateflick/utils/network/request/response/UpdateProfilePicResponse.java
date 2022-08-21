package com.test.mateflick.utils.network.request.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "status",
        "Profileimage"
})
public class UpdateProfilePicResponse {

    @JsonProperty("status")
    private String status;
    @JsonProperty("Profileimage")
    private String profileimage;

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
     * The profileimage
     */
    @JsonProperty("Profileimage")
    public String getProfileimage() {
        return profileimage;
    }

    /**
     *
     * @param profileimage
     * The Profileimage
     */
    @JsonProperty("Profileimage")
    public void setProfileimage(String profileimage) {
        this.profileimage = profileimage;
    }

}


