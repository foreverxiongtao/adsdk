package com.mobile.adsdk.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.google.protobuf.ByteString;
import com.mobile.adsdk.entity.OptionalRequestType;
import com.mobile.adsdk.global.LocaalConstant;
import com.mobile.adsdk.openapi.GlobalSdkConfig;
import com.mobile.adsdk.ui.AdActivity;
import com.mobile.adsdk.update.ServiceProvider;
import com.mobile.adsdk.widget.nav.OptionalRequestAd;

import java.io.File;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.List;

import ks.AdProto;

/**
 * Created by azkf-XT on 2016/12/20.
 */

public class SDKUtils {
    private static volatile GlobalSdkConfig mAdSdkConfig;

    private static final int UNKNOWN_OPERATOR_VALUE = 0;//未知的
    private final int CHINA_MOBILE_VALUE = 1;//中国移动
    private final int CHINA_TELECOM_VALUE = 2;//中国电信
    private final int CHINA_UNICOM_VALUE = 3;//中国联通
    private final int OTHER_OPERATOR_VALUE = 99;//其他运行商

    private final int CONNECTION_UNKNOWN_VALUE = 0;//无法探测当前网络状态
    private final int CELL_UNKNOWN_VALUE = 1;//未知网络状态
    private final int CELL_2G_VALUE = 2;//2g网络
    private final int CELL_3G_VALUE = 3;//3g网络
    private final int CELL_4G_VALUE = 4;//4g网络
    private final int CELL_5G_VALUE = 5;//5g网络
    private final int WIFI_VALUE = 100;//wifi
    private final int ETHERNET_VALUE = 101;//太网
    private final int NEW_TYPE_VALUE = 999;//未知的新类型网络
    private final int PHONE_VALUE = 1;//设备类型  手机
    private final int TABLET_VALUE = 2;//平板
    private String packname;
    private static SDKUtils sdkUtils = null;
    // private static DataUtils dataUtils = null;

    private SDKUtils() {
    }

    static {
        mAdSdkConfig = new GlobalSdkConfig.Builder().build();
    }

    public static GlobalSdkConfig getGlobalSdkConfig() {
        return mAdSdkConfig;
    }

    public static SDKUtils getSDKUtils(Context context) {
        if (sdkUtils == null) {
            sdkUtils = new SDKUtils();
        }
        return sdkUtils;
    }

    public void init(Context context) {
        initData(context);
    }

    private void initData(Context context) {
        mAdSdkConfig.setmContext(context);
        getAppProcessName(context);
        getAppVersion(context, packname);
        getModel();
        getVendor();
        getOsVersion();
        getAppId(context);
        getScreenSize(context);
        getApMacAndApName(context);
        getIsConnected(context);
        getOperatorID(context);
        getConnectionType(context);
        getImei(context);
        getAndroidID(context);
        GetIPv4(context);
        getCellularId(context);
        getGPS(context);
        getTime();

    }

