package com.hao.greatinrxjava.retrofit;

import com.hao.greatinrxjava.flow.api.HeWeatherApi;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.internal.tls.OkHostnameVerifier;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by liuxuehao on 16/12/29.
 */

public class RetrofitFactory {
    private static Retrofit HEWHEATHER_INSTANCE;

    private static final TrustManagerDelegate trustManager = new TrustManagerDelegate().trustWhatSystemTrust();

    /**
     *
     * @return
     */
    public static Retrofit getHeWeatherRetrofit() {
        if (HEWHEATHER_INSTANCE == null) {
            HEWHEATHER_INSTANCE = new Retrofit.Builder()
                    .client(new OkHttpClient.Builder()
                            .connectTimeout(10_000, TimeUnit.MILLISECONDS)
                            .readTimeout(10_000, TimeUnit.MILLISECONDS)
                            .writeTimeout(10_000, TimeUnit.MILLISECONDS)
                            .sslSocketFactory(trustManager.createSSLSocketFactory(), trustManager)
                            .hostnameVerifier(OkHostnameVerifier.INSTANCE)
                            .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                            .build())
                    .baseUrl(HeWeatherApi.HEWEATHER_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return HEWHEATHER_INSTANCE;
    }
}
