package com.test.mateflick.utils.network.request.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "read_status",
        "c_id_fk",
        "conversation_id",
        "date",
        "time",
        "reply",
        "user_id_fk",
        "file_name",
        "name",
        "email",
        "image"
})
public class ChatItem {

    @JsonProperty("read_status")
    private Integer readStatus;
    @JsonProperty("c_id_fk")
    private String cIdFk;
    @JsonProperty("conversation_id")
    private String conversationId;
    @JsonProperty("date")
    private String date;
    @JsonProperty("time")
    private String time;
    @JsonProperty("reply")
    private String reply;
    @JsonProperty("user_id_fk")
    private String userIdFk;
    @JsonProperty("file_name")
    private String fileName;
    @JsonProperty("name")
    private String name;
    @JsonProperty("email")
    private String email;
    @JsonProperty("image")
    private String image;

    /**
     *
     * @return
     * The readStatus
     */
    @JsonProperty("read_status")
    public Integer getReadStatus() {
        return readStatus;
    }

    /**
     *
     * @param readStatus
     * The read_status
     */
    @JsonProperty("read_status")
    public void setReadStatus(Integer readStatus) {
        this.readStatus = readStatus;
    }

    /**
     *
     * @return
     * The cIdFk
     */
    @JsonProperty("c_id_fk")
    public String getCIdFk() {
        return cIdFk;
    }

    /**
     *
     * @param cIdFk
     * The c_id_fk
     */
    @JsonProperty("c_id_fk")
    public void setCIdFk(String cIdFk) {
        this.cIdFk = cIdFk;
    }

    /**
     *
     * @return
     * The conversationId
     */
    @JsonProperty("conversation_id")
    public String getConversationId() {
        return conversationId;
    }

    /**
     *
     * @param conversationId
     * The conversation_id
     */
    @JsonProperty("conversation_id")
    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    /**
     *
     * @return
     * The date
     */
    @JsonProperty("date")
    public String getDate() {
        return date;
    }

    /**
     *
     * @param date
     * The date
     */
    @JsonProperty("date")
    public void setDate(String date) {
        this.date = date;
    }

    /**
     *
     * @return
     * The time
     */
    @JsonProperty("time")
    public String getTime() {
        return time;
    }

    /**
     *
     * @param time
     * The time
     */
    @JsonProperty("time")
    public void setTime(String time) {
        this.time = time;
    }

    /**
     *
     * @return
     * The reply
     */
    @JsonProperty("reply")
    public String getReply() {
        return reply;
    }

    /**
     *
     * @param reply
     * The reply
     */
    @JsonProperty("reply")
    public void setReply(String reply) {
        this.reply = reply;
    }

    /**
     *
     * @return
     * The userIdFk
     */
    @JsonProperty("user_id_fk")
    public String getUserIdFk() {
        return userIdFk;
    }

    /**
     *
     * @param userIdFk
     * The user_id_fk
     */
    @JsonProperty("user_id_fk")
    public void setUserIdFk(String userIdFk) {
        this.userIdFk = userIdFk;
    }

    /**
     *
     * @return
     * The fileName
     */
    @JsonProperty("file_name")
    public String getFileName() {
        return fileName;
    }

    /**
     *
     * @param fileName
     * The file_name
     */
    @JsonProperty("file_name")
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     *
     * @return
     * The name
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The email
     */
    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     * The email
     */
    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     * The image
     */
    @JsonProperty("image")
    public String getImage() {
        return image;
    }

    /**
     *
     * @param image
     * The image
     */
    @JsonProperty("image")
    public void setImage(String image) {
        this.image = image;
    }

}