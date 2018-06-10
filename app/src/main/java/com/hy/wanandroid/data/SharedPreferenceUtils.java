package com.hy.wanandroid.data;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.hy.wanandroid.application.WA_Application;

/**
 * author: huyin
 * date: 2018/6/10
 */
public class SharedPreferenceUtils {

    public static SharedPreferences sharedPreference;

    @SuppressLint("WrongConstant")
    public static void init(String fileName) {
        sharedPreference = WA_Application.getContext()
                .getSharedPreferences(fileName, Context.MODE_ENABLE_WRITE_AHEAD_LOGGING);
    }

    public static void WriteTree(boolean isList) {
        init("TREE");
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putBoolean("tree", isList);
        editor.commit();
    }

    public static boolean ReadTree() {
        init("TREE");
        boolean isLists = sharedPreference.getBoolean("tree", false);
        return isLists;
    }
}
