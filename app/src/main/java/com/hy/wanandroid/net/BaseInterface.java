package com.hy.wanandroid.net;


import android.annotation.SuppressLint;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class BaseInterface {

    @SuppressLint("CheckResult")
    public <T> void requestData(Observable<T> observable,BaseListener baseListener) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<T>() {
                    @Override
                    public void accept(T t) throws Exception {
                        baseListener.onSuccess(t);
                    }
                });
    }
}
