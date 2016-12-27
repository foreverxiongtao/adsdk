package com.mobile.adsdk.widget.splash;

import android.app.Activity;
import android.view.ViewGroup;

import com.mobile.adsdk.entity.AdSlotType;
import com.mobile.adsdk.inf.SplashAdListener;
import com.mobile.adsdk.utils.SDKUtils;
import com.mobile.adsdk.widget.banner.AdConfig;


public class SplashAd {

    private SplashAdView mSplashAdView;

    public SplashAd(Activity activity, ViewGroup vp, SplashAdListener listener, String adId, boolean isClicked) {
        dynamicAddView(activity, vp, adId, listener, isClicked);
    }


    private void dynamicAddView(Activity activity, ViewGroup vp, String adId, SplashAdListener listener, boolean mIsClicked) {
        String appId = SDKUtils.getAppId(activity);
        AdConfig mConfig = new AdConfig.Builder().ad_Type(AdSlotType.AD_SPLASH.getValue()).appId(appId).adId(adId).build();
        mSplashAdView = new SplashAdView(activity, mConfig, listener, mIsClicked);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        vp.addView(mSplashAdView, params);
    }

    public void destroy() {
        mSplashAdView.destroy();
    }
}
