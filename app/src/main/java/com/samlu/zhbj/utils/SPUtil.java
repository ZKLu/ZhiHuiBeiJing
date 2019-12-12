package com.samlu.zhbj.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by sam lu on 2019/12/12.
 */

public class SPUtil {

    private static final String SHARE_PREFS_NAME = "zhbj";

    public static void putBoolean (Context ctx,String key,boolean value){
        SharedPreferences sp = ctx.getSharedPreferences(SHARE_PREFS_NAME,Context.MODE_PRIVATE);
        sp.edit().putBoolean(key,value).commit();
    }
    public static boolean getBoolean (Context ctx,String key,boolean defaultValue){
        SharedPreferences sp = ctx.getSharedPreferences(SHARE_PREFS_NAME,Context.MODE_PRIVATE);
        return sp.getBoolean(key,defaultValue);
    }
}
