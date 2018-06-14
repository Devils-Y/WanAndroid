package com.hy.wanandroid.net;


import android.annotation.SuppressLint;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Response;

public class BaseInterface {

    @SuppressLint("CheckResult")
    public <T> void requestData(Observable<T> observable, BaseListener baseListener) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<T>() {
                    @Override
                    public void callNext(Response<T> t) {
                        baseListener.onSuccess(t);
                    }

                    @Override
                    public void onSuccess() {
                        baseListener.onSuccess();
                    }

                    @Override
                    public void error(String s) {
                        baseListener.onError(s);
                    }
                });
    }
}
