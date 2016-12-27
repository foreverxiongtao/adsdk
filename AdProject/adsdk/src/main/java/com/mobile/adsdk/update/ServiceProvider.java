package com.mobile.adsdk.update;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.NonNull;


import com.mobile.adsdk.R;
import com.mobile.adsdk.global.LocaalConstant;
import com.mobile.adsdk.utils.SDKUtils;
import com.mobile.adsdk.utils.StorageUtils;
import com.mobile.adsdk.utils.ToastUtils;

import java.io.File;

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
 * 创建日期 :2016/12/21       11:27
 *
 * 描 述 :下载工具类
 *
 * 修订日期 :
 */
public class ServiceProvider {
    private static final String APP_UPDATE_SERVER_URL = "http://softfile.3g.qq.com:8080/msoft/179/24659/43549/qq_hd_mini_1.4.apk";
    private Context mContext;
    private static ServiceProvider mDownloader;
    private File apkFile;

    private ServiceProvider(Context context) {
        this.mContext = context;
    }

    /**
     * get instance
     *
     * @param context
     * @return
     */
    public static ServiceProvider getInstance(Context context) {
        if (mDownloader == null) {
            synchronized (ServiceProvider.class) {
                if (mDownloader == null) {
                    mDownloader = new ServiceProvider(context);
                }
            }
        }
        return mDownloader;
    }

    public void downlaodOrInstalled(String pakurl, String content, AdProto.Ad ad) {
        if (ad != null) {
            pakurl = APP_UPDATE_SERVER_URL;
            File dir = StorageUtils.getCacheDirectory(mContext);
            String apkName = pakurl.substring(pakurl.lastIndexOf("/") + 1, pakurl.length());
            apkFile = new File(dir, apkName);
            if (apkFile.exists()) {/**先判断该apk是否存在，如果存在就直接安装，否则就去下载*/
                SDKUtils.installApk(mContext, apkFile);
            } else {
                showNotification(content, pakurl, ad, "");
            }
        }
    }

    public void downlaodOrInstalled(String pakurl, String content, AdProto.Ad ad, String clicKId) {
        if (ad != null) {
            pakurl = APP_UPDATE_SERVER_URL;
            File dir = StorageUtils.getCacheDirectory(mContext);
            String apkName = pakurl.substring(pakurl.lastIndexOf("/") + 1, pakurl.length());
            apkFile = new File(dir, apkName);
            if (apkFile.exists()) {/**先判断该apk是否存在，如果存在就直接安装，否则就去下载*/
                SDKUtils.installApk(mContext, apkFile);
            } else {
                ToastUtils.showToast(mContext, "开始下载应用...");
                showNotification(content, pakurl, ad, clicKId);
            }
        }
    }

    /**
     * install apk
     */
    public void installApkDialog(final File file, String updateLog) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(R.string.newUpdateAvailable);
        builder.setMessage(updateLog)
                .setPositiveButton(R.string.install, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (!file.exists()) {
                            return;
                        }
                        Intent i = new Intent();
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.setAction(Intent.ACTION_VIEW);
                        i.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                        mContext.startActivity(i);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(R.string.dialogNegativeButton, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }

    /**
     * Show Notification
     */
    public void showNotification(String content, String apkUrl, AdProto.Ad ad, String clickId) {
//        Notification noti;
//        Intent myIntent = new Intent(mContext, DownloadService.class);
//        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        myIntent.putExtra(Constants.APK_DOWNLOAD_URL, apkUrl);
//        PendingIntent pendingIntent = PendingIntent.getService(mContext, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        int smallIcon = mContext.getApplicationInfo().icon;
//        noti = new NotificationCompat.Builder(mContext).setTicker(mContext.getString(R.string.newUpdateAvailable))
//                .setContentTitle(mContext.getString(R.string.newUpdateAvailable)).setContentText(content).setSmallIcon(smallIcon)
//                .setContentIntent(pendingIntent).build();
//
//        noti.flags = Notification.FLAG_AUTO_CANCEL;
//        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(0, noti);
        Intent intent = new Intent(mContext, AdSdkService.class);
        intent.putExtra(LocaalConstant.APK_DOWNLOAD_AD, ad);
        intent.putExtra(LocaalConstant.APK_DOWNLOAD_URL, apkUrl);
        intent.putExtra(LocaalConstant.APK_CLICK_ID, clickId);
        mContext.startService(intent);
    }


    /**
     * Check if a network available
     */
    public boolean isNetworkAvailable(Context context) {
        boolean connected = false;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo ni = cm.getActiveNetworkInfo();
            if (ni != null) {
                connected = ni.isConnected();
            }
        }
        return connected;
    }
}
