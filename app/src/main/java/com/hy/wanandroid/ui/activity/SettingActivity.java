package com.hy.wanandroid.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.hy.wanandroid.R;
import com.hy.wanandroid.data.SharedPreferenceUtils;
import com.hy.wanandroid.net.HttpNet;
import com.hy.wanandroid.ui.toast.ToastUtils;
import com.hy.wanandroid.util.GlideCacheUtil;

import static com.hy.wanandroid.constants.Constants.CHANGE;

/**
 * author: huyin
 * date: 2018/6/15
 * <p>
 * 设置
 */
public class SettingActivity extends BaseActivity implements
        View.OnClickListener {

    Toolbar toolbar;
    TextView clearCache;
    TextView cacheSize;
    TextView logout;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_setting);
    }

    private void initTitle() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle(R.string.setting_app_txt);
        toolbar.setNavigationIcon(R.drawable.ic_back_left_white);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void viewInit() {
        initTitle();

        clearCache = (TextView) findViewById(R.id.clearCache);
        clearCache.setOnClickListener(this);
        cacheSize = findViewById(R.id.cacheSize);
        logout = (TextView) findViewById(R.id.logout);
        logout.setOnClickListener(this);
    }

    @Override
    public void dataInit() {
        cacheSize.setText(GlideCacheUtil.getInstance().getCacheSize(this));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.clearCache:
                GlideCacheUtil.getInstance().clearImageAllCache(this);
                cacheSize.setText(GlideCacheUtil.getInstance().getCacheSize(this));
                break;
            case R.id.logout:
                if (!TextUtils.isEmpty(SharedPreferenceUtils.ReadUsername())) {
                    HttpNet.getInstantes().httpNetClearCookie();
                    Intent intent = new Intent(CHANGE);
                    SharedPreferenceUtils.WriteUsername(null);
                    LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
                    ToastUtils.toast("退出成功");
                    finish();
                } else {
                    ToastUtils.toast("请先去登录");
                }
                break;
        }
    }
}
