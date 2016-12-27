package com.mobile.adsdk.openapi;

import android.content.Context;
import android.text.TextUtils;

import com.mobile.adsdk.R;
import com.mobile.adsdk.inf.ISdkAPI;

/*
 *
 *
 * 版 权 :@Copyright desperado版权所有
 *
 * 作 者 :desperado
 *
 * 版 本 :1.0
 *
 * 创建日期 :2016/12/19       13:39
 *
 * 描 述 :sdk入口实现
 *
 * 修订日期 :
 */
 final class SdkApiImpl implements ISdkAPI {

    private static GlobalSdkConfig mConfig;

    public static GlobalSdkConfig getConfig() {
        if (mConfig == null) {
            throw new RuntimeException(mConfig.getmContext().getResources().getString(R.string.sdk_uninit));
        }
        return mConfig;
    }

    public static void setConfig(GlobalSdkConfig mConfig) {
        if (mConfig != null) {
            SdkApiImpl.mConfig = mConfig;
        }
    }

    public SdkApiImpl(Context context, boolean supportedDebug) {
        internalItialize(context, supportedDebug);
    }

    public SdkApiImpl(Context context) {
        this(context, false);
    }

    @Override
    public void registerApp(String apikey) {
        if (TextUtils.isEmpty(apikey)) {
            throw new RuntimeException(mConfig.getmContext().getResources().getString(R.string.apikey_empty));
        }
        if (mConfig != null) {
            mConfig.setmApiKey(apikey);
        } else {
            throw new RuntimeException(mConfig.getmContext().getResources().getString(R.string.sdk_init_failed));
        }
    }

    /**
     * sdk初始化入口
     */
    private void internalItialize(Context context, boolean mSupportedDebug) {
        if (context == null) {
            throw new RuntimeException("context不能为空");
        }
        mConfig = new GlobalSdkConfig.Builder().context(context).supprotDebug(mSupportedDebug).build();
    }

    @Override
    public void unregisterApp() {
        mConfig = null;
    }

    @Override
    public int getAdSdkVersion() {
        return 1;
    }
}
