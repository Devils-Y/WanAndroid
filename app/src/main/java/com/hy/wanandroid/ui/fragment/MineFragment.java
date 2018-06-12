package com.hy.wanandroid.ui.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hy.wanandroid.R;
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
        userName.setOnClickListener(this);
        myCollection = (TextView) getContentView().findViewById(R.id.myCollection);
        myCollection.setOnClickListener(this);
        setting = (TextView) getContentView().findViewById(R.id.setting);
        setting.setOnClickListener(this);
        about = (TextView) getContentView().findViewById(R.id.about);
        about.setOnClickListener(this);
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
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.myCollection:
                break;
            case R.id.setting:
                break;
            case R.id.about:
                break;
        }
    }
}
