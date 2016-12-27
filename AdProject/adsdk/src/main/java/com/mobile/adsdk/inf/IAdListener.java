package com.mobile.adsdk.inf;

/*
 *
 *
 * 版 权 :@Copyright desperado版权所有
 *
 * 作 者 :desperado
 *
 * 版 本 :1.0
 *
 * 创建日期 :2016/12/20       15:56
 *
 * 描 述 :广告接口回调
 *
 * 修订日期 :
 */
public interface IAdListener {
    void onAdvertisementDataDidLoadSuccess();

    void onAdvertisementDataDidLoadFailure();

    void onAdVertisementDataDidLoadStart();
    
    void onAdVertisementDataDidLoadCompleted();

    void onAdvertisementViewDidShow();

    void onAdvertisementViewDidClick();

    void onAdvertisementViewWillStartNewIntent();
}
