package com.lovemyweather.lovemyweather.Entity;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Created by John on 25/09/2016.
 */
@Generated("org.jsonschema2pojo")
public class Clouds {
    @SerializedName("all")
    @Expose
    private int all;

    /**
     * No args constructor for use in serialization
     *
     */
    public Clouds() {
    }

    /**
     *
     * @param all
     */
    public Clouds(int all) {
        this.all = all;
    }

    /**
     *
     * @return
     * The all
     */
    public int getAll() {
        return all;
    }

    /**
     *
     * @param all
     * The all
     */
    public void setAll(int all) {
        this.all = all;
    }

}

