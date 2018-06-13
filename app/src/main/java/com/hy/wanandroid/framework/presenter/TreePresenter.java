package com.hy.wanandroid.framework.presenter;

import com.hy.wanandroid.bean.TreeBean;
import com.hy.wanandroid.framework.view.TreeView;
import com.hy.wanandroid.net.BaseInterface;
import com.hy.wanandroid.net.BaseListener;
import com.hy.wanandroid.net.HttpNet;

import java.util.List;

import retrofit2.Response;

/**
 * author: huyin
 * date: 2018/6/8
 */
public class TreePresenter implements BaseListener<List<TreeBean>> {

    TreeView treeView;

    public TreePresenter(TreeView treeView) {
        this.treeView = treeView;
    }

    public void getTree() {
        new BaseInterface().requestData(
                HttpNet.getInstantes().httpNet().getTree(), this);
    }

    @Override
    public void onSuccess(Response<List<TreeBean>> t) {
        if (t.body() != null) {
            treeView.setTree(t.body());
        }
    }

    @Override
    public void onError(String errorString) {

    }
}
