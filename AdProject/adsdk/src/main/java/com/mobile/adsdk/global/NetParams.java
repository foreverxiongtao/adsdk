package com.mobile.adsdk.global;

import com.mobile.adsdk.openapi.GlobalSdkConfig;
import com.mobile.adsdk.utils.MD5Builder;
import com.mobile.adsdk.utils.SDKUtils;
import com.mobile.adsdk.widget.banner.AdConfig;

import java.util.UUID;

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
 * 创建日期 :2016/12/20       13:55
 *
 * 描 述 :接口参数定义类
 *
 * 修订日期 :
 */


public class NetParams {

    private static NetParams params = new NetParams();

    private NetParams() {

    }

    public static NetParams getInstance() {
        return params;
    }

    /**
     * 生成一个android终端的广告请求
     *
     * @param config
     */
    public AdProto.MobadsRequest generateKsAndroidRequest(AdConfig config) {

        GlobalSdkConfig mAdSdkConfig = SDKUtils.getGlobalSdkConfig();

        //广告位信息（广告位ID，广告位宽，广告位高）
        AdProto.AdSlot.Builder slot = AdProto.AdSlot.newBuilder();
        slot.setAdslotType(config.mAdType).setAdslotId(config.mAdId).setAdslotSize(AdProto.Size.newBuilder().setWidth(config.mWidth).setHeight(config.mHeigt));

        //APP信息（应用ID，包名，版本号）
        AdProto.App.Builder app = AdProto.App.newBuilder();
        // app.setAppId(config.mAppId).setAppPackage("com.example.ANDROIDdebug");
        app.setAppVersion(AdProto.Version.newBuilder().setMajor(3).setMinor(4).setMicro(0));
        app.setAppId(mAdSdkConfig.getAppId()).setAppPackage(mAdSdkConfig.getPackageName());
        String appVersion = String.valueOf(mAdSdkConfig.getmAppVersion());
        /*int mag = getMajor(appVersion);//系统第一位
        int min = getMinor(appVersion);//系统第二位
        int mic = getMicro(appVersion);//系统第三位*/
        //app.setAppVersion(AdProto.Version.newBuilder().setMajor(mag).setMinor(min).setMicro(mic));

        //设备识别信息（MAC地址，IMEI，AndroidID）
        AdProto.UdId.Builder uId = AdProto.UdId.newBuilder();
        // uId.setMac("d8:55:a3:ce:e4:40").setImei("866926020248380").setAndroidId("5b7e9e4f42a6635f");
        uId.setMac(mAdSdkConfig.getApMac()).setImei(mAdSdkConfig.getImei()).setAndroidId(mAdSdkConfig.getAndroidID());

        //设备信息（设备类型，系统类型，设备厂商，设备型号，操作系统版本，设备识别信息，屏幕尺寸）
        AdProto.Device.Builder device = AdProto.Device.newBuilder();
        //device.setDeviceType(AdProto.Device.DeviceType.PHONE);
        //device.setOsType(AdProto.Device.OsType.ANDROID);
        //device.setVendor(com.google.protobuf.ByteString.copyFrom("MEIZU".getBytes()));
        //device.setModel(com.google.protobuf.ByteString.copyFrom("MX5".getBytes()));
        //device.setOsVersion(AdProto.Version.newBuilder().setMajor(3).setMinor(1).setMicro(0));
        //device.setUdid(uId).setScreenSize(AdProto.Size.newBuilder().setWidth(1080).setHeight(1920));
        device.setDeviceType(AdProto.Device.DeviceType.valueOf(mAdSdkConfig.getDeviceType()));
        device.setOsType(AdProto.Device.OsType.ANDROID);
        device.setVendor(SDKUtils.StrToByStr(mAdSdkConfig.getVendor()));
        device.setModel(SDKUtils.StrToByStr(mAdSdkConfig.getModel()));
        String osVersin = mAdSdkConfig.getOsVersion();
        int magor = getMajor(osVersin);//系统第一位
        int minor = getMinor(osVersin);//系统第二位
        int micro = getMicro(osVersin);//系统第三位
        device.setOsVersion(AdProto.Version.newBuilder().setMajor(magor).setMinor(minor).setMicro(micro));

        device.setUdid(uId).setScreenSize(AdProto.Size.newBuilder().setWidth((int) mAdSdkConfig.getScreenWidth()).setHeight((int) mAdSdkConfig.getScreenHeight()));


        //网络信息（网络连接类型，运营商类型，IP地址）
        AdProto.Network.Builder connType = AdProto.Network.newBuilder();
        //connType.setConnectionType(AdProto.Network.ConnectionType.WIFI);
        //connType.setOperatorType(AdProto.Network.OperatorType.CHINA_MOBILE);
        //connType.setIpv4("114.255.44.132");
        connType.setConnectionType(AdProto.Network.ConnectionType.valueOf(mAdSdkConfig.getConnectionType()));
        connType.setOperatorType(AdProto.Network.OperatorType.valueOf(mAdSdkConfig.getOperatorID()));
        connType.setIpv4(mAdSdkConfig.getIPv4());

        //GPS信息
        AdProto.Gps.Builder gps = AdProto.Gps.newBuilder();
        gps.setCoordinateType(AdProto.Gps.CoordinateType.WGS84);
        gps.setLatitude(mAdSdkConfig.getGPSLatitude()).setLongitude(mAdSdkConfig.getGPSLongtitude()).setTimestamp(mAdSdkConfig.getTime());
        //gps.setCoordinateType(AdProto.Gps.CoordinateType.WGS84);
        //gps.setLatitude(40.7127).setLongitude(74.0059).setTimestamp(mAdSdkConfig.getTime());

        //请求信息（请求ID，协议版本号，广告位信息，APP信息，设备信息，网络信息，GPS信息，调试模式）
        AdProto.MobadsRequest.Builder request = AdProto.MobadsRequest.newBuilder();
        request.setRequestId(MD5Builder.getMD5(UUID.randomUUID().toString()));
        request.setApiVersion(AdProto.Version.newBuilder().setMajor(5).setMinor(0).setMicro(0));
        request.setAdslot(slot).setApp(app).setDevice(device).setNetwork(connType).setGps(gps).setIsDebug(true);
        //request.setApiVersion(AdProto.Version.newBuilder().setMajor(5).setMinor(3).setMicro(0));
        //request.setAdslot(slot).setApp(app).setDevice(device).setNetwork(connType).setGps(gps).setIsDebug(true);
        return request.build();
    }

    private int getMicro(String osVersin) {
        int result = 0;
        String substring = osVersin.substring(0, 1);
        result = Integer.parseInt(substring);
        return result;
    }

    private int getMinor(String osVersin) {
        int result = 0;
        if (osVersin.length() > 2) {
            String substring = osVersin.substring(2, 3);
            result = Integer.parseInt(substring);
        }
        return result;
    }

    private int getMajor(String osVersin) {
        int result = 0;
        if (osVersin.length() > 3) {
            result = Integer.parseInt(osVersin.substring(4, osVersin.length()));
        }
        return result;
    }
}
