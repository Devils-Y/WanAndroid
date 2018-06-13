package com.hy.wanandroid.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hy.wanandroid.R;
import com.hy.wanandroid.bean.ProjectListBean;
import com.hy.wanandroid.data.SharedPreferenceUtils;
import com.hy.wanandroid.framework.presenter.ProjectListPresenter;
import com.hy.wanandroid.framework.view.ProjectListView;
import com.hy.wanandroid.ui.activity.ArticleActivity;
import com.hy.wanandroid.ui.adapter.BaseAdapter;
import com.hy.wanandroid.ui.adapter.BaseViewHolder;
import com.hy.wanandroid.ui.adapter.ProjectAdapter;
import com.hy.wanandroid.ui.toast.ToastUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;

import java.util.LinkedList;
import java.util.List;

import static com.hy.wanandroid.constants.Constants.LINK;
import static com.hy.wanandroid.constants.Constants.PROJECTSTYLE;
import static com.hy.wanandroid.constants.Constants.STYLETYPE;

/**
 * author: huyin
 * date: 2018/6/8
 */
public class ProjectFragment extends BaseFragment implements
        ProjectListView, ProjectAdapter.OnItemClickListener {

    RecyclerView projectRecyclerView;
    RefreshLayout refreshLayout;
    List<ProjectListBean.DatasBean> projectList;
    ProjectAdapter projectListAdapter;

    ProjectListPresenter projectListPresenter;

    private static final String CID = "cid";

    int page = 1;
    int pageCount = 0;
    private String cid;

    public static ProjectFragment arguments(int cid) {
        ProjectFragment projectFragment = new ProjectFragment();
        Bundle bundle = new Bundle();
        bundle.putString(CID, String.valueOf(cid));
        projectFragment.setArguments(bundle);
        return projectFragment;
    }

    @Override
    public int setContentLyaoutId() {
        return R.layout.fragment_project;
    }

    @Override
    public void viewInit() {
        refreshLayout = getContentView().findViewById(R.id.refreshLayout);
        projectRecyclerView = getContentView().findViewById(R.id.projectRecyclerView);

        projectList = new LinkedList<>();
        projectListAdapter = new ProjectAdapter(getActivity(), projectList);
        //读取用户喜好样式
        if (SharedPreferenceUtils.ReadProjectType() == ProjectAdapter.PICTURE_MODE) {
            projectRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        } else {
            projectRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
        //设置布局样式
        projectListAdapter.setType(SharedPreferenceUtils.ReadProjectType());
        projectRecyclerView.setAdapter(projectListAdapter);
        projectListAdapter.setOnItemClickListener(this);

        refreshLayout.setOnMultiPurposeListener(new SimpleMultiPurposeListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                page = 0;
                projectListPresenter.getProjectList(String.valueOf(page), cid);
                projectListAdapter.clear();
                refreshLayout.finishRefresh(2000);
            }

            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                if (page < pageCount) {
                    page++;
                    projectListPresenter.getProjectList(String.valueOf(page), cid);
                    refreshLayout.finishLoadMore(2000);
                } else {
                    ToastUtils.toast("已无更多数据");
                    refreshLayout.finishLoadMore(100);
                }
            }
        });

        receiveProjectStyle();
    }

    @Override
    public void dataInit() {
        cid = getArguments().getString(CID);

        projectListPresenter = new ProjectListPresenter(this);
        projectListPresenter.getProjectList(String.valueOf(page), cid);
    }

    @Override
    public void viewDestroy() {

    }

    @Override
    public void setProjectList(ProjectListBean projectList) {
        pageCount = projectList.getPageCount();
        for (ProjectListBean.DatasBean datas : projectList.getDatas()) {
            projectListAdapter.add(datas);
        }
    }

    @Override
    public void onItemClick(String link) {
        Intent intent = new Intent(getActivity(), ArticleActivity.class);
        intent.putExtra(LINK, link);
        startActivity(intent);
    }

    LocalBroadcastManager broadcastManager;

    /**
     * 注册广播接收器
     */
    private void receiveProjectStyle() {
        broadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(PROJECTSTYLE);
        broadcastManager.registerReceiver(mClearInfoSuccessReceiver, intentFilter);
    }

    /**
     * 接收到模式修改成功广播并进行处理
     */
    BroadcastReceiver mClearInfoSuccessReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int styleType = intent.getIntExtra(STYLETYPE, 0);
            if (styleType == ProjectAdapter.ALL_MODE) {
                /**
                 * 设置为完整模式
                 */
                //1：设置布局类型
                projectListAdapter.setType(ProjectAdapter.ALL_MODE);
                //2：设置对应的布局管理器
                projectRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            } else if (styleType == ProjectAdapter.WORDS_MODE) {
                /**
                 * 设置为文字模式
                 */
                //1：设置布局类型
                projectListAdapter.setType(ProjectAdapter.WORDS_MODE);
                //2：设置对应的布局管理器
                projectRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            } else {
                /**
                 * 设置为效果图模式
                 */
                //1：设置布局类型
                projectListAdapter.setType(ProjectAdapter.PICTURE_MODE);
                //2：设置对应的布局管理器
                projectRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            }
            //3：刷新adapter
            projectListAdapter.notifyDataSetChanged();
        }
    };
}
