package com.hy.wanandroid.net;


public interface BaseListener<T> {
     void onSuccess(T t);

     void onFailed(Throwable t);

     void onError(String errorString);
}
