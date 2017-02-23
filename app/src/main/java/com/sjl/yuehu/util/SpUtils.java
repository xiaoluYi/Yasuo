package com.sjl.yuehu.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by hefuyi on 16/7/28.
 */
public class SpUtils {

    public static final String TAG = SpUtils.class.getSimpleName();

    //标识是否是第一次使用
    private static final String SHARED_PREF_IS_FIRST_LAUNCH = "shared_pref_is_first_launch";
    //标识是否阅读过
    private static final String SHARED_PREF_IS_READ = "shared_pref_is_read";

    //startImage
    private static final String SHARED_PREF_SPLASH_JSON = "shared_pref_splash_json";

    private static final String SHARED_PREF_IS_NIGHE_MODE = "shared_pref_is_nighe_mode";

    private static SharedPreferences getDefaultSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static boolean isFirstLaunch(Context context) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(SHARED_PREF_IS_FIRST_LAUNCH, true);
    }

    public static void markFirstLaunch(Context context) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences(context);
        sharedPreferences.edit().putBoolean(SHARED_PREF_IS_FIRST_LAUNCH, false).apply();
    }

    public static boolean isNightMode(Context context) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(SHARED_PREF_IS_NIGHE_MODE, false);
    }

    public static void markIsNightMode(Context context,boolean isNightMode) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences(context);
        sharedPreferences.edit().putBoolean(SHARED_PREF_IS_NIGHE_MODE, isNightMode).apply();
    }

    public static String getSplashJson(Context context) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences(context);
        return sharedPreferences.getString(SHARED_PREF_SPLASH_JSON, null);
    }

    public static void setSplashJson(Context context, String jsonString) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences(context);
        sharedPreferences.edit().putString(SHARED_PREF_SPLASH_JSON, jsonString).apply();
    }


    public static void setIsRead(Context context, int adapterPosition, boolean b) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences(context);
        sharedPreferences.edit().putBoolean(String.valueOf(adapterPosition), b).apply();
    }

    public static boolean getIsRead(Context context, int adapterPosition) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(String.valueOf(adapterPosition),false);
    }
}
