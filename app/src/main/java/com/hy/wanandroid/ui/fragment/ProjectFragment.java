package com.hy.wanandroid.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hy.wanandroid.R;
import com.hy.wanandroid.bean.ProjectListBean;
import com.hy.wanandroid.framework.presenter.ProjectListPresenter;
import com.hy.wanandroid.framework.view.ProjectListView;
import com.hy.wanandroid.ui.activity.ArticleActivity;
import com.hy.wanandroid.ui.adapter.BaseAdapter;
import com.hy.wanandroid.ui.adapter.BaseViewHolder;
import com.hy.wanandroid.ui.toast.ToastUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;

import java.util.LinkedList;
import java.util.List;

import static com.hy.wanandroid.constants.Constants.LINK;

/**
 * author: huyin
 * date: 2018/6/8
 */
public class ProjectFragment extends BaseFragment implements ProjectListView {

    RecyclerView projectRecyclerView;
    RefreshLayout refreshLayout;
    List<ProjectListBean.DatasBean> projectList;
    BaseAdapter<ProjectListBean.DatasBean> projectListAdapter;

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

        projectRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        projectRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));
        projectList = new LinkedList<>();
        projectListAdapter = new BaseAdapter<ProjectListBean.DatasBean>(getActivity(), projectList,
                R.layout.item_project_list) {
            @Override
            protected void onBindViewHolder(BaseViewHolder holder, ProjectListBean.DatasBean item,
                                            int position) {
                holder.setText(R.id.title, item.getTitle())
                        .setText(R.id.content, item.getDesc())
                        .setText(R.id.date, item.getNiceDate())
                        .setText(R.id.author, item.getAuthor());
                holder.setImageUrl(R.id.projectImg, new BaseViewHolder.HolderImage(
                        item.getEnvelopePic()) {
                    @Override
                    public void loadImg(ImageView imageView, String url) {
                        Glide.with(getActivity()).load(url).into(imageView);
                    }
                });
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), ArticleActivity.class);
                        intent.putExtra(LINK, item.getLink());
                        startActivity(intent);
                    }
                });
            }
        };
        projectRecyclerView.setAdapter(projectListAdapter);

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
}
