package com.hy.wanandroid.framework.presenter;

import com.hy.wanandroid.bean.ProjectBean;
import com.hy.wanandroid.framework.view.ProjectView;
import com.hy.wanandroid.net.BaseInterface;
import com.hy.wanandroid.net.BaseListener;
import com.hy.wanandroid.net.HttpNet;

import java.util.List;

import retrofit2.Response;

/**
 * author: huyin
 * date: 2018/6/8
 */
public class ProjectPresenter implements BaseListener<List<ProjectBean>> {

    ProjectView projectView;

    public ProjectPresenter(ProjectView projectView) {
        this.projectView = projectView;
    }

    public void getProject() {
        new BaseInterface().requestData(HttpNet.getInstantes().httpNet()
                .getProject(), this);
    }

    @Override
    public void onSuccess(Response<List<ProjectBean>> t) {
        if (t.body() != null) {
            projectView.setProject(t.body());
        }
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onError(String errorString) {

    }
}
