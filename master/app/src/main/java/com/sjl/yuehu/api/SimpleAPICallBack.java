package com.sjl.yuehu.api;

import android.widget.Toast;

import com.sjl.yuehu.util.StringUtil;

/**
 * Created by 小鹿 on 2017/2/8.
 */
public class SimpleAPICallBack<T>extends AbsAPICallback<T> {


    public SimpleAPICallBack() {
        super("连接错误", "连接错误", "未知错误");
    }

    public SimpleAPICallBack(String networkMsg, String parseMsg, String unknownMsg) {
        super(networkMsg, parseMsg, unknownMsg);
    }

    @Override
    protected void onError(ApiException ex) {
//        if (!StringUtil.isEmpty(ex.getDisplayMessage()))
//            ToastUtil.showShort(ex.getDisplayMessage());
    }

    @Override
    protected void onPermissionError(ApiException ex) {
    }

    @Override
    protected void onResultError(ApiException ex) {
//        if (!StringUtil.isEmpty(ex.getDisplayMessage()))
//            ToastUtil.showShort(ex.getDisplayMessage());
    }

    @Override
    public void onNext(T t) {
    }

    @Override
    public void onCompleted() {
    }
}
