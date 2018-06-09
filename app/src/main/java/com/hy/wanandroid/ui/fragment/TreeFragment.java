package com.hy.wanandroid.ui.fragment;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.hy.wanandroid.R;
import com.hy.wanandroid.ui.adapter.BaseAdapter;
import com.hy.wanandroid.ui.adapter.BaseViewHolder;
import com.hy.wanandroid.bean.TreeBean;
import com.hy.wanandroid.framework.presenter.TreePresenter;
import com.hy.wanandroid.framework.view.TreeView;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by huyin on 2018/4/24.
 * <p>
 * 体系
 */

public class TreeFragment extends BaseFragment implements TreeView {

    Toolbar toolbar;
    RecyclerView treeRecyclerView;
    RecyclerView detailRecyclerView;
    List<TreeBean> treeList;
    List<TreeBean.ChildrenBean> childrenList;
    BaseAdapter<TreeBean> treeAdapter;
    BaseAdapter<TreeBean.ChildrenBean> childrenAdapter;

    TreePresenter treePresenter;
    //detail对应的ID
    int id = 0;

    @Override
    public int setContentLyaoutId() {
        return R.layout.fragment_tree;
    }

    @Override
    public void viewInit() {
        toolbar = (Toolbar) getContentView().findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.tree_txt);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        treeRecyclerView = getContentView().findViewById(R.id.treeRecyclerView);
        detailRecyclerView = getContentView().findViewById(R.id.detailRecyclerView);

        //体系
        treeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        treeRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));
        treeList = new LinkedList<>();
        treeAdapter = new BaseAdapter<TreeBean>(getActivity(), treeList, R.layout.item_tree_text) {
            @Override
            protected void onBindViewHolder(BaseViewHolder holder, TreeBean item, int position) {
                holder.setText(R.id.treeTv, item.getName());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        id = item.getId();
                        childrenAdapter.clear();
                        treePresenter.getTree();
                    }
                });
            }
        };
        treeRecyclerView.setAdapter(treeAdapter);

        //detail
        detailRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        childrenList = new LinkedList<>();
        childrenAdapter = new BaseAdapter<TreeBean.ChildrenBean>(getActivity(), childrenList,
                R.layout.item_tree_children) {
            @Override
            protected void onBindViewHolder(BaseViewHolder holder,
                                            TreeBean.ChildrenBean item, int position) {
                holder.setText(R.id.name, item.getName());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        
                    }
                });
            }
        };
        detailRecyclerView.setAdapter(childrenAdapter);
    }

    @Override
    public void dataInit() {
        treePresenter = new TreePresenter(this);
        treePresenter.getTree();
    }

    @Override
    public void viewDestroy() {

    }

    @Override
    public void setTree(List<TreeBean> treeList) {
        for (TreeBean tree : treeList) {
            if (id == 0) {
                treeAdapter.add(tree);
            } else {
                if (id == tree.getId()) {
                    for (TreeBean.ChildrenBean children : tree.getChildren()) {
                        childrenAdapter.add(children);
                    }
                }
            }
        }
    }
}
