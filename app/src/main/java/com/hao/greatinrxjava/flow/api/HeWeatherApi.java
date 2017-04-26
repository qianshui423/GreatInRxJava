package com.hao.greatinrxjava.flow.api;

import com.hao.greatinrxjava.flow.model.bean.HeWeather5;

import io.reactivex.Flowable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by liuxuehao on 16/12/26.
 */

public interface HeWeatherApi {
    String HEWEATHER_BASE_URL = "https://free-api.heweather.com/v5/";

    /**
     * 实况天气
     */
    @GET(SK.NOW)
    Flowable<Response<HeWeather5>> now(@Query("city") String city, @Query("key") String key, @Query("lang") String lang);
}
