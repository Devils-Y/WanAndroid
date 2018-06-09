package com.hy.wanandroid.framework.presenter;

import com.hy.wanandroid.bean.FriendBean;
import com.hy.wanandroid.framework.view.FriendView;
import com.hy.wanandroid.net.BaseInterface;
import com.hy.wanandroid.net.BaseListener;
import com.hy.wanandroid.net.HttpNet;

import java.util.List;

/**
 * author: huyin
 * date: 2018/6/7
 */
public class FriendPresenter implements BaseListener<List<FriendBean>> {

    FriendView friendView;

    public FriendPresenter(FriendView friendView) {
        this.friendView = friendView;
    }

    public void getFriend() {
        new BaseInterface().requestData(HttpNet.getInstantes().httpNet().getFriend(), this);
    }

    @Override
    public void onSuccess(List<FriendBean> friendBeans) {
        friendView.setFriend(friendBeans);
    }

    @Override
    public void onFailed(Throwable t) {

    }

    @Override
    public void onError(String errorString) {

    }
}
