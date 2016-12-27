package com.mobile.adsdk.update;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.NotificationCompat.Builder;
import android.text.TextUtils;


import com.mobile.adsdk.R;
import com.mobile.adsdk.global.API;
import com.mobile.adsdk.global.LocaalConstant;
import com.mobile.adsdk.inf.IEventTrackingListener;
import com.mobile.adsdk.track.TrackingEventImpl;
import com.mobile.adsdk.utils.AbFileUtils;
import com.mobile.adsdk.utils.AbLogUtil;
import com.mobile.adsdk.utils.AdSdkDownlaoder;
import com.mobile.adsdk.utils.NetWorkManager;
import com.mobile.adsdk.utils.SPUtils;
import com.mobile.adsdk.utils.StorageUtils;
import com.mobile.adsdk.utils.ThreadManager;
import com.mobile.adsdk.utils.interceptor.DownloadProgressInterceptor;

import java.io.File;
import java.io.IOException;
import java.util.List;

import ks.AdProto;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class AdSdkService extends IntentService {
//    private static final int BUFFER_SIZE = 10 * 1024; // 8k ~ 32K
//    private static volatile int notifyId = 0;
//    private static final String TAG = "AdSdkService";
//    private NotificationManager mNotifyManager;
//    private Builder mBuilder;
//    private AdProto.Ad ad;
//    private IEventTrackingListener mListener;
//    private volatile int oldProgress = 0;


    public AdSdkService() {
        super("AdSdkService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        final String apkUrl = intent.getStringExtra(LocaalConstant.APK_DOWNLOAD_URL);
        final AdProto.Ad ad = (AdProto.Ad) intent.getSerializableExtra(LocaalConstant.APK_DOWNLOAD_AD);
        final String clickId = intent.getStringExtra(LocaalConstant.APK_CLICK_ID);
        int notifyId = SPUtils.loadPrefInt(this, LocaalConstant.NOTIFATION_CURRENT_ID, 0);
        String apkName = "";
        if (ad.getMetaGroup(0) != null) {
            AdProto.MaterialMeta metaGroup = ad.getMetaGroup(0);
            if (TextUtils.isEmpty(metaGroup.getBrandName())) {
                apkName = getString(getApplicationInfo().labelRes);
            } else {
                apkName = metaGroup.getBrandName();
            }
        }
        if (!TextUtils.isEmpty(clickId)) {
            new AdSdkDownlaoder.Builder().ad(ad).apkurl(apkUrl).clidkid(clickId).apkName(apkName).context(AdSdkService.this).notifyId(notifyId).build().downloadApk();
        } else {
            new AdSdkDownlaoder.Builder().ad(ad).apkurl(apkUrl).apkName(apkName).context(AdSdkService.this).notifyId(notifyId).build().downloadApk();
        }
        notifyId++;
        SPUtils.savePrefInt(this, LocaalConstant.NOTIFATION_CURRENT_ID, notifyId);
    }
//}

//
//    @Override
//    protected void onHandleIntent(Intent intent) {
//        notifyId++;
//        mNotifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        mBuilder = new Builder(this);
//        String appName = getString(getApplicationInfo().labelRes);
//        int icon = getApplicationInfo().icon;
//        mBuilder.setContentTitle(appName).setSmallIcon(icon);
//        String urlStr = intent.getStringExtra(LocaalConstant.APK_DOWNLOAD_URL);
//        ad = (AdProto.Ad) intent.getSerializableExtra(LocaalConstant.APK_DOWNLOAD_AD);
//        if (ad != null && ad.getAdTrackingList() != null) {
//            if (mListener == null) {
//                mListener = new TrackingEventImpl.Builder().trackinglList(ad.getAdTrackingList()).build();
//            } else {
//                mListener.resetTrackListEvent(ad.getAdTrackingList());
//            }
//        }
//        downloadApk(urlStr);
//    }
//
//    private void updateProgress(int progress) {
//        //"正在下载:" + progress + "%"
//        mBuilder.setContentText(this.getString(R.string.download_progress, progress)).setProgress(100, progress, false);
//        //setContentInent如果不设置在4.0+上没有问题，在4.0以下会报异常
//        PendingIntent pendingintent = PendingIntent.getActivity(this, 0, new Intent(), PendingIntent.FLAG_CANCEL_CURRENT);
//        mBuilder.setContentIntent(pendingintent);
//        mNotifyManager.notify(notifyId, mBuilder.build());
//    }
//
//    private void downloadApk(final String url) {
//        if (TextUtils.isEmpty(url)) {
//            return;
//        }
//        //启动下载APK线程
//        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new DownloadProgressInterceptor(new DownloadProgressInterceptor.DownloadProgressListener() {
//            @Override
//            public void update(long bytesRead, long contentLength, boolean done) {
//                if (done) {
//                    return;
//                }
//                // 如果进度与之前进度相等，则不更新，如果更新太频繁，否则会造成界面卡顿
//                final int percent = (int) ((bytesRead * 100L) / contentLength);
//                if (oldProgress != percent) {
//                    updateProgress(percent);
//                    oldProgress = percent;
//                }
//            }
//        })).build();
////        Retrofit retrofit = new Retrofit.Builder().baseUrl(API.BASE_URL).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).client(client).build();
////        API xuXianService = retrofit.create(API.class);
//        API api = NetWorkManager.getManager().getDownloadAPI(client);
//        Observable<ResponseBody> file = api.download(url);
////        mListener.trackDownloadStartEvent();
//        file.subscribeOn(Schedulers.io()).map(new Func1<ResponseBody, File>() {
//            @Override
//            public File call(ResponseBody response) {
//                File apkFile = null;
//                try {
//                    File dir = StorageUtils.getCacheDirectory(AdSdkService.this);
////                    String apkName = LocalConstant.APK_NAME + version + ".apk";
//                    String apkName = url.substring(url.lastIndexOf("/") + 1, url.length());
//                    apkFile = new File(dir, apkName);
//                    AbFileUtils.saveBytesToFile(response.bytes(), apkFile.getAbsolutePath());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                return apkFile;
//            }
//        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<File>() {
//            @Override
//            public void onCompleted() {
//                AbLogUtil.e("downloadService", "onCompleted");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                AbLogUtil.e(AdSdkService.this, "下载失败！");
//                mBuilder.setContentText("下载失败").setProgress(0, 0, false);
//                Notification noti = mBuilder.build();
//                noti.flags = Notification.FLAG_AUTO_CANCEL;
//                mNotifyManager.notify(0, noti);
//            }
//
//            @Override
//            public void onNext(File file) {
//                mListener.trackDownloadCompletedEvent();
//                AbLogUtil.i("percent", "下载完成");
//                if (file != null) {
//                    installApk(file);
//                    // 下载完成
//                    mBuilder.setContentText(getString(R.string.download_success)).setProgress(0, 0, false);
//
//                    Intent installAPKIntent = new Intent(Intent.ACTION_VIEW);
//                    //如果没有设置SDCard写权限，或者没有sdcard,apk文件保存在内存中，需要授予权限才能安装
//                    String[] command = {"chmod", "777", file.toString()};
//                    ProcessBuilder builder = new ProcessBuilder(command);
//                    try {
//                        builder.start();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    installAPKIntent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
//                    PendingIntent pendingIntent = PendingIntent.getActivity(AdSdkService.this, 0, installAPKIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//                    mBuilder.setContentIntent(pendingIntent);
//                    Notification noti = mBuilder.build();
//                    noti.flags = Notification.FLAG_AUTO_CANCEL;
//                    mNotifyManager.notify(0, noti);
//                } else {
//                    AbLogUtil.e(AdSdkService.this, "文件更新异常！");
//                }
//            }
//        });
//    }
//
//    /**
//     * 安装APK
//     */
//    private void installApk(File file) {
//        if (!file.exists()) {
//            return;
//        }
//        Intent i = new Intent();
//        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        i.setAction(Intent.ACTION_VIEW);
//        i.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
//        startActivity(i);
//    }

}
