package com.test.mateflick.utils.network.request.response;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.test.mateflick.utils.network.request.response.model.Event;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "status",
        "Events"
})
public class GetMyEventsResponse {

    @JsonProperty("status")
    private Integer status;
    @JsonProperty("Events")
    private List<Event> events = new ArrayList<Event>();

    /**
     *
     * @return
     * The status
     */
    @JsonProperty("status")
    public Integer getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    @JsonProperty("status")
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     *
     * @return
     * The events
     */
    @JsonProperty("Events")
    public List<Event> getEvents() {
        return events;
    }

    /**
     *
     * @param events
     * The Events
     */
    @JsonProperty("Events")
    public void setEvents(List<Event> events) {
        this.events = events;
    }

}