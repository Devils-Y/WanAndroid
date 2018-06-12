package com.hy.wanandroid.ui.fragment;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.hy.wanandroid.R;
import com.hy.wanandroid.bean.BannerBean;
import com.hy.wanandroid.framework.presenter.BannerPresenter;
import com.hy.wanandroid.framework.view.BannerView;
import com.hy.wanandroid.ui.activity.ArticleActivity;
import com.hy.wanandroid.ui.activity.MainActivity;
import com.hy.wanandroid.ui.activity.SearchActivity;
import com.hy.wanandroid.ui.adapter.BaseAdapter;
import com.hy.wanandroid.ui.adapter.BaseViewHolder;
import com.hy.wanandroid.bean.FriendBean;
import com.hy.wanandroid.bean.JsonBean;
import com.hy.wanandroid.framework.presenter.FriendPresenter;
import com.hy.wanandroid.framework.presenter.JsonPresenter;
import com.hy.wanandroid.framework.view.FriendView;
import com.hy.wanandroid.framework.view.JsonView;
import com.hy.wanandroid.ui.toast.ToastUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;
import com.scwang.smartrefresh.layout.util.DensityUtil;

import java.util.LinkedList;
import java.util.List;

import static com.hy.wanandroid.constants.Constants.LINK;

/**
 * Created by huyin on 2018/4/23.
 * <p>
 * 首页
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener,
        JsonView, FriendView, BannerView {

    private int mScrollY = 0;

    ImageView searchView;
    Toolbar toolbar;
    NestedScrollView scrollView;
    RefreshLayout refreshLayout;
    RecyclerView friendRecyclerView;
    RecyclerView artRecyclerView;

    List<FriendBean> friendList;
    BaseAdapter<FriendBean> friendAdapter;

    List<JsonBean.DatasBean> jsonList;
    BaseAdapter<JsonBean.DatasBean> jsonAdapter;

    FriendPresenter friendPresenter;
    JsonPresenter jsonPresenter;
    BannerPresenter bannerPresenter;
    int page = 0;
    int pageCount = 0;

    @Override
    public int setContentLyaoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void viewInit() {
        toolbar = getContentView().findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.home_txt);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        searchView = getContentView().findViewById(R.id.searchView);
        scrollView = getContentView().findViewById(R.id.scrollView);
        refreshLayout = getContentView().findViewById(R.id.refreshLayout);
        friendRecyclerView = getContentView().findViewById(R.id.friendRecyclerView);
        artRecyclerView = getContentView().findViewById(R.id.artRecyclerView);
        searchView.setOnClickListener(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        friendRecyclerView.setLayoutManager(linearLayoutManager);
        friendList = new LinkedList<>();
        friendAdapter = new BaseAdapter<FriendBean>(getActivity(), friendList, R.layout.item_friend_web) {
            @Override
            protected void onBindViewHolder(BaseViewHolder holder, FriendBean item, int position) {
                holder.setText(R.id.name, item.getName());
            }
        };
        friendRecyclerView.setAdapter(friendAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setSmoothScrollbarEnabled(true);
        layoutManager.setAutoMeasureEnabled(true);
        artRecyclerView.setLayoutManager(layoutManager);
        //解决RecyclerView滑动不流畅
        artRecyclerView.setHasFixedSize(true);
        artRecyclerView.setNestedScrollingEnabled(false);

        artRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));
        jsonList = new LinkedList<>();
        jsonAdapter = new BaseAdapter<JsonBean.DatasBean>(getActivity(), jsonList, R.layout.item_home_art) {
            @Override
            protected void onBindViewHolder(BaseViewHolder holder, JsonBean.DatasBean item, int position) {
                holder.setText(R.id.authorName, item.getAuthor())
                        .setText(R.id.belong2, item.getSuperChapterName()
                                + "/" + item.getChapterName())
                        .setText(R.id.time, item.getNiceDate())
                        .setText(R.id.title, Html.fromHtml(item.getTitle()));
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
        artRecyclerView.setAdapter(jsonAdapter);

        refreshLayout.setOnMultiPurposeListener(new SimpleMultiPurposeListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                page = 0;
                jsonPresenter.getJson(String.valueOf(page));
                jsonAdapter.clear();
                refreshLayout.finishRefresh(2000);
            }

            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                if (page < pageCount) {
                    page++;
                    jsonPresenter.getJson(String.valueOf(page));
                    refreshLayout.finishLoadMore(2000);
                } else {
                    ToastUtils.toast("已无更多数据");
                    refreshLayout.finishLoadMore(100);
                }
            }
        });
        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            private int lastScrollY = 0;
            private int h = DensityUtil.dp2px(160);
            private int color = ContextCompat.getColor(getActivity(), R.color.cerulean) & 0x00ffffff;

            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (lastScrollY < h) {
                    scrollY = Math.min(h, scrollY);
                    mScrollY = scrollY > h ? h : scrollY;
                    toolbar.setAlpha(1f * mScrollY / h);
                    toolbar.setBackgroundColor(((255 * mScrollY / h) << 24) | color);
                }
                lastScrollY = scrollY;
            }
        });
        toolbar.setAlpha(0);
        toolbar.setBackgroundColor(0);
    }

    @Override
    public void dataInit() {
        friendPresenter = new FriendPresenter(this);
        friendPresenter.getFriend();
        jsonPresenter = new JsonPresenter(this);
        jsonPresenter.getJson(String.valueOf(page));
        bannerPresenter = new BannerPresenter(this);
        bannerPresenter.getBanner();
    }

    @Override
    public void viewDestroy() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.searchView:
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void setJson(JsonBean json) {
        pageCount = json.getPageCount();
        for (JsonBean.DatasBean allJson : json.getDatas()) {
            jsonAdapter.add(allJson);
        }
    }

    @Override
    public void setFriend(List<FriendBean> friendList) {
        for (FriendBean friend : friendList) {
            friendAdapter.add(friend);
        }
    }

    @Override
    public void setBanner(List<BannerBean> banner) {
        for (int i = 0; i < banner.size(); i++) {
            Log.e("TAG", "---->----" + banner.get(i).getTitle());
        }
    }
}
