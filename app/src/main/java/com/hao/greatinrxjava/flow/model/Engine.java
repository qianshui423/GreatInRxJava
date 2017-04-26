package com.hao.greatinrxjava.flow.model;

import com.hao.greatinrxjava.flow.api.HeWeatherApi;
import com.hao.greatinrxjava.flow.model.bean.HeWeather5;
import com.hao.greatinrxjava.flow.model.bean.Now;
import com.hao.greatinrxjava.retrofit.RetrofitFactory;

import org.reactivestreams.Subscription;

import io.reactivex.FlowableSubscriber;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by liuxuehao on 16/12/26.
 * 面向Model层
 */

public class Engine {
    private static Engine INSTANCE;

    private HeWeatherApi mHeWeatherApi;

    private Engine() {
        mHeWeatherApi = RetrofitFactory.getHeWeatherRetrofit().create(HeWeatherApi.class);
    }

    public static Engine getInstance() {
        if (INSTANCE == null) {
            synchronized (Engine.class) {
                INSTANCE = new Engine();
            }
        }
        return INSTANCE;
    }

    /**
     * @param city 城市名
     * @param key  和风key
     * @param lang 语言
     * @param flow 回调接口
     */
    public void engineNow(String city, String key, String lang, final Flow<Now> flow) {
        /*TaskCached.getInstance().addEngine(SK.NOW, mHeWeatherApi.now(city, key, lang))
                .addTask(new DisposableSubscriber<Response<HeWeather5>>() {
                    @Override
                    public void onNext(Response<HeWeather5> heWeather5Response) {
                        Log.d(TAG, "onNext: ");
                        flow.flowData(heWeather5Response.body().getHeWeather5().get(0).getNow());
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.d(TAG, "onError: ");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                })
                .executeTask();*/
        mHeWeatherApi.now(city, key, lang)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FlowableSubscriber<Response<HeWeather5>>() {
                    @Override
                    public void onSubscribe(@NonNull Subscription s) {
                        s.request(1);
                    }

                    @Override
                    public void onNext(Response<HeWeather5> heWeather5Response) {
                        flow.flowData(heWeather5Response.body().getHeWeather5().get(0).getNow());
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
