package com.test.mateflick.utils.network.request.response.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "_id",
        "name",
        "email",
        "dob",
        "country",
        "image",
        "about",
        "longitude",
        "latitude",
        "Distance"
})
public class User {

    @JsonProperty("_id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("email")
    private String email;
    @JsonProperty("dob")
    private String dob;
    @JsonProperty("country")
    private String country;
    @JsonProperty("image")
    private String image;
    @JsonProperty("about")
    private String about;
    @JsonProperty("longitude")
    private String longitude;
    @JsonProperty("latitude")
    private String latitude;
    @JsonProperty("Distance")
    private Double distance;

    /**
     * @return The id
     */
    @JsonProperty("_id")
    public String getId() {
        return id;
    }

    /**
     * @param id The _id
     */
    @JsonProperty("_id")
    public void setId(String id) {
        this.id = id;
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
     * @return The dob
     */
    @JsonProperty("dob")
    public String getDob() {
        return dob;
    }

    /**
     * @param dob The dob
     */
    @JsonProperty("dob")
    public void setDob(String dob) {
        this.dob = dob;
    }

    /**
     * @return The country
     */
    @JsonProperty("country")
    public String getCountry() {
        return country;
    }

    /**
     * @param country The country
     */
    @JsonProperty("country")
    public void setCountry(String country) {
        this.country = country;
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
     * @return The about
     */
    @JsonProperty("about")
    public String getAbout() {
        return about;
    }

    /**
     * @param about The about
     */
    @JsonProperty("about")
    public void setAbout(String about) {
        this.about = about;
    }

    /**
     * @return The longitude
     */
    @JsonProperty("longitude")
    public String getLongitude() {
        return longitude;
    }

    /**
     * @param longitude The longitude
     */
    @JsonProperty("longitude")
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     * @return The latitude
     */
    @JsonProperty("latitude")
    public String getLatitude() {
        return latitude;
    }

    /**
     * @param latitude The latitude
     */
    @JsonProperty("latitude")
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     * @return The distance
     */
    @JsonProperty("Distance")
    public Double getDistance() {
        return distance;
    }

    /**
     * @param distance The Distance
     */
    @JsonProperty("Distance")
    public void setDistance(Double distance) {
        this.distance = distance;
    }

}
