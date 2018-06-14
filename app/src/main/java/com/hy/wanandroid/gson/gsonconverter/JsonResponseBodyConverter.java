package com.hy.wanandroid.gson.gsonconverter;


import android.util.Log;

import com.hy.wanandroid.gson.ErrorCode;
import com.hy.wanandroid.gson.GsonHelper;
import com.hy.wanandroid.ui.toast.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import okio.BufferedSource;
import okio.Okio;
import retrofit2.Converter;


public class JsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private final Type type;

    public JsonResponseBodyConverter(Type type) {
        this.type = type;
    }

    /**
     * 转换
     *
     * @param responseBody
     * @return
     * @throws IOException
     */
    @Override
    public T convert(ResponseBody responseBody) throws IOException {
        BufferedSource bufferedSource = Okio.buffer(responseBody.source());
        String tempStr = bufferedSource.readUtf8();
        bufferedSource.close();
        JSONObject jsonObject = null;
        T t = null;
        try {
            jsonObject = new JSONObject(tempStr);
            if (jsonObject.getString("errorCode") != null) {
                ErrorCode.code = Integer.valueOf(jsonObject.getString("errorCode"));
                if (ErrorCode.isSuccess()) {
                    Log.e("TAG","--->---");
                    if (jsonObject.getString("data") != null) {
                        Log.e("TAG","---这里---");
                        t = GsonHelper.getDeserializer().fromJson(jsonObject.getString("data"), type);
                    }else{
                        Log.e("TAG","---==null---");
                    }
                } else {
                    ToastUtils.toast(jsonObject.getString("errorMsg"));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return t;
    }
}