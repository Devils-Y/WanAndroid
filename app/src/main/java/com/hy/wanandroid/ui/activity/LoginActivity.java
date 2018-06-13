package com.hy.wanandroid.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.hy.wanandroid.R;
import com.hy.wanandroid.bean.LoginBean;
import com.hy.wanandroid.data.SharedPreferenceUtils;
import com.hy.wanandroid.framework.presenter.LoginPresenter;
import com.hy.wanandroid.framework.view.LoginView;
import com.hy.wanandroid.ui.toast.ToastUtils;

import static com.hy.wanandroid.constants.Constants.LOGIN_SUCCESS;
import static com.hy.wanandroid.constants.Constants.PROJECTSTYLE;

/**
 * author: huyin
 * date: 2018/6/12
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener,
        LoginView {

    private Toolbar toolbar;
    LoginPresenter loginPresenter;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_login);
    }

    private void initTitle() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
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
        findViewById(R.id.loginBtn).setOnClickListener(this);
        findViewById(R.id.registerTv).setOnClickListener(this);

        receiveLoginSuccess();
    }

    private EditText getUsernameEdit() {
        return (EditText) findViewById(R.id.usernameEdit);
    }

    private EditText getPasswordEdit() {
        return (EditText) findViewById(R.id.passwordEdit);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.loginBtn:
                String username = getUsernameEdit().getText().toString();
                String password = getPasswordEdit().getText().toString();
                if (!TextUtils.isEmpty(username)
                        && !TextUtils.isEmpty(password)) {
                    loginPresenter.postLogin(username, password);
                } else {
                    if (TextUtils.isEmpty(username)) {
                        ToastUtils.toast("请输入用户名");
                    } else if (TextUtils.isEmpty(password)) {
                        ToastUtils.toast("请输入密码");
                    }
                }
                break;
            case R.id.registerTv:
                Intent intentLogin = new Intent(this, RegisterActivity.class);
                startActivity(intentLogin);
                break;
        }
    }

    @Override
    public void dataInit() {
        loginPresenter = new LoginPresenter(this);
    }

    @Override
    public void setLogin(LoginBean login) {
        SharedPreferenceUtils.WriteUsername(login.getUsername());
        ToastUtils.toast("登录成功");
        finish();
    }

    LocalBroadcastManager broadcastManager;

    /**
     * 注册广播接收器
     */
    private void receiveLoginSuccess() {
        broadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(LOGIN_SUCCESS);
        broadcastManager.registerReceiver(mLoginSuccessReceiver, intentFilter);
    }

    /**
     * 接收到注册成功并登录广播并进行处理
     */
    BroadcastReceiver mLoginSuccessReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    };

}
