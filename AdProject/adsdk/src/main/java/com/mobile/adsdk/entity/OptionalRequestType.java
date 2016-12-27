package com.mobile.adsdk.entity;

/**
 * Created by azkf-XT on 2016/12/22.
 */

public enum OptionalRequestType {
    AD_REQUEST("1");
    private String value;

    OptionalRequestType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
