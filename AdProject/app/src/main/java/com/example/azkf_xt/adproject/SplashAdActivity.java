package com.example.azkf_xt.adproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;
import com.mobile.adsdk.inf.SplashAdListener;
import com.mobile.adsdk.openapi.AdSdk;
import com.mobile.adsdk.widget.splash.SplashAd;

/*
 *
 *
 * 版 权 :@Copyright desperado版权所有
 *
 * 作 者 :desperado
 *
 * 版 本 :1.0
 *
 * 创建日期 :2016/12/21       19:41
 *
 * 描 述 :实时开屏，广告实时请求并且立即展现
 *
 * 修订日期 :
 */
public class SplashAdActivity extends Activity {

    private RelativeLayout rl_splash_adsParent;
    private SplashAd splashAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        AdSdk.init(this);
        initView();
        initData();
    }


    SplashAdListener listener = new SplashAdListener() {
        @Override
        public void onAdDismissed() {
            Log.i("RSplashActivity", "onAdDismissed");
            jumpWhenCanClick(); // 跳转至您的应用主界面
        }

        @Override
        public void onAdFailed(String arg0) {
            Log.i("RSplashActivity", "onAdFailed");
            jump();
        }

        @Override
        public void onAdPresent() {
            Log.i("RSplashActivity", "onAdPresent");
        }

        @Override
        public void onAdClick() {
            Log.i("RSplashActivity", "onAdClick");
            // 设置开屏可接受点击时，该回调可用
        }
    };


    private void initView() {
        rl_splash_adsParent = (RelativeLayout) findViewById(R.id.rl_splash_adsParent);
    }

    private void initData() {
        String adPlaceId = "sx8d0lkj"; // 重要：请填上您的广告位ID，代码位错误会导致无法请求到广告
        splashAd = new SplashAd(this, rl_splash_adsParent, listener, adPlaceId, true);
    }

    /**
     * 当设置开屏可点击时，需要等待跳转页面关闭后，再切换至您的主窗口。故此时需要增加canJumpImmediately判断。 另外，点击开屏还需要在onResume中调用jumpWhenCanClick接口。
     */
    public boolean canJumpImmediately = false;

    private void jumpWhenCanClick() {
        Log.d("test", "this.hasWindowFocus():" + this.hasWindowFocus());
        if (canJumpImmediately) {
            this.startActivity(new Intent(this, FunctionListActivity.class));
            this.finish();
        } else {
            canJumpImmediately = true;
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (splashAd != null) {
            splashAd.destroy();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        canJumpImmediately = false;
    }

    /**
     * 不可点击的开屏，使用该jump方法，而不是用jumpWhenCanClick
     */
    private void jump() {
        this.startActivity(new Intent(this, FunctionListActivity.class));
        this.finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (canJumpImmediately) {
            jumpWhenCanClick();
        }
        canJumpImmediately = true;
    }
}
