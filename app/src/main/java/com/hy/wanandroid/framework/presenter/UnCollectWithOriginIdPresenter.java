package com.hy.wanandroid.framework.presenter;

import com.hy.wanandroid.bean.UnCollectWithOriginIdBean;
import com.hy.wanandroid.framework.view.UnCollectWithOriginIdView;
import com.hy.wanandroid.net.BaseInterface;
import com.hy.wanandroid.net.BaseListener;
import com.hy.wanandroid.net.HttpNet;

import retrofit2.Response;

/**
 * author: huyin
 * date: 2018/6/13
 */
public class UnCollectWithOriginIdPresenter implements BaseListener<UnCollectWithOriginIdBean>{

    UnCollectWithOriginIdView unCollectWithOriginIdView;

    public UnCollectWithOriginIdPresenter(UnCollectWithOriginIdView unCollectWithOriginIdView) {
        this.unCollectWithOriginIdView = unCollectWithOriginIdView;
    }

    /**
     *
     * @param id
     */
    public void postUnCollect(String id){
        new BaseInterface().requestData(HttpNet.getInstantes().httpNet()
                .postUnCollectWithOriginId(id),this);
    }

    @Override
    public void onSuccess(Response<UnCollectWithOriginIdBean> t) {

    }

    @Override
    public void onSuccess() {
        unCollectWithOriginIdView.setUnCollect();
    }

    @Override
    public void onError(String errorString) {

    }
}
