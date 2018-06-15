package com.hy.wanandroid.framework.presenter;

import com.hy.wanandroid.bean.NaviBean;
import com.hy.wanandroid.framework.view.NaviView;
import com.hy.wanandroid.net.BaseInterface;
import com.hy.wanandroid.net.BaseListener;
import com.hy.wanandroid.net.HttpNet;

import java.util.List;

import retrofit2.Response;

/**
 * author: huyin
 * date: 2018/6/15
 */
public class NaviPresenter implements BaseListener<List<NaviBean>> {

    NaviView naviView;

    public NaviPresenter(NaviView naviView) {
        this.naviView = naviView;
    }

    public void getNavi() {
        new BaseInterface().requestData(HttpNet.getInstantes().httpNet()
                .getNavi(), this);
    }

    @Override
    public void onSuccess(Response<List<NaviBean>> t) {
        if (t.body() != null) {
            naviView.setNavi(t.body());
        }
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onError(String errorString) {

    }
}
