package com.mobile.adsdk.inf;

import java.util.List;

import ks.AdProto;

/**
 * Created by azkf-XT on 2016/12/23.
 */

public interface IEventTrackingListener {

    void resetTrackListEvent(List<AdProto.Tracking> trackingList);

    void trackClickEvent();

    void trackCloseEvent();

    void trackDisplayEvent();

    void trackDownloadStartEvent();

    void trackDownloadCompletedEvent();

    void tranckDownloadInstallEvent();
}
