package com.hy.wanandroid.net;

import android.util.Log;

import com.hy.wanandroid.ui.toast.ToastUtils;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;
import retrofit2.Response;

/**
 * author: huyin
 * date: 2018/6/13
 */
public abstract class BaseObserver<T> implements Observer<T> {

    @Override
    public void onSubscribe(Disposable d) {
    }

    @Override
    public void onNext(T t) {
        callNext((Response<T>) t);
    }

    @Override
    public void onError(Throwable t) {
        String errorString = null;
        if (t instanceof UnknownHostException) {
            errorString = "网络连接异常，请打开网络";
        } else if (t instanceof SocketTimeoutException) {
            errorString = "请求超时";
        } else if (t instanceof ConnectException) {
            errorString = "连接失败";
        } else if (t instanceof HttpException) {
            errorString = "请求超时";
        } else {
            errorString = t.getMessage();
        }
        ToastUtils.toast(errorString);
        error(errorString);
        t.printStackTrace();
    }

    @Override
    public void onComplete() {
        onSuccess();
    }

    public abstract void callNext(Response<T> t);

    public abstract void onSuccess();

    public abstract void error(String s);
}

