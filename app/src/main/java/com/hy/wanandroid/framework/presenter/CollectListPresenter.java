package com.hy.wanandroid.framework.presenter;

import android.util.Log;

import com.hy.wanandroid.bean.CollectListBean;
import com.hy.wanandroid.framework.view.CollectListView;
import com.hy.wanandroid.net.BaseInterface;
import com.hy.wanandroid.net.BaseListener;
import com.hy.wanandroid.net.HttpNet;
import com.hy.wanandroid.rx.RxBus;

import io.reactivex.functions.Consumer;
import retrofit2.Response;

/**
 * author: huyin
 * date: 2018/6/13
 */
public class CollectListPresenter implements BaseListener<CollectListBean> {

    CollectListView collectListView;

    public CollectListPresenter(CollectListView collectListView) {
        this.collectListView = collectListView;
    }

    public void getCollectList(String page) {
        new BaseInterface().requestData(HttpNet.getInstantes().httpNet()
                .getCollectList(page), this);
    }

    @Override
    public void onSuccess(Response<CollectListBean> t) {
        if (t.body() != null) {
            collectListView.setCollectList(t.body());
        }
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onError(String errorString) {

    }
}
