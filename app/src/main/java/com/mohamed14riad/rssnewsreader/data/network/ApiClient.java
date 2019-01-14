package com.mohamed14riad.rssnewsreader.data.network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public final class ApiClient {
    private static final int CONNECT_TIMEOUT = 30;

    private static OkHttpClient okHttpClient = null;
    private static Retrofit retrofit = null;

    public static ApiService getApiService(String BASE_URL) {
        return getClient(BASE_URL).create(ApiService.class);
    }

    private static Retrofit getClient(String BASE_URL) {
        if (retrofit == null) {
            initRetrofit(BASE_URL);
        }

        return retrofit;
    }

    private static void initRetrofit(String BASE_URL) {
        if (okHttpClient == null) {
            initOkHttp();
        }

        //noinspection deprecation
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    private static void initOkHttp() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .build();
    }
}
