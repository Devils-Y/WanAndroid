package com.hy.wanandroid.framework.presenter;

import com.hy.wanandroid.bean.TreeArticleBean;
import com.hy.wanandroid.framework.view.TreeArticleView;
import com.hy.wanandroid.net.BaseInterface;
import com.hy.wanandroid.net.BaseListener;
import com.hy.wanandroid.net.HttpNet;

import retrofit2.Response;

/**
 * author: huyin
 * date: 2018/6/10
 */
public class TreeArticlePresenter implements BaseListener<TreeArticleBean> {

    TreeArticleView treeArticleView;

    public TreeArticlePresenter(TreeArticleView treeArticleView) {
        this.treeArticleView = treeArticleView;
    }

    public void getTreeArticle(String page, String cid) {
        new BaseInterface().requestData(HttpNet.getInstantes().httpNet()
                .getTreeArticle(page, cid), this);
    }

    @Override
    public void onSuccess(Response<TreeArticleBean> t) {
        if (t.body() != null) {
            treeArticleView.setTreeArticle(t.body());
        }
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onError(String errorString) {

    }
}
