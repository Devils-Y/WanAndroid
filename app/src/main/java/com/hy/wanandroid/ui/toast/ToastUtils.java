package com.hy.wanandroid.ui.toast;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.hy.wanandroid.application.WA_Application;


/**
 * 吐司工具类
 */
public class ToastUtils {

     public static void toast(final String content) {
          new Handler(Looper.getMainLooper()).post(new Runnable() {
               @Override
               public void run() {
                    CustomToast.showToast(WA_Application.getContext(), content, 2000);
               }
          });
     }

     public static void toast(final int resID) {
          new Handler(Looper.getMainLooper()).post(new Runnable() {
               @Override
               public void run() {
                    CustomToast.showToast(WA_Application.getContext(), resID, 1000);
               }
          });
     }

     public static void toast(final Context context, final String content) {
          new Handler(Looper.getMainLooper()).post(new Runnable() {
               @Override
               public void run() {
                    CustomToast.showToast(context, content, 1000);
               }
          });
     }

     public static void toast(final Context context, final int resID) {
          new Handler(Looper.getMainLooper()).post(new Runnable() {
               @Override
               public void run() {
                    CustomToast.showToast(context, resID, 1000);
               }
          });
     }

     public static void longToast(final String content) {
          new Handler(Looper.getMainLooper()).post(new Runnable() {
               @Override
               public void run() {
                    CustomToast.showToast(WA_Application.getContext(), content, 3000);
               }
          });
     }


     public static void longToast(final Context context, final String content) {
          new Handler(Looper.getMainLooper()).post(new Runnable() {
               @Override
               public void run() {
                    CustomToast.showToast(context, content, 3000);
               }
          });
     }

     public static void longToast(final Context context, final int resID) {
          new Handler(Looper.getMainLooper()).post(new Runnable() {
               @Override
               public void run() {
                    CustomToast.showToast(context, resID, 3000);
               }
          });
     }

}
