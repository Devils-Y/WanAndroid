package com.hy.wanandroid.net;

import com.hy.wanandroid.gson.gsonconverter.JsonConverterFactory;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpNet {

    public static final int CONNECT_TIMEOUT = 15;
    public static final int READ_TIMEOUT = 15;
    public static final int WRITE_TIMEOUT = 15;

    public Retrofit mRetrofit = null;
    public OkHttpClient mOkHttpClient = null;

    private static HttpNet httpNet = null;

    public HttpNet() {
    }

    public static HttpNet getInstantes() {
        if (httpNet == null) {
            synchronized (HttpNet.class) {
                if (httpNet == null) {
                    httpNet = new HttpNet();
                }
            }
        }
        return httpNet;
    }

    /**
     * 设置请求网络地址
     *
     * @param baseUrl
     * @return
     */
    public APIInterface httpNet(String baseUrl) {
        if (mRetrofit == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.addInterceptor(new NetLogInterceptor());
            /**
             * 设置连接超时时间
             */
            builder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS);
            /**
             * 设置读取时间
             */
            builder.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS);
            /**
             * 设置写入时间
             */
            builder.writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS);
            /**
             * 错误重连
             */
            builder.retryOnConnectionFailure(true);

            mOkHttpClient = builder.build();
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(mOkHttpClient)
                    .addConverterFactory(JsonConverterFactory.create())//自定义转换器
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//适配器
                    .build();
        }
        return mRetrofit.create(APIInterface.class);
    }

    public APIInterface httpNet() {
        if (mRetrofit == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.addInterceptor(new NetLogInterceptor());
            /**
             * 设置连接超时时间
             */
            builder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS);
            /**
             * 设置读取时间
             */
            builder.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS);
            /**
             * 设置写入时间
             */
            builder.writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS);
            /**
             * 错误重连
             */
            builder.retryOnConnectionFailure(true);

            mOkHttpClient = builder.build();
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(APIInterface.url)
                    .client(mOkHttpClient)
                    .addConverterFactory(JsonConverterFactory.create())//自定义转换器
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//适配器
                    .build();
        }
        return mRetrofit.create(APIInterface.class);
    }
}
