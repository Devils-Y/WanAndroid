package com.hy.wanandroid.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;

import com.hy.wanandroid.R;
import com.hy.wanandroid.bean.CollectListBean;
import com.hy.wanandroid.framework.presenter.CollectListPresenter;
import com.hy.wanandroid.framework.presenter.UnCollectPresenter;
import com.hy.wanandroid.framework.view.CollectListView;
import com.hy.wanandroid.framework.view.UnCollectView;
import com.hy.wanandroid.ui.adapter.BaseAdapter;
import com.hy.wanandroid.ui.adapter.BaseViewHolder;
import com.hy.wanandroid.ui.toast.ToastUtils;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;

import java.util.LinkedList;
import java.util.List;

import static com.hy.wanandroid.constants.Constants.LINK;

/**
 * author: huyin
 * date: 2018/6/13
 */
public class CollectListActivity extends BaseActivity implements
        CollectListView, UnCollectView {

    Toolbar toolbar;
    RefreshLayout refreshLayout;
    RecyclerView collectListRecyclerView;
    CollectListPresenter collectListPresenter;
    List<CollectListBean.DatasBean> collectList;
    BaseAdapter<CollectListBean.DatasBean> collectListAdapter;
    UnCollectPresenter unCollectPresenter;
    int page = 0;
    int pageCount = 0;
    int finalPosition = 0;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_collect_list);
    }

    private void initTitle() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle(R.string.my_collection_txt);
        toolbar.setNavigationIcon(R.drawable.ic_back_left_white);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void viewInit() {
        initTitle();

        refreshLayout = findViewById(R.id.refreshLayout);
        collectListRecyclerView = findViewById(R.id.collectListRecyclerView);
        collectListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        collectList = new LinkedList();
        collectListAdapter = new BaseAdapter<CollectListBean.DatasBean>(this,
                collectList, R.layout.item_collect_list) {
            @Override
            protected void onBindViewHolder(BaseViewHolder holder, CollectListBean.DatasBean item, int position) {
                holder.setText(R.id.authorName, item.getAuthor())
                        .setText(R.id.belong2, item.getChapterName())
                        .setText(R.id.time, item.getNiceDate())
                        .setText(R.id.title, Html.fromHtml(item.getTitle()));
                LikeButton likeButton = holder.getView(R.id.collectBtn);
                likeButton.setOnLikeListener(new OnLikeListener() {
                    @Override
                    public void liked(LikeButton likeButton) {
                    }

                    @Override
                    public void unLiked(LikeButton likeButton) {
                        finalPosition = position;
                        unCollectPresenter.postUnCollect(String.valueOf(item.getId()),
                                String.valueOf(item.getOriginId()));
                    }
                });
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(CollectListActivity.this,
                                WebActivity.class);
                        intent.putExtra(LINK, item.getLink());
                        startActivity(intent);
                    }
                });
            }
        };
        collectListRecyclerView.setAdapter(collectListAdapter);
        refreshLayout.setOnMultiPurposeListener(new SimpleMultiPurposeListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                page = 0;
                collectListPresenter.getCollectList(String.valueOf(page));
                collectListAdapter.clear();
                refreshLayout.finishRefresh(2000);
            }

            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                if (page < pageCount) {
                    page++;
                    collectListPresenter.getCollectList(String.valueOf(page));
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
        collectListPresenter = new CollectListPresenter(this);
        collectListPresenter.getCollectList(String.valueOf(page));
        unCollectPresenter = new UnCollectPresenter(this);
    }

    @Override
    public void setCollectList(CollectListBean collectList) {
        pageCount = collectList.getPageCount();
        for (CollectListBean.DatasBean datas : collectList.getDatas()) {
            collectListAdapter.add(datas);
        }
    }

    @Override
    public void setUnCollect() {
        ToastUtils.toast("取消收藏成功");
        collectListAdapter.remove(finalPosition);
    }
}
