package com.hy.wanandroid.ui.fragment;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hy.wanandroid.R;
import com.hy.wanandroid.bean.ProjectListBean;
import com.hy.wanandroid.framework.presenter.ProjectListPresenter;
import com.hy.wanandroid.framework.view.ProjectListView;
import com.hy.wanandroid.ui.adapter.BaseAdapter;
import com.hy.wanandroid.ui.adapter.BaseViewHolder;

import java.util.LinkedList;
import java.util.List;

/**
 * author: huyin
 * date: 2018/6/8
 */
public class ProjectFragment extends BaseFragment implements ProjectListView {

    RecyclerView projectRecyclerView;
    List<ProjectListBean.DatasBean> projectList;
    BaseAdapter<ProjectListBean.DatasBean> projectListAdapter;

    ProjectListPresenter projectListPresenter;

    @Override
    public int setContentLyaoutId() {
        return R.layout.fragment_project;
    }

    @Override
    public void viewInit() {
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
            }
        };
        projectRecyclerView.setAdapter(projectListAdapter);
    }

    @Override
    public void dataInit() {
        projectListPresenter = new ProjectListPresenter(this);
        projectListPresenter.getProjectList("1", "294");
    }

    @Override
    public void viewDestroy() {

    }

    @Override
    public void setProjectList(ProjectListBean projectList) {
        for (ProjectListBean.DatasBean datas : projectList.getDatas()) {
            projectListAdapter.add(datas);
        }
    }
}
