package com.mobile.adsdk.entity;

/**
 * Created by azkf-XT on 2016/12/22.
 */

public enum AdSlotType {
    AD_BANNER(1), AD_INTERSTITIAL(2), AD_SPLASH(4), AD_NATIVE(8);
    private int value;

    AdSlotType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
