package com.hy.wanandroid.framework.presenter;

import com.hy.wanandroid.bean.HotKeyBean;
import com.hy.wanandroid.framework.view.HotKeyView;
import com.hy.wanandroid.net.BaseInterface;
import com.hy.wanandroid.net.BaseListener;
import com.hy.wanandroid.net.HttpNet;

import java.util.List;

import retrofit2.Response;

/**
 * author: huyin
 * date: 2018/6/14
 */
public class HotKeyPresenter implements BaseListener<List<HotKeyBean>>{

    HotKeyView hotKeyView;

    public HotKeyPresenter(HotKeyView hotKeyView) {
        this.hotKeyView = hotKeyView;
    }

    public void getHotKey(){
        new BaseInterface().requestData(HttpNet.getInstantes().httpNet()
                .getHotKey(),this);
    }

    @Override
    public void onSuccess(Response<List<HotKeyBean>> t) {
        if(t.body()!=null){
            hotKeyView.setHotKey(t.body());
        }
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onError(String errorString) {

    }
}
