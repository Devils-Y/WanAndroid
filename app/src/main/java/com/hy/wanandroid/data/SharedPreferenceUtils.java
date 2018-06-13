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
        editor.apply();
    }

    public static boolean ReadTree() {
        init("TREE");
        boolean isLists = sharedPreference.getBoolean("tree", false);
        return isLists;
    }

    public static void WriteProjectType(int type) {
        init("PROJECT_TYPE");
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putInt("projectType", type);
        editor.apply();
    }

    public static int ReadProjectType() {
        init("PROJECT_TYPE");
        int mType = sharedPreference.getInt("projectType", 0);
        return mType;
    }

    public static void WriteUsername(String username) {
        init("USERNAME");
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putString("username", username);
        editor.apply();
    }

    public static String ReadUsername() {
        init("USERNAME");
        String username = sharedPreference.getString("username",null);
        return username;
    }
}
