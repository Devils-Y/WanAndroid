package com.hy.wanandroid.framework.presenter;

import com.hy.wanandroid.bean.QueryBean;
import com.hy.wanandroid.framework.view.QueryView;
import com.hy.wanandroid.net.BaseInterface;
import com.hy.wanandroid.net.BaseListener;
import com.hy.wanandroid.net.HttpNet;

import retrofit2.Response;

/**
 * author: huyin
 * date: 2018/6/14
 */
public class QueryPresenter implements BaseListener<QueryBean> {

    QueryView queryView;

    public QueryPresenter(QueryView queryView) {
        this.queryView = queryView;
    }

    public void postQuery(String page, String keyword) {
        new BaseInterface().requestData(HttpNet.getInstantes().httpNet()
                .postQuery(page, keyword), this);
    }

    @Override
    public void onSuccess(Response<QueryBean> t) {
        if(t.body()!=null){
            queryView.setQuery(t.body());
        }
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onError(String errorString) {

    }
}
