package com.hy.wanandroid.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;

import com.hy.wanandroid.R;
import com.hy.wanandroid.bean.QueryBean;
import com.hy.wanandroid.framework.presenter.CollectPresenter;
import com.hy.wanandroid.framework.presenter.QueryPresenter;
import com.hy.wanandroid.framework.presenter.UnCollectWithOriginIdPresenter;
import com.hy.wanandroid.framework.view.CollectView;
import com.hy.wanandroid.framework.view.QueryView;
import com.hy.wanandroid.framework.view.UnCollectWithOriginIdView;
import com.hy.wanandroid.ui.adapter.BaseAdapter;
import com.hy.wanandroid.ui.adapter.BaseViewHolder;
import com.hy.wanandroid.ui.toast.ToastUtils;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;

import java.util.LinkedList;
import java.util.List;

import static com.hy.wanandroid.constants.Constants.KEYWORD;
import static com.hy.wanandroid.constants.Constants.LINK;

/**
 * author: huyin
 * date: 2018/6/14
 * <p>
 * 搜索结果列表
 */
public class SearchListActivity extends BaseActivity implements
        QueryView , CollectView, UnCollectWithOriginIdView {

    Toolbar toolbar;
    RecyclerView searchListRecyclerView;
    RefreshLayout refreshLayout;
    List<QueryBean.DatasBean> datasList;
    BaseAdapter<QueryBean.DatasBean> queryAdapter;

    QueryPresenter queryPresenter;
    CollectPresenter collectPresenter;
    UnCollectWithOriginIdPresenter unCollectWithOriginIdPresenter;

    String keyword;
    int page = 0;
    int pageCount= 0 ;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_search_list);
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
        searchListRecyclerView = findViewById(R.id.searchListRecyclerView);
        searchListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        datasList = new LinkedList<>();
        queryAdapter = new BaseAdapter<QueryBean.DatasBean>(this,datasList,
                R.layout.item_search_list) {
            @Override
            protected void onBindViewHolder(BaseViewHolder holder, QueryBean.DatasBean item, int position) {
                holder.setText(R.id.authorName, item.getAuthor())
                        .setText(R.id.time, item.getNiceDate())
                        .setText(R.id.title, Html.fromHtml(item.getTitle()));
                LikeButton likeButton = holder.getView(R.id.collectBtn);
                if (item.isCollect()) {
                    likeButton.setLiked(true);
                } else {
                    likeButton.setLiked(false);
                }
                likeButton.setOnLikeListener(new OnLikeListener() {
                    @Override
                    public void liked(LikeButton likeButton) {
                        collectPresenter.postCollect(String.valueOf(item.getId()));
                    }

                    @Override
                    public void unLiked(LikeButton likeButton) {
                        unCollectWithOriginIdPresenter.postUnCollect(String.valueOf(item.getId()));
                    }
                });
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(SearchListActivity.this,
                                WebActivity.class);
                        intent.putExtra(LINK, item.getLink());
                        startActivity(intent);
                    }
                });
            }
        };
        searchListRecyclerView.setAdapter(queryAdapter);
        refreshLayout.setOnMultiPurposeListener(new SimpleMultiPurposeListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                page = 0;
                queryPresenter.postQuery(String.valueOf(page), keyword);
                queryAdapter.clear();
                refreshLayout.finishRefresh(2000);
            }

            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                if (page < pageCount) {
                    page++;
                    queryPresenter.postQuery(String.valueOf(page), keyword);
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
        keyword = getIntent().getStringExtra(KEYWORD);
        queryPresenter = new QueryPresenter(this);
        queryPresenter.postQuery(String.valueOf(page), keyword);
        collectPresenter = new CollectPresenter(this);
        unCollectWithOriginIdPresenter = new UnCollectWithOriginIdPresenter(this);
    }

    @Override
    public void setQuery(QueryBean query) {
        pageCount = query.getPageCount();
        for(QueryBean.DatasBean datas:query.getDatas()){
            queryAdapter.add(datas);
        }
    }



    @Override
    public void setCollect() {
        ToastUtils.toast("收藏成功");
    }

    @Override
    public void setUnCollect() {
        ToastUtils.toast("取消收藏成功");
    }
}
