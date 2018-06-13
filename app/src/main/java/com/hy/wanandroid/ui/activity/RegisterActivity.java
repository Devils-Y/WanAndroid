package com.hy.wanandroid.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.hy.wanandroid.R;
import com.hy.wanandroid.bean.RegisterBean;
import com.hy.wanandroid.data.SharedPreferenceUtils;
import com.hy.wanandroid.framework.presenter.RegisterPresenter;
import com.hy.wanandroid.framework.view.RegisterView;
import com.hy.wanandroid.ui.toast.ToastUtils;

import static com.hy.wanandroid.constants.Constants.LOGIN_SUCCESS;

/**
 * author: huyin
 * date: 2018/6/12
 */
public class RegisterActivity extends BaseActivity implements
        RegisterView, View.OnClickListener {

    Toolbar toolbar;
    RegisterPresenter registerPresenter;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_register);
    }

    private void initTitle() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.register_txt);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_back_left_white);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        findViewById(R.id.loginBtn).setOnClickListener(this);
    }

    private EditText getUsernameEdit() {
        return (EditText) findViewById(R.id.usernameEdit);
    }

    private EditText getPasswordEdit() {
        return (EditText) findViewById(R.id.passwordEdit);
    }

    private EditText getRepasswordEdit() {
        return (EditText) findViewById(R.id.repasswordEdit);
    }

    @Override
    public void viewInit() {
        initTitle();
    }

    @Override
    public void dataInit() {
        registerPresenter = new RegisterPresenter(this);
    }

    @Override
    public void setRegister(RegisterBean register) {
        SharedPreferenceUtils.WriteUsername(register.getUsername());
        ToastUtils.toast("登录成功!");
        Intent intent = new Intent(LOGIN_SUCCESS);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.loginBtn:
                String username = getUsernameEdit().getText().toString();
                String password = getPasswordEdit().getText().toString();
                String repassword = getRepasswordEdit().getText().toString();
                if (!TextUtils.isEmpty(username)
                        && !TextUtils.isEmpty(password)
                        && !TextUtils.isEmpty(repassword)
                        && password.equals(repassword)) {
                    registerPresenter.postRegister(username, password, repassword);
                } else {
                    if (TextUtils.isEmpty(username)) {
                        ToastUtils.toast("请输入用户名");
                    } else if (TextUtils.isEmpty(password)) {
                        ToastUtils.toast("请输入密码");
                    } else if (TextUtils.isEmpty(repassword)) {
                        ToastUtils.toast("请输入确认密码");
                    } else if (!password.equals(repassword)) {
                        ToastUtils.toast("两次输入的密码不一致");
                    }
                }
                break;
        }
    }
}
