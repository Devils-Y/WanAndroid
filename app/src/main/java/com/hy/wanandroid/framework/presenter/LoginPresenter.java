package com.hy.wanandroid.framework.presenter;

import com.hy.wanandroid.bean.LoginBean;
import com.hy.wanandroid.framework.view.LoginView;
import com.hy.wanandroid.net.BaseInterface;
import com.hy.wanandroid.net.BaseListener;
import com.hy.wanandroid.net.HttpNet;

import retrofit2.Response;

/**
 * author: huyin
 * date: 2018/6/13
 */
public class LoginPresenter implements BaseListener<LoginBean> {

    LoginView loginView;

    public LoginPresenter(LoginView loginView) {
        this.loginView = loginView;
    }

    public void postLogin(String username, String password) {
        new BaseInterface().requestData(HttpNet.getInstantes().httpNet()
                .postLogin(username, password), this);
    }

    @Override
    public void onSuccess(Response<LoginBean> t) {
        if (t.body() != null) {
            loginView.setLogin(t.body());
        }
    }

    @Override
    public void onError(String errorString) {

    }
}
