package com.mobile.adsdk;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.mobile.adsdk.global.API;
import com.mobile.adsdk.global.LocaalConstant;
import com.mobile.adsdk.global.NetParams;
import com.mobile.adsdk.openapi.AdSdkFactory;
import com.mobile.adsdk.widget.nav.AdNative;

import ks.AdProto;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.protobuf.ProtoConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by azkf-XT on 2016/12/20.
 * <p>
 * <p>
 * <p>
 * <p>
 * aaaaaa
 */

public class Texter {


    public void text(Context context) {
//        String appid="";
////        try {
////            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(),
////                    PackageManager.GET_META_DATA);
////            appid = appInfo.metaData.getString(LocaalConstant.APP_ID);
////            Log.d("Tag", " app key : " + appid);  // Tag﹕ app key : AIzaSyBhBFOgVQclaa8p1JJeqaZHiCo2nfiyBBo
////        } catch (PackageManager.NameNotFoundException e) {
////            e.printStackTrace();
////        }
////        AdProto.MobadsRequest request = NetParams.getInstance().generateKsAndroidRequest("sx8d0lkj",appid, 640, 960).build();
//        Retrofit retrofit = new Retrofit.Builder().addConverterFactory(ProtoConverterFactory.create()).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).baseUrl("http://120.92.44.245").build();
//        API api = retrofit.create(API.class);
//        Observable<AdProto.MobadsResponse> comments = api.def(request);
//        comments.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<AdProto.MobadsResponse>() {
//            @Override
//            public void onCompleted() {
//                Log.i("onCompleted", "onCompleted");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.i("onError", "onError");
//            }
//
//            @Override
//            public void onNext(AdProto.MobadsResponse responseBody) {
//                Log.i("onNext",responseBody.toString()+"");
//            }
//        });

    }
}
