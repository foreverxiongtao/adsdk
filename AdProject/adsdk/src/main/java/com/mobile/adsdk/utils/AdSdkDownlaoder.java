package com.mobile.adsdk.utils;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.widget.Toast;

import com.mobile.adsdk.R;
import com.mobile.adsdk.global.API;
import com.mobile.adsdk.inf.IEventTrackingListener;
import com.mobile.adsdk.track.TrackingEventImpl;
import com.mobile.adsdk.update.AdSdkService;
import com.mobile.adsdk.utils.interceptor.DownloadProgressInterceptor;

import java.io.File;
import java.io.IOException;
import java.security.KeyStore;
import java.security.PublicKey;

import ks.AdProto;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by azkf-XT on 2016/12/23.
 */

public class AdSdkDownlaoder {

    private Context mContext;
    private int mNotifyId = 0;
    private static final String TAG = "AdSdkDownlaoder";
    private NotificationManager mNotifyManager;
    private NotificationCompat.Builder mBuilder;
    private AdProto.Ad mAd;
    private IEventTrackingListener mListener;
    private String mApkUrl;
    private int oldProgress = 0;
    private String mApkName;
    private String mClickId;

    private AdSdkDownlaoder(Builder builder) {
        this.mAd = builder.mAd;
        this.mNotifyId = builder.mNotifyId;
        this.mContext = builder.mContext;
        this.mApkUrl = builder.mApkUrl;
        this.mClickId = builder.mClickId;
        mNotifyManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(mContext);

        int icon = mContext.getApplicationInfo().icon;
        mBuilder.setContentTitle(mApkName).setSmallIcon(icon);
        mListener = new TrackingEventImpl.Builder().clickid(mClickId).trackinglList(mAd.getAdTrackingList()).build();

    }

    public void downloadApk() {
        downloadApk(mApkUrl);
    }


    public static class Builder {
        private AdProto.Ad mAd;
        private Context mContext;
        private String mApkUrl;
        private int mNotifyId;
        private String mApkName;
        private String mClickId;


        public Builder() {

        }


        public Builder apkurl(String apkUrl) {
            this.mApkUrl = apkUrl;
            return this;
        }

        public Builder clidkid(String clickId) {
            this.mClickId = clickId;
            return this;
        }


        public Builder apkName(String apkName) {
            this.mApkName = apkName;
            return this;
        }

        public Builder ad(AdProto.Ad ad) {
            this.mAd = ad;
            return this;
        }

        public Builder context(Context context) {
            this.mContext = context;
            return this;
        }


        public Builder notifyId(int notifyId) {
            this.mNotifyId = notifyId;
            return this;
        }

        public AdSdkDownlaoder build() {
            return new AdSdkDownlaoder(this);
        }
    }


    private void updateProgress(int progress) {
        //"正在下载:" + progress + "%"
        mBuilder.setContentText(mContext.getString(R.string.download_progress, progress)).setProgress(100, progress, false);
        //setContentInent如果不设置在4.0+上没有问题，在4.0以下会报异常
        PendingIntent pendingintent = PendingIntent.getActivity(mContext, mNotifyId, new Intent(), PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pendingintent);
        mNotifyManager.notify(mNotifyId, mBuilder.build());
    }


    private void downloadApk(String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        //启动下载APK线程
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new DownloadProgressInterceptor(new DownloadProgressInterceptor.DownloadProgressListener() {
            @Override
            public void update(long bytesRead, long contentLength, boolean done) {
                if (done) {
                    return;
                }
                // 如果进度与之前进度相等，则不更新，如果更新太频繁，否则会造成界面卡顿
                final int percent = (int) ((bytesRead * 100L) / contentLength);
                if (oldProgress != percent) {
                    updateProgress(percent);
                    oldProgress = percent;
                }
            }
        })).build();
        API api = NetWorkManager.getManager().getDownloadAPI(client);
        Observable<ResponseBody> file = api.download(url);
        mListener.trackDownloadStartEvent();
        file.subscribeOn(Schedulers.io()).map(new Func1<ResponseBody, File>() {
            @Override
            public File call(ResponseBody response) {
                File apkFile = null;
                try {
                    File dir = StorageUtils.getCacheDirectory(mContext);
//                    String apkName = LocalConstant.APK_NAME + version + ".apk";
                    String apkName = mApkUrl.substring(mApkUrl.lastIndexOf("/") + 1, mApkUrl.length());
                    apkFile = new File(dir, apkName);
                    AbFileUtils.saveBytesToFile(response.bytes(), apkFile.getAbsolutePath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return apkFile;
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<File>() {
            @Override
            public void onCompleted() {
                AbLogUtil.e("downloadService", "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                AbLogUtil.e(mContext, "下载失败！");
                mBuilder.setContentText("下载失败").setProgress(0, 0, false);
                Notification noti = mBuilder.build();
                noti.flags = Notification.FLAG_AUTO_CANCEL;
                mNotifyManager.notify(0, noti);
            }

            @Override
            public void onNext(File file) {
                mListener.trackDownloadCompletedEvent();
                AbLogUtil.i("percent", "下载完成");
                if (file != null) {
                    SDKUtils.installApk(mContext, file);
                    // 下载完成
                    mBuilder.setContentText(mContext.getString(R.string.download_success)).setProgress(0, 0, false);

                    Intent installAPKIntent = new Intent(Intent.ACTION_VIEW);
                    //如果没有设置SDCard写权限，或者没有sdcard,apk文件保存在内存中，需要授予权限才能安装
                    String[] command = {"chmod", "777", file.toString()};
                    ProcessBuilder builder = new ProcessBuilder(command);
                    try {
                        builder.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    installAPKIntent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                    PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, installAPKIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    mBuilder.setContentIntent(pendingIntent);
                    Notification noti = mBuilder.build();
                    noti.flags = Notification.FLAG_AUTO_CANCEL;
                    mNotifyManager.notify(0, noti);
                } else {
                    AbLogUtil.e(mContext, "文件更新异常！");
                }
            }
        });
    }
}
