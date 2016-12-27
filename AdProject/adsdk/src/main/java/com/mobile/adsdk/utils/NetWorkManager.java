package com.mobile.adsdk.utils;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.mobile.adsdk.global.API;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.protobuf.ProtoConverterFactory;

/**
 * Created by azkf-XT on 2016/12/23.
 */

public class NetWorkManager {
    private static volatile NetWorkManager mInstance;
    private static volatile API mApi;
    private static volatile API mDownloadApi;

    public static NetWorkManager getManager() {
        if (mInstance == null) {
            synchronized (NetWorkManager.class) {
                if (mInstance == null) {
                    mInstance = new NetWorkManager();
                }
            }
        }
        return mInstance;
    }

    public API getAPI() {
        if (mApi == null) {
            OkHttpClient client = null;
            client = new OkHttpClient.Builder().addNetworkInterceptor(new StethoInterceptor()).connectTimeout(30, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS).build();
            Retrofit retrofit = new Retrofit.Builder().baseUrl(API.BASE_URL).client(client).addConverterFactory(ProtoConverterFactory.create()).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
            mApi = retrofit.create(API.class);
        }
        return mApi;
    }

    public API getDownloadAPI(OkHttpClient client) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(API.BASE_URL).client(client).addConverterFactory(ProtoConverterFactory.create()).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
        mDownloadApi = retrofit.create(API.class);
        return mDownloadApi;
    }
}
