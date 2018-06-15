package com.hy.wanandroid.ui.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.hy.wanandroid.R;
import com.hy.wanandroid.bean.NaviBean;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * author: huyin
 * date: 2018/6/15
 */
public class NaviAdapter extends BaseAdapter<NaviBean> {

    FlexboxLayoutManager layoutManager = null;
    List<NaviBean.ArticlesBean> articlesList;
    BaseAdapter<NaviBean.ArticlesBean> articlesAdapter;

    public NaviAdapter(Context context, List<NaviBean> data) {
        super(context, data, R.layout.item_navi);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(mLayoutId, parent, false);
        toChange(view, parent);
        return new BaseViewHolder(view);
    }

    void toChange(View view, ViewGroup parent) {
        Resources resources = mContext.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        int width = dm.widthPixels;

        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
        layoutParams.width = width / 4 * 3;
        view.setLayoutParams(layoutParams);
    }

    @Override
    protected void onBindViewHolder(BaseViewHolder holder, NaviBean item, int position) {
        holder.setText(R.id.titleTv, item.getName());
        RecyclerView articlesRecyclerView = holder.getView(R.id.articlesRecyclerView);
        articlesRecyclerView.setNestedScrollingEnabled(false);
        layoutManager = new FlexboxLayoutManager(mContext);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        articlesRecyclerView.setLayoutManager(layoutManager);
        articlesList = new ArrayList<>();
        articlesAdapter = new BaseAdapter<NaviBean.ArticlesBean>(mContext,
                articlesList, R.layout.item_navi_article) {
            @Override
            protected void onBindViewHolder(BaseViewHolder holder,
                                            NaviBean.ArticlesBean item, int position) {
                holder.setText(R.id.naviArticleTv, item.getTitle());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onArticlesClickListener != null) {
                            onArticlesClickListener.onArticlesClick(item.getLink());
                        }
                    }
                });
            }
        };
        articlesRecyclerView.setAdapter(articlesAdapter);
        for (NaviBean.ArticlesBean articles : item.getArticles()) {
            articlesAdapter.add(articles);
        }
    }

    public interface OnArticlesClickListener {
        void onArticlesClick(String link);
    }

    OnArticlesClickListener onArticlesClickListener;

    public void setOnArticlesClickListener(OnArticlesClickListener onArticlesClickListener) {
        this.onArticlesClickListener = onArticlesClickListener;
    }
}
