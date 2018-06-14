package com.hy.wanandroid.framework.presenter;

import com.hy.wanandroid.bean.RegisterBean;
import com.hy.wanandroid.framework.view.RegisterView;
import com.hy.wanandroid.net.BaseInterface;
import com.hy.wanandroid.net.BaseListener;
import com.hy.wanandroid.net.HttpNet;

import retrofit2.Response;

/**
 * author: huyin
 * date: 2018/6/13
 */
public class RegisterPresenter implements BaseListener<RegisterBean> {

    RegisterView registerView;

    public RegisterPresenter(RegisterView registerView) {
        this.registerView = registerView;
    }

    public void postRegister(String username, String password, String repassword) {
        new BaseInterface().requestData(HttpNet.getInstantes().httpNet()
                .postRegister(username, password, repassword), this);
    }

    @Override
    public void onSuccess(Response<RegisterBean> t) {
        if (t.body() != null) {
            registerView.setRegister(t.body());
        }
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onError(String errorString) {

    }
}
