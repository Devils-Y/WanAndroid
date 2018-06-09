package com.hy.wanandroid.framework.presenter;

import com.hy.wanandroid.bean.ProjectListBean;
import com.hy.wanandroid.framework.view.ProjectListView;
import com.hy.wanandroid.net.BaseInterface;
import com.hy.wanandroid.net.BaseListener;
import com.hy.wanandroid.net.HttpNet;

/**
 * author: huyin
 * date: 2018/6/8
 */
public class ProjectListPresenter implements BaseListener<ProjectListBean> {

    ProjectListView projectListView;

    public ProjectListPresenter(ProjectListView projectListView) {
        this.projectListView = projectListView;
    }

    public void getProjectList(String page, String cid) {
        new BaseInterface().requestData(HttpNet.getInstantes().httpNet()
                .getProjectList(page, cid), this);
    }

    @Override
    public void onSuccess(ProjectListBean projectListBean) {
        projectListView.setProjectList(projectListBean);
    }

    @Override
    public void onFailed(Throwable t) {

    }

    @Override
    public void onError(String errorString) {

    }
}
