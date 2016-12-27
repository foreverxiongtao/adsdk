package com.mobile.adsdk.widget.banner;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.mobile.adsdk.entity.AdSlotType;
import com.mobile.adsdk.inf.BannerAdListener;
import com.mobile.adsdk.utils.SDKUtils;

/*
 *
 *
 * 版 权 :@Copyright desperado版权所有
 *
 * 作 者 :desperado
 *
 * 版 本 :1.0
 *
 * 创建日期 :2016/12/20       15:12
 *
 * 描 述 : banner广告条
 *
 * 修订日期 :
 */

public class BannerAd {
    private CarouseView mCarouseView;

    public BannerAd(Activity context, String adId, int width, int height, BannerAdListener listener) {
        configAdLoad(context, adId, width, height, listener);
    }

    private void configAdLoad(Activity context, String adId, int width, int height, BannerAdListener listener) {
        String mAppId = SDKUtils.getAppId(context);
        AdConfig config = new AdConfig.Builder().ad_Type(AdSlotType.AD_BANNER.getValue()).appId(mAppId).adId(adId).width(width).height(height).build();
        if (mCarouseView != null) {
            mCarouseView.load(config);
        }
        mCarouseView = new CarouseView(context, listener, config);
        ViewGroup.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
        mCarouseView.setLayoutParams(params);
    }


    public void destroy() {
        if (mCarouseView != null) {
            mCarouseView.destroy();
        }
    }

    public void showAd() {
        if (mCarouseView != null) {
            mCarouseView.showAd();
        }
    }


//    public void setAdSize(int width, int height) {
////        this.mWidht = width;
////        this.mHeight = height;
////        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) getLayoutParams();
////        if (layoutParams == null) {
////            layoutParams = new RelativeLayout.LayoutParams(mWidht, mHeight);
////        }
////        carouseView.setLayoutParams(layoutParams);
//    }

//
//    private void initView() {
////        carouseView = new CarouseView(mActivity, null);
////        carouseView.setId(R.id.carouse);
////        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(mWidht, mHeight);
////        carouseView.setLayoutParams(params);
////        addView(carouseView, params);
//    }


    public View getBannerView() {
        return mCarouseView;
    }
}
