package com.example.azkf_xt.adproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mobile.adsdk.widget.banner.BannerAd;
import com.mobile.adsdk.widget.interstitial.InterstitialAd;

/*
 *
 *
 * 版 权 :@Copyright desperado版权所有
 *
 * 作 者 :desperado
 *
 * 版 本 :1.0
 *
 * 创建日期 :2016/12/21       19:40
 *
 * 描 述 :插屏广告
 *
 * 修订日期 :
 */
public class InterstitialAdtivity extends AppCompatActivity {

    String api_url, android_appid, android_slotid, ios_appid, ios_slotid, s;
    final static String _NAME = "KsMSSPTester";
    final static String _VERSION = "V0.9.4";
    private LinearLayout ll_main_container;
    private BannerAd bannerAd;
    private InterstitialAd ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interstitial);
        initView();
        requestData();
    }

    public void advclick(View view) {
        Toast.makeText(this, "点击了我", Toast.LENGTH_SHORT).show();
    }

    private void initView() {
    }


    public void showAd(View view){
        ad.showAd();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && !ad.isAdClosed()) {
            ad.hideAd();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    private void requestData() {
        ad = new InterstitialAd(this, "oc1lyk7w");
    }

    @Override
    protected void onDestroy() {
        if (ad != null) {
            ad.destroy();
        }
        super.onDestroy();
    }
}
