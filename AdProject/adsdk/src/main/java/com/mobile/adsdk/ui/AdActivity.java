package com.mobile.adsdk.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.mobile.adsdk.R;
import com.mobile.adsdk.Texter;
import com.mobile.adsdk.global.LocaalConstant;
import com.mobile.adsdk.track.TrackingEventImpl;
import com.mobile.adsdk.widget.ProgressWebView;

import ks.AdProto;

/*
 *
 *
 * 版 权 :@Copyright desperado版权所有
 *
 * 作 者 :desperado
 *
 * 版 本 :1.0
 *
 * 创建日期 :2016/12/21       10:22
 *
 * 描 述 :广告activity
 *
 * 修订日期 :
 */
public class AdActivity extends Activity {

    private ProgressWebView pwv_ad_dispay;
    private ImageView item_image;
    private String mUrl;
    private AdProto.Ad mAd;
    private TrackingEventImpl mTrackingEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad);
        initView();
        setEventListener();
        initData();
    }


    private void initView() {
        pwv_ad_dispay = (ProgressWebView) findViewById(R.id.pwv_ad_dispay);
        item_image = (ImageView) findViewById(R.id.item_image);
    }

    private void setEventListener() {
        item_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTrackingEvent.trackCloseEvent();
                finish();
            }
        });
    }

    private void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            mUrl = intent.getStringExtra(LocaalConstant.INTENT_WEB_URL);
            mAd = (AdProto.Ad) intent.getSerializableExtra(LocaalConstant.INTENT_AD_SERIABLE);
            if (!TextUtils.isEmpty(mUrl)) {
                pwv_ad_dispay.loadUrl(mUrl);
            }
            if (mAd != null) {
                mTrackingEvent = new TrackingEventImpl.Builder().clickid("").trackinglList(mAd.getAdTrackingList()).build();
            }

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && pwv_ad_dispay.canGoBack()) {
            pwv_ad_dispay.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (pwv_ad_dispay != null) {
            // / 解决Receiver not registered: //
            // android.widget.ZoomButtonsController
            pwv_ad_dispay.setVisibility(View.GONE);// 把destroy()延后
            pwv_ad_dispay.destroy();
        }
    }
}
