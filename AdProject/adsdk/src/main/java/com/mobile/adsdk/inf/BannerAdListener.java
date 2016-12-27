package com.mobile.adsdk.inf;

/**
 * Created by azkf-XT on 2016/12/21.
 */

public interface BannerAdListener {
    void onAdPresent();

    void onAdDismissed();

    void onAdFailed(String var1);

    void onAdClick();
}
