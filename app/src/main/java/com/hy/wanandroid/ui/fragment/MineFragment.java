package com.hy.wanandroid.ui.fragment;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hy.wanandroid.R;
import com.hy.wanandroid.data.SharedPreferenceUtils;
import com.hy.wanandroid.ui.activity.CollectListActivity;
import com.hy.wanandroid.ui.activity.LoginActivity;

/**
 * Created by huyin on 2018/4/24.
 * <p>
 * 我的     /   个人中心
 */

public class MineFragment extends BaseFragment implements View.OnClickListener {

    TextView userName;
    TextView myCollection;
    TextView setting;
    TextView about;

    @Override
    public int setContentLyaoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void viewInit() {
        userName = getContentView().findViewById(R.id.userName);
        myCollection = (TextView) getContentView().findViewById(R.id.myCollection);
        myCollection.setOnClickListener(this);
        setting = (TextView) getContentView().findViewById(R.id.setting);
        setting.setOnClickListener(this);
        about = (TextView) getContentView().findViewById(R.id.about);
        about.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        //判断是否可以点击
        if (TextUtils.isEmpty(SharedPreferenceUtils.ReadUsername())) {
            userName.setOnClickListener(this);
            userName.setText(R.string.not_login_txt);
        } else {
            userName.setText(SharedPreferenceUtils.ReadUsername());
        }
    }

    @Override
    public void dataInit() {
    }

    @Override
    public void viewDestroy() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.userName:
                Intent intentLogin = new Intent(getActivity(), LoginActivity.class);
                startActivity(intentLogin);
                break;
            case R.id.myCollection:
                Intent intentCollect = new Intent(getActivity(), CollectListActivity.class);
                startActivity(intentCollect);
                break;
            case R.id.setting:
                break;
            case R.id.about:
                break;
        }
    }
}
