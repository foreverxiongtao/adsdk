package com.mobile.adsdk.openapi;


import android.content.Context;

import com.mobile.adsdk.utils.SDKUtils;

public class AdSdk {

    private static AdSdk adSdk = null;

    private AdSdk() {
    }

    //    public static AdSdk getInstance(){
//        if(adSdk == null){
//            adSdk =  new AdSdk();
//        }
//        return adSdk;
//    }

    public static synchronized void init(Context context) {
        if (context == null) {
            throw new RuntimeException("context can't be null");
        }
        SDKUtils.getSDKUtils(context).init(context);
    }

//
//    public void setmAdSdkConfig(GlobalSdkConfig adSdkConfig) {
//        mAdSdkConfig = adSdkConfig;
//    }
//
//    public static GlobalSdkConfig getmAdSdkConfig() {
//        return mAdSdkConfig;
//    }
}
