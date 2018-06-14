package com.hy.wanandroid.framework.presenter;

import com.hy.wanandroid.bean.FriendBean;
import com.hy.wanandroid.framework.view.FriendView;
import com.hy.wanandroid.net.BaseInterface;
import com.hy.wanandroid.net.BaseListener;
import com.hy.wanandroid.net.HttpNet;

import java.util.List;

import retrofit2.Response;

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
        new BaseInterface().requestData(HttpNet.getInstantes().httpNet()
                .getFriend(), this);
    }

    @Override
    public void onSuccess(Response<List<FriendBean>> t) {
        if (t.body() != null) {
            friendView.setFriend(t.body());
        }
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onError(String errorString) {

    }
}
