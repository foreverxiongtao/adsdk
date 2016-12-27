package com.mobile.adsdk.openapi;

/*
 *
 *
 * 版 权 :@Copyright desperado版权所有
 *
 * 作 者 :desperado
 *
 * 版 本 :1.0
 *
 * 创建日期 :2016/12/19       12:08
 *
 * 描 述 :sdk的相关参数配置
 *
 * 修订日期 :
 */

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public  class GlobalSdkConfig {
    public static final int WGS84_VALUE = 1;//全球卫星定位系统坐标系
    public static final int GCJ02_VALUE = 2;//国家测绘局坐标系
    public static final int BD09_VALUE = 3;//百度坐标系

    private Context mContext;
    private String mAppId;
    private boolean mSupportedDebug;
    //屏幕宽度
    private float mScreenWidth;
    //屏幕高度
    private float mScreenHeight;
    //包名
    private String packageName;
    //经度
    private double longtitude;
    //纬度
    private double latitude;
    //手机厂商
    private String vendor;
    //安卓设备唯一标识码imei
    private String imei;
    //网络类型
    private int connection_type;
    /*private final int CONNECTION_UNKNOWN_VALUE = 0;//无法探测当前网络状态
    private final int CELL_UNKNOWN_VALUE = 1;//未知网络状态
    private final int CELL_2G_VALUE = 2;//2g网络
    private final int CELL_3G_VALUE = 3;//3g网络
    private final int CELL_4G_VALUE = 4;//4g网络
    private final int CELL_5G_VALUE = 5;//5g网络
    private final int WIFI_VALUE = 100;//wifi
    private final int ETHERNET_VALUE = 101;//太网
    private final int NEW_TYPE_VALUE = 999;//未知的新类型网络*/
    //app_id
    private String app_id;
    //程序版本号
    private int app_version;
    //设备型号
    private String model;
    //手机的操作系统版本
    private String os_version;
    //热点mac地址
    private String ap_mac;
    //热点地址
    private String ap_name;
    //热点信号强度
    private int rssi;
    //是否连接wifi
    private boolean is_connected;
    //运营商id
    private int operator_id;
    //设备类型
    private int device_type;
    //安卓操作系统
    private final int ANDROID_VALUE = 1;
    //android设备系统id
    private String android_id;
    //IPv4
    private String IPv4;
    //运营商基站ID
    private int cellular_id;
    //时间戳
    private int time;
    //广告位类型
    private final int BANNER = 1;
    private final int INTERSTITIAL = 2;//插屏Interstitial
    private final int COOPEN = 4;//开屏
    private final int NATIVE = 8;//原生
    private final int REWARD = 9;//奖励

    private GlobalSdkConfig(Builder builder) {
        this.mContext = builder.mContext;
        this.mAppId = builder.mAppId;
        this.mSupportedDebug = builder.mSupportedDebug;
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public String getmApiKey() {
        return mAppId;
    }

    public void setmApiKey(String mApiKey) {
        this.mAppId = mApiKey;
    }

    public boolean ismSupportedDebug() {
        return mSupportedDebug;
    }

    public void setPackageName(String packageName){
        this.packageName = packageName;
    }

    public String getPackageName(){
        return packageName ;
    }

    //获取程序的版本号
    public int getmAppVersion(){
        return app_version;
    }

    public void setmSupportedDebug(boolean mSupportedDebug) {
        this.mSupportedDebug = mSupportedDebug;
    }

    //设备型号
    public void setModel(String model){
        this.model = model;
    }

    public String getModel(){
        return model;
    }

    //手机厂商
    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getVendor(){
        return vendor;
    }

    //手机的操作系统版本
    public void setOsVersion(String os_version) {
        this.os_version = os_version;
    }

    public String getOsVersion(){
        return os_version;
    }
    //应用版本
    public void setAppVersion(int appVersion) {
        this.app_version = appVersion;
    }

    public int getAppVersion(){
        return app_version;
    }

    //手机屏幕宽度
    public void setScreenWidth(float screenWidth) {
        this.mScreenWidth = screenWidth;
    }

    public float getScreenWidth(){
        return mScreenWidth;
    }

    //手机屏幕高度
    public void setScreenHeigh(float screenHeigh) {
        this.mScreenHeight = screenHeigh;
    }

    public float getScreenHeight(){
        return mScreenHeight;
    }

    //热点mac地址
    public void setApMac(String ap_mac) {
        this.ap_mac = ap_mac;
    }

    public String getApMac(){
        return ap_mac;
    }

    //热点名称
    public void setApName(String ap_name) {
        this.ap_name = ap_name;
    }

    public String getApName(){
        return ap_name;
    }

    //热点信号强度
    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    public int getRssi(){
        return rssi;
    }

    //是否连接wifi
    public void setIsConnected(boolean is_connected) {
        this.is_connected = is_connected;
    }

    public boolean getIsConnected(){
        return is_connected;
    }

    //获取运营商ID
    public void setOperatorID(int operator_id) {
        this.operator_id = operator_id;
    }

    public int getOperatorID(){
        return operator_id;
    }

    //网络类型
    public void setConnectedType(int connection_unknown_value) {
        this.connection_type = connection_unknown_value;
    }

    public int getConnectionType(){
        return connection_type;
    }

    //设备类型
    public void setDeviceType(int device_type) {
        this.device_type = device_type;
    }

    public int getDeviceType(){
        return device_type;
    }

    //操作系统
    public int getOS(){
        return ANDROID_VALUE;
    }

    //安卓设备唯一标识码imei
    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getImei(){
        return imei;
    }

    //android设备系统id
    public void setAndroidID(String android_id) {
        this.android_id = android_id;
    }

    public String getAndroidID(){
        return android_id;
    }

    //IPv4
    public void setIPv4(String IPv4) {
        this.IPv4 = IPv4;
    }

    public String getIPv4(){
        //会有无网络连接状态
        return IPv4;
    }

    //运营商基站ID
    public void setCellularId(int cellularId) {
        this.cellular_id = cellularId;
    }

    public int getCellularId(){
        return cellular_id;
    }

    //时间戳
    public void setTime(int time) {
        this.time = time;
    }

    public int getTime(){
        return time;
    }

    //appid
    public void setAppId(String app_id) {
        this.app_id = app_id;
    }

    public String getAppId(){
        return app_id;
    }

    //经度
    public void setGPSLongtitude(double longitude) {
        this.longtitude = longitude;
    }

    public double getGPSLongtitude(){
        return longtitude;
    }

    //纬度
    public void setGPSLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getGPSLatitude(){
        return latitude;
    }

    public static class Builder {
        private Context mContext;
        private String mAppId;
        private boolean mSupportedDebug;

        public Builder context(Context context) {
            this.mContext = context;
            return this;
        }

        public Builder apikey(String apikey) {
            this.mAppId = apikey;
            return this;
        }

        public Builder supprotDebug(boolean supportDebug) {
            this.mSupportedDebug = supportDebug;
            return this;
        }

        public GlobalSdkConfig build() {
            return new GlobalSdkConfig(this);
        }
    }


}
