package com.mobile.adsdk.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.List;

/*
 *
 *
 * 版 权 :@Copyright desperado版权所有
 *
 * 作 者 :desperado
 *
 * 版 本 :1.0
 *
 * 创建日期 :2016/10/28  11:53
 *
 * 描 述 :SharedPreference工具类
 *
 * 修订日期 :
 */
public class SPUtils {
    public static final String GUID = "GUID".intern();

    public static final String SplashUpdateTime = "SplashUpdateTime".intern();
    public static final String SplashUpdateURL = "SplashUpdateURL".intern();


    private static SharedPreferences pref = null;
    private static SharedPreferences.Editor editor = null;

    public static String loadPrefString(Context context, String key) {
        return loadPrefString(context, key, null);
    }

    public static String loadPrefString(Context context, String key,
                                        String defaultValue) {
        if (pref == null) {
            pref = PreferenceManager.getDefaultSharedPreferences(context);
        }
        return pref.getString(key, defaultValue);
    }

    public static void savePrefString(Context context, String key, String value) {
        if (pref == null) {
            pref = PreferenceManager.getDefaultSharedPreferences(context);
        }
        if (editor == null) {
            editor = pref.edit();
        }
        editor.putString(key, value);
        editor.commit();
    }

    public static void savePrefString(Context context, String key, List<String> values) {
        if (pref == null) {
            pref = PreferenceManager.getDefaultSharedPreferences(context);
        }
        if (editor == null) {
            editor = pref.edit();
        }
    }


    public static int loadPrefInt(Context context, String key, int defaultValue) {
        if (pref == null) {
            pref = PreferenceManager.getDefaultSharedPreferences(context);
        }
        try {
            return pref.getInt(key, defaultValue);
        } catch (Exception e) {
            return defaultValue;
        }

    }

    public static void savePrefInt(Context context, String key, int value) {
        if (pref == null) {
            pref = PreferenceManager.getDefaultSharedPreferences(context);
        }

        if (editor == null) {
            editor = pref.edit();
        }
        editor.putInt(key, value);
        editor.commit();
    }

    public static long loadPrefLong(Context context, String key,
                                    long defaultValue) {
        if (pref == null) {
            pref = PreferenceManager.getDefaultSharedPreferences(context);
        }
        return pref.getLong(key, defaultValue);
    }

    public static void savePrefLong(Context context, String key, long value) {
        if (pref == null) {
            pref = PreferenceManager.getDefaultSharedPreferences(context);
        }
        if (editor == null) {
            editor = pref.edit();
        }
        editor.putLong(key, value);
        editor.commit();
    }

    public static float loadPrefFloat(Context context, String key,
                                      float defaultValue) {
        if (pref == null) {
            pref = PreferenceManager.getDefaultSharedPreferences(context);
        }
        return pref.getFloat(key, defaultValue);
    }

    public static void savePrefFloat(Context context, String key, float value) {
        if (pref == null) {
            pref = PreferenceManager.getDefaultSharedPreferences(context);
        }
        if (editor == null) {
            editor = pref.edit();
        }
        editor.putFloat(key, value);
        editor.commit();
    }

    public static boolean loadPrefBoolean(Context context, String key,
                                          boolean defaultValue) {
        if (pref == null) {
            pref = PreferenceManager.getDefaultSharedPreferences(context);
        }
        return pref.getBoolean(key, defaultValue);
    }

    public static void savePrefBoolean(Context context, String key, boolean value) {
        if (pref == null) {
            pref = PreferenceManager.getDefaultSharedPreferences(context);
        }
        if (editor == null) {
            editor = pref.edit();
        }
        editor.putBoolean(key, value);
        editor.commit();
    }

}
