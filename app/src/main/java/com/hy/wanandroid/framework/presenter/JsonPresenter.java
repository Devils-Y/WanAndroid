package com.hy.wanandroid.framework.presenter;

import com.hy.wanandroid.bean.JsonBean;
import com.hy.wanandroid.framework.view.JsonView;
import com.hy.wanandroid.net.BaseInterface;
import com.hy.wanandroid.net.BaseListener;
import com.hy.wanandroid.net.HttpNet;

import retrofit2.Response;

/**
 * author: huyin
 * date: 2018/6/7
 */
public class JsonPresenter implements BaseListener<JsonBean> {

    JsonView jsonView;

    public JsonPresenter(JsonView jsonView) {
        this.jsonView = jsonView;
    }

    public void getJson(String pageNum) {
        new BaseInterface().requestData(
                HttpNet.getInstantes().httpNet().getJson(pageNum), this);
    }

    @Override
    public void onSuccess(Response<JsonBean> t) {
        if (t.body() != null) {
            jsonView.setJson(t.body());
        }
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onError(String errorString) {

    }
}
