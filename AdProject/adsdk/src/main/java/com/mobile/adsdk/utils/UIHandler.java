package com.mobile.adsdk.utils;

import android.os.Handler;
import android.os.Looper;


/**
 * 用于处理主线程请求的Handler，工作在主线程中
 *
 * @author dpk
 */
public class UIHandler extends Handler {

    public UIHandler(Looper looper) {
        super(looper);
    }

}
