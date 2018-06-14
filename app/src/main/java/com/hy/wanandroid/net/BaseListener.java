package com.hy.wanandroid.net;


import retrofit2.Response;

public interface BaseListener<T> {
    void onSuccess(Response<T> t);

    void onSuccess();

    void onError(String errorString);
}
