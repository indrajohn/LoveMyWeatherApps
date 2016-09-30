package com.lovemyweather.lovemyweather.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

/**
 * Created by John on 25/09/2016.
 */
@Generated("org.jsonschema2pojo")
public class Sys {
    @SerializedName("type")
    @Expose
    private int type;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("message")
    @Expose
    private float message;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("sunrise")
    @Expose
    private int sunrise;
    @SerializedName("sunset")
    @Expose
    private int sunset;

    /**
     * No args constructor for use in serialization
     *
     */
    public Sys() {
    }

    /**
     *
     * @param message
     * @param id
     * @param sunset
     * @param sunrise
     * @param type
     * @param country
     */
    public Sys(int type, int id, float message, String country, int sunrise, int sunset) {
        this.type = type;
        this.id = id;
        this.message = message;
        this.country = country;
        this.sunrise = sunrise;
        this.sunset = sunset;
    }

    /**
     *
     * @return
     * The type
     */
    public int getType() {
        return type;
    }

    /**
     *
     * @param type
     * The type
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     *
     * @return
     * The id
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The message
     */
    public float getMessage() {
        return message;
    }

    /**
     *
     * @param message
     * The message
     */
    public void setMessage(float message) {
        this.message = message;
    }

    /**
     *
     * @return
     * The country
     */
    public String getCountry() {
        return country;
    }

    /**
     *
     * @param country
     * The country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     *
     * @return
     * The sunrise
     */
    public int getSunrise() {
        return sunrise;
    }

    /**
     *
     * @param sunrise
     * The sunrise
     */
    public void setSunrise(int sunrise) {
        this.sunrise = sunrise;
    }

    /**
     *
     * @return
     * The sunset
     */
    public int getSunset() {
        return sunset;
    }

    /**
     *
     * @param sunset
     * The sunset
     */
    public void setSunset(int sunset) {
        this.sunset = sunset;
    }


}
