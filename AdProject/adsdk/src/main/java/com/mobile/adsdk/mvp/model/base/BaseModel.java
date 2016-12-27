package com.mobile.adsdk.mvp.model.base;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.mobile.adsdk.BuildConfig;
import com.mobile.adsdk.global.API;
import com.mobile.adsdk.utils.NetWorkManager;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.protobuf.ProtoConverterFactory;

/*
 *
 *
 * 版 权 :@Copyright desperado版权所有
 *
 * 作 者 :desperado
 *
 * 版 本 :1.0
 *
 * 创建日期 :2016/12/20       13:12
 *
 * 描 述 :数据源抽象类
 *
 * 修订日期 :
 */
public class BaseModel {
    private static API mApi;

    protected BaseModel() {
        if (mApi == null) {
//            OkHttpClient client = null;
//            client = new OkHttpClient.Builder().addNetworkInterceptor(new StethoInterceptor()).connectTimeout(30, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS).build();
//            Retrofit retrofit = new Retrofit.Builder().baseUrl(API.BASE_URL).client(client).addConverterFactory(ProtoConverterFactory.create()).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
//            flashDonkeyService = retrofit.create(API.class);
            mApi = NetWorkManager.getManager().getAPI();
        }

    }

    public API getAPIService() {
        return mApi;
    }
}
