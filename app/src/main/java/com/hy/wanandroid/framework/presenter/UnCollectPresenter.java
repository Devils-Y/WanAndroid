package com.hy.wanandroid.framework.presenter;

import com.hy.wanandroid.bean.UnCollectBean;
import com.hy.wanandroid.framework.view.UnCollectView;
import com.hy.wanandroid.net.BaseInterface;
import com.hy.wanandroid.net.BaseListener;
import com.hy.wanandroid.net.HttpNet;

import retrofit2.Response;

/**
 * author: huyin
 * date: 2018/6/14
 */
public class UnCollectPresenter implements BaseListener<UnCollectBean> {

    UnCollectView unCollectView;

    public UnCollectPresenter(UnCollectView unCollectView) {
        this.unCollectView = unCollectView;
    }

    public void postUnCollect(String id, String originId) {
        new BaseInterface().requestData(HttpNet.getInstantes().httpNet()
                .postUnCollect(id, originId), this);
    }

    @Override
    public void onSuccess(Response<UnCollectBean> t) {
    }

    @Override
    public void onSuccess() {
        unCollectView.setUnCollect();
    }

    @Override
    public void onError(String errorString) {

    }
}
