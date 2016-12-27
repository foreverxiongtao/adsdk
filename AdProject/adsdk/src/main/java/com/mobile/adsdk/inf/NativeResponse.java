package com.mobile.adsdk.inf;

import android.content.Context;
import android.view.View;
import java.util.List;
/**
 * Created by azkf-XT on 2016/12/22.
 */

public interface NativeResponse {
    void recordImpression(View var1);

    void handleClick(View var1);

    void handleClick(View var1, int var2);

    String getTitle();

    String getDesc();

    String getIconUrl();

    String getImageUrl();

    int getMainPicWidth();

    int getMainPicHeight();

    String getBrandName();



    boolean isDownloadApp();

    boolean isAdAvailable(Context var1);

    long getAppSize();

    String getAppPackage();

    List<String> getMultiPicUrls();
    void onStart(Context var1);

    void onError(Context var1, int var2, int var3);

    void onComplete(Context var1);

    void onClose(Context var1, int var2);

    void onClickAd(Context var1);

    void onFullScreen(Context var1, int var2);

    int getDuration();

    NativeResponse.MaterialType getMaterialType();

    String getHtmlSnippet();


    public static enum MaterialType {
        NORMAL,
        VIDEO,
        HTML;

        private MaterialType() {
        }
    }
}
