package com.lovemyweather.lovemyweather.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

/**
 * Created by John on 25/09/2016.
 */
@Generated("org.jsonschema2pojo")
public class Rain {@SerializedName("3h")
@Expose
private float _3h;

    /**
     * No args constructor for use in serialization
     *
     */
    public Rain() {
    }

    /**
     *
     * @param _3h
     */
    public Rain(float _3h) {
        this._3h = _3h;
    }

    /**
     *
     * @return
     * The _3h
     */
    public float get3h() {
        return _3h;
    }

    /**
     *
     * @param _3h
     * The 3h
     */
    public void set3h(float _3h) {
        this._3h = _3h;
    }
}
