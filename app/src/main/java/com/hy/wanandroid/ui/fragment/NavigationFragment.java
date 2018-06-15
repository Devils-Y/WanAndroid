package com.hy.wanandroid.ui.fragment;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.hy.wanandroid.R;
import com.hy.wanandroid.bean.NaviBean;
import com.hy.wanandroid.framework.presenter.NaviPresenter;
import com.hy.wanandroid.framework.view.NaviView;
import com.hy.wanandroid.ui.activity.ArticleListActivity;
import com.hy.wanandroid.ui.activity.WebActivity;
import com.hy.wanandroid.ui.adapter.BaseAdapter;
import com.hy.wanandroid.ui.adapter.BaseViewHolder;
import com.hy.wanandroid.ui.adapter.NaviAdapter;

import java.util.LinkedList;
import java.util.List;

import static com.hy.wanandroid.constants.Constants.LINK;

/**
 * Created by huyin on 2018/4/24.
 * <p>
 * 网页快速导航
 */

public class NavigationFragment extends BaseFragment implements
        NaviView ,NaviAdapter.OnArticlesClickListener {

    Toolbar toolbar;
    RecyclerView naviRecyclerView;
    List<NaviBean> naviList;
    NaviAdapter naviAdapter;

    NaviPresenter naviPresenter;

    @Override
    public int setContentLyaoutId() {
        return R.layout.fragment_navigation;
    }

    @Override
    public void viewInit() {
        toolbar = (Toolbar) getContentView().findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.navigation_txt);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        naviRecyclerView = getContentView().findViewById(R.id.naviRecyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        naviRecyclerView.setLayoutManager(manager);
        // 将PagerSnapHelper attach 到 naviRecyclerView
        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(naviRecyclerView);

        naviList = new LinkedList<>();
        naviAdapter = new NaviAdapter(getActivity(), naviList);
        naviRecyclerView.setAdapter(naviAdapter);
        naviAdapter.setOnArticlesClickListener(this);
    }

    @Override
    public void dataInit() {
        naviPresenter = new NaviPresenter(this);
        naviPresenter.getNavi();
    }

    @Override
    public void viewDestroy() {

    }

    @Override
    public void setNavi(List<NaviBean> navi) {
        for (NaviBean naviBean : navi) {
            naviAdapter.add(naviBean);
        }
    }

    @Override
    public void onArticlesClick(String link) {
        Intent intent = new Intent(getActivity(), WebActivity.class);
        intent.putExtra(LINK, link);
        startActivity(intent);
    }
}
