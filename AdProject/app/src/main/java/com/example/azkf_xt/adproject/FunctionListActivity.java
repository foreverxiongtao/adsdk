package com.example.azkf_xt.adproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by azkf-XT on 2016/12/21.
 */

public class FunctionListActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function_list);
    }


    public void transformBanerAd(View view) {
        Intent intent = new Intent(this, BannerAdActivity.class);
        startActivity(intent);
    }

    public void transformInterstitialAd(View view) {
        Intent intent = new Intent(this, InterstitialAdtivity.class);
        startActivity(intent);
    }

    public void transformNativeListView(View view) {
        Intent intent = new Intent(this, NativeListActivity.class);
        startActivity(intent);
    }

}
