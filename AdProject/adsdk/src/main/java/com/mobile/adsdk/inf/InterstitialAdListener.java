package com.mobile.adsdk.inf;

/**
 * Created by azkf-XT on 2016/12/21.
 */

public interface InterstitialAdListener {
    void onAdReady();

    void onAdPresent();

    void onAdClick();

    void onAdDismissed();

    void onAdFailed(String var1);
}
