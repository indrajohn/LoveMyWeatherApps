package com.lovemyweather.lovemyweather.Services;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lovemyweather.lovemyweather.BuildConfig;
import com.lovemyweather.lovemyweather.Entity.WeatherList;

import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by John on 25/09/2016.
 */
public interface ServicesAPI {
    String BASE_URL = "http://api.openweathermap.org/data/2.5/";

    //get the weather with retrofit
    @Headers("x-api-key: 201d8014006f40c026e3e2f463d2fe00")//API from openweathermap for get the weather
    @GET("weather/")
    Call<WeatherList> getWeatherListByInput(@Query("q") String q, @Query("units") String unit);
    //output is http://api.openweathermap.org/data/2.5/weather/?q=[input from user]&unit=[input from user]
    //where q is town and unit is for output in celcius.

    //get the weather with retrofit
    @Headers("x-api-key: 201d8014006f40c026e3e2f463d2fe00")//API from openweathermap for get the weather
    @GET("weather/")
    Call<WeatherList> getWeatherListByGeo(@Query("units") String unit ,
                                            @Query("lat") double latitude,@Query("lon") double longitude);
    //output is http://api.openweathermap.org/data/2.5/weather/
    //             unit=[input from user]&lat=[input from user]&lon=[input from user]
    //where q is town ,unit is for output in celcius,lat is for latitude,and lon is for longitude from user


    class Factory{
        private static ServicesAPI service;
        public static ServicesAPI getInstance(Context context)
        {
            if(service ==null)
            {
                OkHttpClient.Builder builder = new OkHttpClient.Builder();
                builder.readTimeout(15, TimeUnit.SECONDS);
                builder.connectTimeout(10,TimeUnit.SECONDS);
                builder.writeTimeout(10,TimeUnit.SECONDS);

                if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                    builder.addInterceptor(interceptor);
                }
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();
                //for cache
                int cacheSize = 10 * 1024 * 1024 ;
                Cache cache = new Cache(context.getCacheDir(),cacheSize);
                builder.cache(cache);

                Retrofit retrofit = new Retrofit.Builder().client(builder.build())
                        .addConverterFactory(GsonConverterFactory.create(gson)).baseUrl(BASE_URL).build();
                service = retrofit.create(ServicesAPI.class);
                return service;
            }
            else {
                return service;
            }
        }
    }
}