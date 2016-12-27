package com.mobile.adsdk.widget.nav;

import android.app.Activity;

import com.mobile.adsdk.inf.AdDataHandleListener;
import com.mobile.adsdk.inf.AdNativeNetworkListener;

/**
 * Created by azkf-XT on 2016/12/22.
 */

public class AdNative {

    private AdNativeDataWrapper mWrapper;

    private AdNative() {

    }

    public AdNative(Activity activity, String adId, AdNativeNetworkListener listener) {
        this(activity, adId, listener, new AdNativeDataWrapper(activity, adId, listener));
    }

    private AdNative(Activity activity, String adId, AdNativeNetworkListener listener, AdNativeDataWrapper wrapper) {
        this.mWrapper = wrapper;
    }


    public void loadNativeAd() {
        if (mWrapper != null) {
            mWrapper.loadNativeAd();
        }
    }
}
