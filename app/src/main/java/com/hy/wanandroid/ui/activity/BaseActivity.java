package com.hy.wanandroid.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
/**
 * Created by huyin on 2018/4/17.
 */

public abstract class BaseActivity extends AppCompatActivity {

     @Override
     protected void onCreate(@Nullable Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView();

          viewInit();

          dataInit();
     }

     /**
      * 设置布局
      */
     public abstract void setContentView();

     /**
      * 控件初始化
      */
     public abstract void viewInit();

     /**
      * 数据初始化
      */
     public abstract void dataInit();

     @Override
     protected void onSaveInstanceState(Bundle outState) {
          super.onSaveInstanceState(outState);
     }

     @Override
     protected void onRestoreInstanceState(Bundle savedInstanceState) {
          super.onRestoreInstanceState(savedInstanceState);
     }
}
