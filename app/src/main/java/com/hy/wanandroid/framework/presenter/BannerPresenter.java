package com.hy.wanandroid.framework.presenter;

import com.hy.wanandroid.bean.BannerBean;
import com.hy.wanandroid.framework.view.BannerView;
import com.hy.wanandroid.net.BaseInterface;
import com.hy.wanandroid.net.BaseListener;
import com.hy.wanandroid.net.HttpNet;

import java.util.List;

/**
 * author: huyin
 * date: 2018/6/12
 */
public class BannerPresenter implements BaseListener<List<BannerBean>> {

    BannerView bannerView;

    public BannerPresenter(BannerView bannerView) {
        this.bannerView = bannerView;
    }

    public void getBanner() {
        new BaseInterface().requestData(HttpNet.getInstantes().httpNet()
                .getBanner(), this);
    }

    @Override
    public void onSuccess(List<BannerBean> bannerBeans) {
        bannerView.setBanner(bannerBeans);
    }

    @Override
    public void onFailed(Throwable t) {

    }

    @Override
    public void onError(String errorString) {

    }
}
