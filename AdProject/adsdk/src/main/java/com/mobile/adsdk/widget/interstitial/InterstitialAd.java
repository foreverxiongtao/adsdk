package com.mobile.adsdk.widget.interstitial;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.mobile.adsdk.R;
import com.mobile.adsdk.entity.AdSlotType;
import com.mobile.adsdk.inf.InterstitialAdListener;
import com.mobile.adsdk.inf.SplashAdListener;
import com.mobile.adsdk.utils.SDKUtils;
import com.mobile.adsdk.widget.banner.AdConfig;

public class InterstitialAd {

    private InterstitialAdView mInterstitialAdView;

    private InterstitialAd() {

    }

    public InterstitialAd(Activity activity, String adId) {
        dynamicAddView(activity, adId, null);
    }


    private void dynamicAddView(Activity activity, String adId, InterstitialAdListener listener) {
        String appId = SDKUtils.getAppId(activity);
        AdConfig mConfig = new AdConfig.Builder().ad_Type(AdSlotType.AD_INTERSTITIAL.getValue()).appId(appId).adId(adId).build();
        mInterstitialAdView = new InterstitialAdView(activity, mConfig, listener);
        mInterstitialAdView.setClickable(true);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        decorView.addView(mInterstitialAdView, params);
        mInterstitialAdView.bringToFront();
    }


    public void destroy() {
        if (mInterstitialAdView != null) {
            mInterstitialAdView.destroy();
        }
    }

    public void hideAd() {
        mInterstitialAdView.hideAd();
    }

    public void showAd() {
        mInterstitialAdView.showAd();
    }

    public boolean isAdClosed() {
        return mInterstitialAdView.isAdClosed();
    }
}
