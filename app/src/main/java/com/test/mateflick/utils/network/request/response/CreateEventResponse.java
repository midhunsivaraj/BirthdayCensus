package com.test.mateflick.utils.network.request.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "_id",
        "status",
        "message"
})
public class CreateEventResponse {

    @JsonProperty("_id")
    private String id;
    @JsonProperty("status")
    private String status;
    @JsonProperty("message")
    private String message;

    /**
     *
     * @return
     * The id
     */
    @JsonProperty("_id")
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The _id
     */
    @JsonProperty("_id")
    public void setId(String id) {
        this.id = id;
    }

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
     * The message
     */
    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    /**
     *
     * @param message
     * The message
     */
    @JsonProperty("message")
    public void setMessage(String message) {
        this.message = message;
    }

}
