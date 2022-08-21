package com.test.mateflick.utils.network.request.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "user_id",
        "conversation_id",
        "name",
        "email",
        "image",
        "last_msg",
        "time"
})
public class GetMyChatsResponse {

    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("conversation_id")
    private String conversationId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("email")
    private String email;
    @JsonProperty("image")
    private String image;
    @JsonProperty("last_msg")
    private String lastMsg;
    @JsonProperty("time")
    private String time;

    /**
     * @return The userId
     */
    @JsonProperty("user_id")
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId The user_id
     */
    @JsonProperty("user_id")
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return The conversationId
     */
    @JsonProperty("conversation_id")
    public String getConversationId() {
        return conversationId;
    }

    /**
     * @param conversationId The conversation_id
     */
    @JsonProperty("conversation_id")
    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    /**
     * @return The name
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The email
     */
    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    /**
     * @param email The email
     */
    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return The image
     */
    @JsonProperty("image")
    public String getImage() {
        return image;
    }

    /**
     * @param image The image
     */
    @JsonProperty("image")
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * @return The lastMsg
     */
    @JsonProperty("last_msg")
    public String getLastMsg() {
        return lastMsg;
    }

    /**
     * @param lastMsg The last_msg
     */
    @JsonProperty("last_msg")
    public void setLastMsg(String lastMsg) {
        this.lastMsg = lastMsg;
    }

    /**
     * @return The time
     */
    @JsonProperty("time")
    public String getTime() {
        return time;
    }

    /**
     * @param time The time
     */
    @JsonProperty("time")
    public void setTime(String time) {
        this.time = time;
    }

}