    //获取程序的版本号
    private void getAppVersion(Context context, String packname) {
        int versionCode = 0;
        //包管理操作管理类
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo packinfo = pm.getPackageInfo(packname, 0);
            versionCode = packinfo.versionCode;
            mAdSdkConfig.setAppVersion(versionCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //获取应用包名
    private void getAppProcessName(Context context) {
        String processName = "";
        //当前应用pid
        int pid = android.os.Process.myPid();
        //任务管理类
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        //遍历所有应用
        List<ActivityManager.RunningAppProcessInfo> infos = manager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo info : infos) {
            if (info.pid == pid)//得到当前应用
                this.packname = info.processName;
            mAdSdkConfig.setPackageName(packname);//返回包名
        }
    }

    //获取设备型号
    private void getModel() {
        String model = "";
        model = android.os.Build.MODEL;
        mAdSdkConfig.setModel(model);
    }

    //获取设备厂商
    private void getVendor() {
        String vendor = "";
        vendor = android.os.Build.MANUFACTURER;
        mAdSdkConfig.setVendor(vendor);
    }

    //获取手机系统版本
    private void getOsVersion() {
        String osVersion = "";
        osVersion = android.os.Build.VERSION.RELEASE;
        mAdSdkConfig.setOsVersion(osVersion);
    }

    //获取屏幕尺寸
    private void getScreenSize(Context context) {
       /* DisplayMetrics dm = new DisplayMetrics();
         //获取屏幕信息
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeigh = dm.heightPixels;
        mAdSdkConfig.setScreenWidth(screenWidth);
        mAdSdkConfig.setScreenHeigh(screenHeigh);*/
        float screenWidth = 0.0f;
        float screenHeigh = 0.0f;
        double x = 0.0;
        double y = 0.0;
        double screenInches = 0.0;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        // 屏幕宽度
        screenWidth = display.getWidth();
        // 屏幕高度
        screenHeigh = display.getHeight();
        mAdSdkConfig.setScreenWidth(screenWidth);
        mAdSdkConfig.setScreenHeigh(screenHeigh);
        DisplayMetrics dm = new DisplayMetrics();
        display.getMetrics(dm);
        x = Math.pow(dm.widthPixels / dm.xdpi, 2);
        y = Math.pow(dm.heightPixels / dm.ydpi, 2);
        // 屏幕尺寸
        screenInches = Math.sqrt(x + y);
        // 大于6尺寸则为Pad
        if (screenInches >= 6.0) {
            /*private final int PHONE_VALUE = 1;//设备类型  手机
            private final int TABLET_VALUE = 2;//平板*/
            mAdSdkConfig.setDeviceType(TABLET_VALUE);
        } else {
            mAdSdkConfig.setDeviceType(PHONE_VALUE);
        }

    }

    //获取mac地址、wifi名称、wifi连接强度
    private void getApMacAndApName(Context context) {
        String mac = "";
        String apName = "";
        int linkSpeed = 0;
        WifiManager wifiMan = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifiMan.getConnectionInfo();
        mac = info.getMacAddress();// 获得本机的MAC地址
        mAdSdkConfig.setApMac(mac);
        apName = info.getSSID();// 获得本机所链接的WIFI名称
        mAdSdkConfig.setApName(apName);
        linkSpeed = info.getLinkSpeed();//获取本机连接wifi强度
        mAdSdkConfig.setRssi(linkSpeed);
    }

    //是否连接wifi
    private void getIsConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWiFiNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mWiFiNetworkInfo != null) {
                mAdSdkConfig.setIsConnected(mWiFiNetworkInfo.isAvailable());
            }
        }
        mAdSdkConfig.setIsConnected(false);
    }

    //获取运营商id
    private void getOperatorID(Context context) {
        //private static final int UNKNOWN_OPERATOR_VALUE = 0;
        /*private final int CHINA_MOBILE_VALUE = 1;//中国移动
        private final int CHINA_TELECOM_VALUE = 2;//中国电信
        private final int CHINA_UNICOM_VALUE = 3;//中国联通
        private final int OTHER_OPERATOR_VALUE = 99;//其他运行商*/
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        int ProvidersName = 0;
        // 返回唯一的用户ID;就是这张卡的编号神马的
        String subscriberId = telephonyManager.getSubscriberId();
        // IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。
        if (subscriberId != null) {
            if (subscriberId.startsWith("46000") || subscriberId.startsWith("46002")) {
                ProvidersName = CHINA_MOBILE_VALUE;
            } else if (subscriberId.startsWith("46001")) {
                ProvidersName = CHINA_UNICOM_VALUE;
            } else if (subscriberId.startsWith("46003")) {
                ProvidersName = CHINA_TELECOM_VALUE;
            }
            mAdSdkConfig.setOperatorID(ProvidersName);
        } else {
            ProvidersName = UNKNOWN_OPERATOR_VALUE;
            mAdSdkConfig.setOperatorID(ProvidersName);
        }
    }

    //网络连接类型
    private void getConnectionType(Context context) {
       /* //没有网络连接
        public static final int NETWORN_NONE = 0;
        //wifi连接
        public static final int NETWORN_WIFI = 1;
        //手机网络数据连接类型
        public static final int NETWORN_2G = 2;
        public static final int NETWORN_3G = 3;
        public static final int NETWORN_4G = 4;
        public static final int NETWORN_MOBILE = 5;*/


        /*private final int CONNECTION_UNKNOWN_VALUE = 0;//无法探测当前网络状态
        private final int CELL_UNKNOWN_VALUE = 1;//未知网络状态
        private final int CELL_2G_VALUE = 2;//2g网络
        private final int CELL_3G_VALUE = 3;//3g网络
        private final int CELL_4G_VALUE = 4;//4g网络
        private final int CELL_5G_VALUE = 5;//5g网络
        private final int WIFI_VALUE = 100;//wifi
        private final int ETHERNET_VALUE = 101;//太网
        private final int NEW_TYPE_VALUE = 999;//未知的新类型网络*/
        //获取系统的网络服务
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //如果当前没有网络
        if (null == connManager) {
            mAdSdkConfig.setConnectedType(CONNECTION_UNKNOWN_VALUE);
        }
        //获取当前网络类型，如果为空，返回无网络
        NetworkInfo activeNetInfo = connManager.getActiveNetworkInfo();
        if (activeNetInfo == null || !activeNetInfo.isAvailable()) {
            mAdSdkConfig.setConnectedType(CONNECTION_UNKNOWN_VALUE);
        }
        // 判断是不是连接的是不是wifi
        NetworkInfo wifiInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (null != wifiInfo) {
            NetworkInfo.State state = wifiInfo.getState();
            if (null != state)
                if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING) {
                    mAdSdkConfig.setConnectedType(WIFI_VALUE);
                }
        }
        // 如果不是wifi，则判断当前连接的是运营商的哪种网络2g、3g、4g等
        NetworkInfo networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (null != networkInfo) {
            NetworkInfo.State state = networkInfo.getState();
            String strSubTypeName = networkInfo.getSubtypeName();
            if (null != state)
                if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING) {
                    switch (activeNetInfo.getSubtype()) {
                        //如果是2g类型
                        case TelephonyManager.NETWORK_TYPE_GPRS: // 联通2g
                        case TelephonyManager.NETWORK_TYPE_CDMA: // 电信2g
                        case TelephonyManager.NETWORK_TYPE_EDGE: // 移动2g
                        case TelephonyManager.NETWORK_TYPE_1xRTT:
                        case TelephonyManager.NETWORK_TYPE_IDEN:
                            mAdSdkConfig.setConnectedType(CELL_2G_VALUE);
                            break;
                        //如果是3g类型
                        case TelephonyManager.NETWORK_TYPE_EVDO_A: // 电信3g
                        case TelephonyManager.NETWORK_TYPE_UMTS:
                        case TelephonyManager.NETWORK_TYPE_EVDO_0:
                        case TelephonyManager.NETWORK_TYPE_HSDPA:
                        case TelephonyManager.NETWORK_TYPE_HSUPA:
                        case TelephonyManager.NETWORK_TYPE_HSPA:
                        case TelephonyManager.NETWORK_TYPE_EVDO_B:
                        case TelephonyManager.NETWORK_TYPE_EHRPD:
                        case TelephonyManager.NETWORK_TYPE_HSPAP:
                            mAdSdkConfig.setConnectedType(CELL_3G_VALUE);
                            break;
                        //如果是4g类型
                        case TelephonyManager.NETWORK_TYPE_LTE:
                            mAdSdkConfig.setConnectedType(CELL_4G_VALUE);
                            break;
                        default://未知
                            mAdSdkConfig.setConnectedType(CELL_UNKNOWN_VALUE);
                    }
                }
        }
    }

    //获取安卓设备唯一标识码  imei
    private void getImei(Context context) {
        String szImei = "";
        TelephonyManager TelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        szImei = TelephonyMgr.getDeviceId();
        mAdSdkConfig.setImei(szImei);
    }

    //获取安卓系统id
    private void getAndroidID(Context context) {
        String android_id = "";
        android_id = Settings.System.getString(context.getContentResolver(), Settings.System.ANDROID_ID);
        mAdSdkConfig.setAndroidID(android_id);
    }

    //获取IPv4
    private void GetIPv4(Context context) {
        // 获取WiFi服务
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        // 判断WiFi是否开启
        if (wifiManager.isWifiEnabled()) {
            // 已经开启了WiFi
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int ipAddress = wifiInfo.getIpAddress();
            String ipv4 = intToIp(ipAddress);
            mAdSdkConfig.setIPv4(ipv4);
        } else {
            // 未开启WiFi
            getIpAddress();
        }
    }

    private String intToIp(int ipAddress) {
        return (ipAddress & 0xFF) + "." +
                ((ipAddress >> 8) & 0xFF) + "." +
                ((ipAddress >> 16) & 0xFF) + "." +
                (ipAddress >> 24 & 0xFF);
    }

    /**
     * 获取本机IPv4地址
     *
     * @return 本机IPv4地址；null：无网络连接
     */
    private void getIpAddress() {
        try {
            NetworkInterface networkInterface;
            InetAddress inetAddress;
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                networkInterface = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = networkInterface.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress()) {
                        mAdSdkConfig.setIPv4(inetAddress.getHostAddress());
                    }
                }
            }
            mAdSdkConfig.setIPv4("无网络连接");
        } catch (Exception ex) {
            ex.printStackTrace();
            mAdSdkConfig.setIPv4("无网络连接");
        }
    }

    //运营商基站ID
    private void getCellularId(Context context) {
        int cellularId = 0;
        TelephonyManager mTelephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        // 返回值MCC + MNC
        String imsi = mTelephonyManager.getSubscriberId();
        if (imsi != null) {
            if (imsi.startsWith("46000") || imsi.startsWith("46002") || imsi.startsWith("46001")) {
                //中国移动||中国联通
                // 中国移动和中国联通获取LAC、CID的方式
                GsmCellLocation location = (GsmCellLocation) mTelephonyManager.getCellLocation();
                cellularId = location.getCid();
            } else if (imsi.startsWith("46003")) {
                //中国电信
                // 中国电信获取LAC、CID的方式
                CdmaCellLocation location1 = (CdmaCellLocation) mTelephonyManager.getCellLocation();
                cellularId = location1.getBaseStationId();
            }
        }
        mAdSdkConfig.setCellularId(cellularId);
    }

    //获取GPS
    private void getGPS(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setCostAllowed(true);
        String bestProvider = locationManager.getBestProvider(criteria, true);
        try {
            locationManager.requestLocationUpdates(bestProvider, 0, 0, new MyListener());
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private class MyListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            double longitude = 0.0;
            double latitude = 0.0;
            longitude = location.getLongitude();//经度
            latitude = location.getLatitude();//纬度
            System.out.print(longitude + "=======================================================================");
            System.out.print(latitude + "=======================================================================");
            mAdSdkConfig.setGPSLongtitude(longitude);
            mAdSdkConfig.setGPSLatitude(latitude);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    }

    //获取时间戳
    private void getTime() {
        int time = 0;
        long lcc_minute_time = System.currentTimeMillis();
        time = (int) (lcc_minute_time / 1000);
        mAdSdkConfig.setTime(time);
    }

    public static GlobalSdkConfig getmAdSdkConfig() {
        return mAdSdkConfig;
    }

    //获取appId
    public static String getAppId(Context context) {
        String appid = "";
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA);
            appid = appInfo.metaData.getString(LocaalConstant.APP_ID);
            AbLogUtil.e("Tag", " app key : " + appid);  // Tag﹕ app key : AIzaSyBhBFOgVQclaa8p1JJeqaZHiCo2nfiyBBo
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (TextUtils.isEmpty(appid)) {
            throw new RuntimeException("please set the adsdk_id in androidmanifest.mxl");
        }
        mAdSdkConfig.setAppId(appid);
        return appid;
    }

    public static String ByStrToStr(ByteString byteString) {
        String result = "";
        if (byteString != null && !byteString.isEmpty() && byteString.isValidUtf8()) {
            result = byteString.toStringUtf8();
        }
        return result;
    }

    public static ByteString StrToByStr(String source) {
        ByteString target = null;
        if (!TextUtils.isEmpty(source)) {
            target = ByteString.copyFrom(source.getBytes());
        }
        return target;
    }


    public static void clickAction(Activity activity, AdProto.MaterialMeta.InteractionType interactionType, String clickurl, String packname, AdProto.Ad ad) {
        if (SDKUtils.isFastClick()) {
            return;
        }
        if (activity == null || interactionType == null) {
            AbLogUtil.e("clickAction", "activity or interactionType is null");
            return;
        }
        if (interactionType.getNumber() == AdProto.MaterialMeta.InteractionType.DOWNLOAD.getNumber() && !TextUtils.isEmpty(clickurl)) {
            ServiceProvider.getInstance(activity).downlaodOrInstalled(clickurl, packname + "", ad);
        } else if (interactionType.getNumber() == AdProto.MaterialMeta.InteractionType.SURFING.getNumber()) {
            Intent intent = new Intent(activity, AdActivity.class);
            intent.putExtra(LocaalConstant.INTENT_WEB_URL, clickurl + "");
            intent.putExtra(LocaalConstant.INTENT_AD_SERIABLE, ad);
            activity.startActivity(intent);
        } else if (interactionType.getNumber() == AdProto.MaterialMeta.InteractionType.OPTIONAL.getNumber()) {
            OptionalRequestAd.getmInstance().handleOptionalRequest(activity, ad, OptionalRequestType.AD_REQUEST);
        }
    }


    /**
     * 安装APK
     */
    public static void installApk(Context context, File file) {
        if (!file.exists()) {
            return;
        }
        Intent i = new Intent();
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setAction(Intent.ACTION_VIEW);
        i.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        context.startActivity(i);
    }

    /**
     * 防止按钮被多次点击
     */
    private static long lastClickTime;

    public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 200) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
