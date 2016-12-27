package com.mobile.adsdk.openapi;

import android.content.Context;

import com.mobile.adsdk.inf.ISdkAPI;

public class AdSdkFactory {
    private static AdSdkFactory mInstance = null;

    private AdSdkFactory() {
    }

    /**
     * 创建入口实例
     *
     * @param context
     * @return
     */
    public static ISdkAPI createAdSdkAPI(Context context) {
        if (mInstance == null) {
            mInstance = new AdSdkFactory();
        }
        return new SdkApiImpl(context);
    }

    /**
     * 创建入口实例
     *
     * @param context
     * @param supportDebug
     * @return
     */
    public static ISdkAPI createAdSdkAPI(Context context, boolean supportDebug) {
        if (mInstance == null) {
            mInstance = new AdSdkFactory();
        }
        return new SdkApiImpl(context, supportDebug);
    }
}
