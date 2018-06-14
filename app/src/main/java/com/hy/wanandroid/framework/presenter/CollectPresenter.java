package com.hy.wanandroid.framework.presenter;

import com.hy.wanandroid.bean.CollectBean;
import com.hy.wanandroid.framework.view.CollectView;
import com.hy.wanandroid.net.BaseInterface;
import com.hy.wanandroid.net.BaseListener;
import com.hy.wanandroid.net.HttpNet;

import retrofit2.Response;

/**
 * author: huyin
 * date: 2018/6/13
 */
public class CollectPresenter implements BaseListener<Void> {

    CollectView collectView;

    public CollectPresenter(CollectView collectView) {
        this.collectView = collectView;
    }

    /**
     * 收藏站内文章
     *
     * @param id 为需要收藏的文章id.
     */
    public void postCollect(String id) {
        new BaseInterface().requestData(HttpNet.getInstantes().httpNet()
                .postCollect(id), this);
    }

    @Override
    public void onSuccess(Response<Void> t) {
    }

    @Override
    public void onSuccess() {
        collectView.setCollect();
    }

    @Override
    public void onError(String errorString) {

    }
}
