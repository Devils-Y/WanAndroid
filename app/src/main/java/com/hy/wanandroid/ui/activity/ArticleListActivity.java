package com.hy.wanandroid.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;

import com.hy.wanandroid.R;
import com.hy.wanandroid.bean.TreeArticleBean;
import com.hy.wanandroid.framework.presenter.TreeArticlePresenter;
import com.hy.wanandroid.framework.view.TreeArticleView;
import com.hy.wanandroid.ui.adapter.BaseAdapter;
import com.hy.wanandroid.ui.adapter.BaseViewHolder;
import com.hy.wanandroid.ui.toast.ToastUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;

import java.util.LinkedList;
import java.util.List;

import static com.hy.wanandroid.constants.Constants.CID;
import static com.hy.wanandroid.constants.Constants.LINK;
import static com.hy.wanandroid.constants.Constants.TREENAME;

/**
 * author: huyin
 * date: 2018/6/10
 * <p>
 * 体系下的文章
 */
public class ArticleListActivity extends BaseActivity implements TreeArticleView {

    Toolbar toolbar;
    RecyclerView articleListRecyclerView;
    RefreshLayout refreshLayout;
    TreeArticlePresenter treeArticlePresenter;

    List<TreeArticleBean.DatasBean> treeArticleList;
    BaseAdapter<TreeArticleBean.DatasBean> treeArticleAdapter;
    String cid;
    int page = 0;
    int pageCount = 0;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_article_list);
    }

    private void initTitle() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getIntent().getStringExtra(TREENAME));
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_back_left_white);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
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
        cid = getIntent().getStringExtra(CID);

        articleListRecyclerView = findViewById(R.id.articleListRecyclerView);
        refreshLayout = findViewById(R.id.refreshLayout);
        articleListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        treeArticleList = new LinkedList<>();
        treeArticleAdapter = new BaseAdapter<TreeArticleBean.DatasBean>(this,
                treeArticleList, R.layout.item_article_list) {
            @Override
            protected void onBindViewHolder(BaseViewHolder holder,
                                            TreeArticleBean.DatasBean item, int position) {
                holder.setText(R.id.authorName, item.getAuthor())
                        .setText(R.id.belong2, item.getSuperChapterName()
                                + "/" + item.getChapterName())
                        .setText(R.id.time, item.getNiceDate())
                        .setText(R.id.title, Html.fromHtml(item.getTitle()));
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ArticleListActivity.this, WebActivity.class);
                        intent.putExtra(LINK, item.getLink());
                        startActivity(intent);
                    }
                });
            }
        };
        articleListRecyclerView.setAdapter(treeArticleAdapter);

        refreshLayout.setOnMultiPurposeListener(new SimpleMultiPurposeListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                page = 0;
                treeArticlePresenter.getTreeArticle(String.valueOf(page), cid);
                treeArticleAdapter.clear();
                refreshLayout.finishRefresh(2000);
            }

            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                if (page < pageCount) {
                    page++;
                    treeArticlePresenter.getTreeArticle(String.valueOf(page), cid);
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
        treeArticlePresenter = new TreeArticlePresenter(this);
        treeArticlePresenter.getTreeArticle(String.valueOf(page), cid);
    }

    @Override
    public void setTreeArticle(TreeArticleBean treeArticle) {
        pageCount = treeArticle.getPageCount();
        for (int i = 0; i < treeArticle.getDatas().size(); i++) {
            treeArticleAdapter.add(treeArticle.getDatas().get(i));
        }
    }
}
