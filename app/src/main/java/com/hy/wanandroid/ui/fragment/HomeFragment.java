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
import com.hy.wanandroid.framework.presenter.CollectPresenter;
import com.hy.wanandroid.framework.presenter.UnCollectWithOriginIdPresenter;
import com.hy.wanandroid.framework.view.BannerView;
import com.hy.wanandroid.framework.view.CollectView;
import com.hy.wanandroid.framework.view.UnCollectWithOriginIdView;
import com.hy.wanandroid.ui.activity.ArticleListActivity;
import com.hy.wanandroid.ui.activity.WebActivity;
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
import com.hy.wanandroid.ui.view.ColorHeadImage;
import com.hy.wanandroid.util.banner.GlideImageLoader;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;
import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static com.hy.wanandroid.constants.Constants.LINK;

/**
 * Created by huyin on 2018/4/23.
 * <p>
 * 首页
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener,
        JsonView, FriendView, BannerView, CollectView, UnCollectWithOriginIdView,
        GlideImageLoader.OnImageClickListener{

    private int mScrollY = 0;

    ImageView searchView;
    Toolbar toolbar;
    NestedScrollView scrollView;
    Banner mBanner;
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
    CollectPresenter collectPresenter;
    UnCollectWithOriginIdPresenter unCollectWithOriginIdPresenter;
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
        mBanner = getContentView().findViewById(R.id.banner);
        refreshLayout = getContentView().findViewById(R.id.refreshLayout);
        friendRecyclerView = getContentView().findViewById(R.id.friendRecyclerView);
        artRecyclerView = getContentView().findViewById(R.id.artRecyclerView);
        searchView.setOnClickListener(this);
        /**
         * banner
         */

        //设置轮播时间
        mBanner.setDelayTime(3000);
        GlideImageLoader glideImageLoader = new GlideImageLoader();
        mBanner.setImageLoader(glideImageLoader);
        glideImageLoader.setOnImageClickListener(this);
        /**
         * 常用网址
         */
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        friendRecyclerView.setLayoutManager(linearLayoutManager);
        friendList = new LinkedList<>();
        friendAdapter = new BaseAdapter<FriendBean>(getActivity(), friendList,
                R.layout.item_friend_web) {
            @Override
            protected void onBindViewHolder(BaseViewHolder holder,
                                            FriendBean item, int position) {
                holder.setText(R.id.name, item.getName());
                ColorHeadImage colorHeadImage = holder.getView(R.id.icon);
                colorHeadImage.setName(item.getName());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), WebActivity.class);
                        intent.putExtra(LINK, item.getLink());
                        startActivity(intent);
                    }
                });
            }
        };
        friendRecyclerView.setAdapter(friendAdapter);

        /**
         * 首页列表数据
         */
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
        jsonAdapter = new BaseAdapter<JsonBean.DatasBean>(getActivity(), jsonList,
                R.layout.item_home_art) {
            @Override
            protected void onBindViewHolder(BaseViewHolder holder,
                                            JsonBean.DatasBean item, int position) {
                holder.setText(R.id.authorName, item.getAuthor())
                        .setText(R.id.belong2, item.getSuperChapterName()
                                + "/" + item.getChapterName())
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
                        Intent intent = new Intent(getActivity(), WebActivity.class);
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
        collectPresenter = new CollectPresenter(this);
        unCollectWithOriginIdPresenter = new UnCollectWithOriginIdPresenter(this);
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
        mBanner.setImages(banner).start();

    }

    @Override
    public void setCollect() {
        ToastUtils.toast("收藏成功");
    }

    @Override
    public void setUnCollect() {
        ToastUtils.toast("取消收藏成功");
    }

    @Override
    public void onStart() {
        super.onStart();
        //开始轮播
        mBanner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
        mBanner.stopAutoPlay();
    }

    @Override
    public void onImageClick(String link) {
        Intent intent = new Intent(getActivity(), WebActivity.class);
        intent.putExtra(LINK, link);
        startActivity(intent);
    }
}
