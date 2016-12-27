package com.mobile.adsdk.inf;

import com.mobile.adsdk.entity.NativeErrorCode;

import java.util.List;

/**
 * Created by azkf-XT on 2016/12/22.
 */

public interface AdNativeNetworkListener {
    void onNativeLoad(List<NativeResponse> var1);

    void onNativeFail(NativeErrorCode var1);
}
