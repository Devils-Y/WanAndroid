package com.hy.wanandroid.ui.fragment;

import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.hy.wanandroid.R;
import com.hy.wanandroid.data.SharedPreferenceUtils;
import com.hy.wanandroid.ui.activity.ArticleListActivity;
import com.hy.wanandroid.ui.adapter.BaseAdapter;
import com.hy.wanandroid.ui.adapter.BaseViewHolder;
import com.hy.wanandroid.bean.TreeBean;
import com.hy.wanandroid.framework.presenter.TreePresenter;
import com.hy.wanandroid.framework.view.TreeView;

import java.util.LinkedList;
import java.util.List;

import static com.hy.wanandroid.constants.Constants.CID;
import static com.hy.wanandroid.constants.Constants.TREENAME;

/**
 * Created by huyin on 2018/4/24.
 * <p>
 * 体系
 */

public class TreeFragment extends BaseFragment implements View.OnClickListener, TreeView {

    Toolbar toolbar;
    ImageView changListStyle;
    RecyclerView treeRecyclerView;
    RecyclerView detailRecyclerView;
    LinearLayout twoRecyclerViewLayout;
    RecyclerView treeListRecyclerView;
    List<TreeBean> treeList;
    BaseAdapter<TreeBean> treeAdapter;
    List<TreeBean.ChildrenBean> childrenList;
    BaseAdapter<TreeBean.ChildrenBean> childrenAdapter;

    List<TreeBean> treeListList;
    BaseAdapter<TreeBean> treeListAdapter;
    List<TreeBean.ChildrenBean> childrenBeanList;
    BaseAdapter<TreeBean.ChildrenBean> childrenBeanBaseAdapter;

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

        changListStyle = getContentView().findViewById(R.id.changListStyle);
        changListStyle.setOnClickListener(this);
        treeRecyclerView = getContentView().findViewById(R.id.treeRecyclerView);
        detailRecyclerView = getContentView().findViewById(R.id.detailRecyclerView);

        twoRecyclerViewLayout = getContentView().findViewById(R.id.twoRecyclerViewLayout);
        treeListRecyclerView = getContentView().findViewById(R.id.treeListRecyclerView);


        /**
         * 列表
         * 模式1
         */
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
                        Intent intent = new Intent(getActivity(), ArticleListActivity.class);
                        intent.putExtra(CID, String.valueOf(item.getId()));
                        intent.putExtra(TREENAME, item.getName());
                        startActivity(intent);
                    }
                });
            }
        };
        detailRecyclerView.setAdapter(childrenAdapter);

        /**
         * 列表
         * 模式2
         */
        treeListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        treeListList = new LinkedList<>();
        childrenBeanList = new LinkedList<>();

        treeListAdapter = new BaseAdapter<TreeBean>(getActivity(), treeListList,
                R.layout.item_tree_list) {

            FlexboxLayoutManager layoutManager = null;

            @Override
            protected void onBindViewHolder(BaseViewHolder holder,
                                            TreeBean item, int position) {
                holder.setText(R.id.title, item.getName());
                RecyclerView flexboxRecyclerView = holder.getView(R.id.flexboxRecyclerView);
                flexboxRecyclerView.setNestedScrollingEnabled(false);
                layoutManager = new FlexboxLayoutManager(getActivity());
                layoutManager.setFlexDirection(FlexDirection.ROW);
                layoutManager.setJustifyContent(JustifyContent.FLEX_START);
                flexboxRecyclerView.setLayoutManager(layoutManager);
                childrenBeanBaseAdapter = new BaseAdapter<TreeBean.ChildrenBean>(getActivity(),
                        childrenBeanList, R.layout.item_tree_children_flox) {
                    @Override
                    protected void onBindViewHolder(BaseViewHolder holder, TreeBean.ChildrenBean item, int position) {
                        holder.setText(R.id.childrenTv, item.getName());
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getActivity(), ArticleListActivity.class);
                                intent.putExtra(CID, String.valueOf(item.getId()));
                                intent.putExtra(TREENAME, item.getName());
                                startActivity(intent);
                            }
                        });
                    }
                };
                flexboxRecyclerView.setAdapter(childrenBeanBaseAdapter);
                childrenBeanBaseAdapter.clear();
                for (TreeBean.ChildrenBean children : item.getChildren()) {
                    childrenBeanBaseAdapter.add(children);
                }
            }
        };
        treeListRecyclerView.setAdapter(treeListAdapter);
        refreshData();

    }

    private void refreshData() {

        if (!SharedPreferenceUtils.ReadTree()) {
            twoRecyclerViewLayout.setVisibility(View.VISIBLE);
            treeListRecyclerView.setVisibility(View.GONE);
        } else {
            twoRecyclerViewLayout.setVisibility(View.GONE);
            treeListRecyclerView.setVisibility(View.VISIBLE);
        }
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
        if (!SharedPreferenceUtils.ReadTree()) {
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
        } else {
            for (TreeBean tree : treeList) {
                treeListAdapter.add(tree);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.changListStyle:

                if (childrenAdapter.size() != 0) {
                    /**
                     * id 赋值为 0的意义是先显示左侧列表
                     */
                    id = 0;
                    childrenAdapter.clear();
                }
                if (treeListAdapter.size() != 0) {
                    treeListAdapter.clear();
                }
                if (treeAdapter.size() != 0) {
                    treeAdapter.clear();
                }

                if (!SharedPreferenceUtils.ReadTree()) {
                    SharedPreferenceUtils.WriteTree(true);
                } else {
                    SharedPreferenceUtils.WriteTree(false);
                }
                treePresenter.getTree();
                refreshData();
                break;
        }
    }
}
