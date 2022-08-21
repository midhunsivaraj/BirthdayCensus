package com.test.mateflick.utils.network.request.response;


import android.os.Parcel;
import android.os.Parcelable;

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
        "_id",
        "longitude",
        "latitude",
        "cover",
        "about",
        "status",
        "image",
        "country",
        "dob",
        "password",
        "email",
        "name",
        "__v",
        "date_created"
})
public class LoginResponse implements Parcelable{

    @JsonProperty("_id")
    private String id;
    @JsonProperty("longitude")
    private String longitude;
    @JsonProperty("latitude")
    private String latitude;
    @JsonProperty("cover")
    private String cover;
    @JsonProperty("about")
    private String about;
    @JsonProperty("status")
    private Integer status;
    @JsonProperty("image")
    private String image;
    @JsonProperty("country")
    private String country;
    @JsonProperty("dob")
    private String dob;
    @JsonProperty("password")
    private String password;
    @JsonProperty("email")
    private String email;
    @JsonProperty("name")
    private String name;
    @JsonProperty("__v")
    private Integer v;
    @JsonProperty("date_created")
    private String dateCreated;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    @JsonIgnore
    public String message;

    public LoginResponse() {
    }

    @JsonIgnore
    public LoginResponse(Parcel in) {
        id = in.readString();
        longitude = in.readString();
        latitude = in.readString();
        cover = in.readString();
        about = in.readString();
        image = in.readString();
        country = in.readString();
        dob = in.readString();
        password = in.readString();
        email = in.readString();
        name = in.readString();
        dateCreated = in.readString();
        message = in.readString();
    }

    @JsonIgnore
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(longitude);
        dest.writeString(latitude);
        dest.writeString(cover);
        dest.writeString(about);
        dest.writeString(image);
        dest.writeString(country);
        dest.writeString(dob);
        dest.writeString(password);
        dest.writeString(email);
        dest.writeString(name);
        dest.writeString(dateCreated);
        dest.writeString(message);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @JsonIgnore
    public static final Creator<LoginResponse> CREATOR = new Creator<LoginResponse>() {
        @Override
        public LoginResponse createFromParcel(Parcel in) {
            return new LoginResponse(in);
        }

        @Override
        public LoginResponse[] newArray(int size) {
            return new LoginResponse[size];
        }
    };

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
     * @return The cover
     */
    @JsonProperty("cover")
    public String getCover() {
        return cover;
    }

    /**
     * @param cover The cover
     */
    @JsonProperty("cover")
    public void setCover(String cover) {
        this.cover = cover;
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
     * @return The status
     */
    @JsonProperty("status")
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status The status
     */
    @JsonProperty("status")
    public void setStatus(Integer status) {
        this.status = status;
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
     * @return The password
     */
    @JsonProperty("password")
    public String getPassword() {
        return password;
    }

    /**
     * @param password The password
     */
    @JsonProperty("password")
    public void setPassword(String password) {
        this.password = password;
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
     * @return The v
     */
    @JsonProperty("__v")
    public Integer getV() {
        return v;
    }

    /**
     * @param v The __v
     */
    @JsonProperty("__v")
    public void setV(Integer v) {
        this.v = v;
    }

    /**
     * @return The dateCreated
     */
    @JsonProperty("date_created")
    public String getDateCreated() {
        return dateCreated;
    }

    /**
     * @param dateCreated The date_created
     */
    @JsonProperty("date_created")
    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
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