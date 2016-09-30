package com.lovemyweather.lovemyweather.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

/**
 * Created by John on 25/09/2016.
 */
@Generated("org.jsonschema2pojo")
public class Wind {

    @SerializedName("speed")
    @Expose
    private float speed;
    @SerializedName("deg")
    @Expose
    private float deg;
    @SerializedName("gust")
    @Expose
    private float gust;

    /**
     * No args constructor for use in serialization
     *
     */
    public Wind() {
    }

    /**
     *
     * @param gust
     * @param speed
     * @param deg
     */
    public Wind(float speed, float deg, float gust) {
        this.speed = speed;
        this.deg = deg;
        this.gust = gust;
    }

    /**
     *
     * @return
     * The speed
     */
    public float getSpeed() {
        return speed;
    }

    /**
     *
     * @param speed
     * The speed
     */
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    /**
     *
     * @return
     * The deg
     */
    public float getDeg() {
        return deg;
    }

    /**
     *
     * @param deg
     * The deg
     */
    public void setDeg(float deg) {
        this.deg = deg;
    }

    /**
     *
     * @return
     * The gust
     */
    public float getGust() {
        return gust;
    }

    /**
     *
     * @param gust
     * The gust
     */
    public void setGust(float gust) {
        this.gust = gust;
    }
}
