package com.sjl.yuehu.injector.component;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sjl.yuehu.api.ApiService;
import com.sjl.yuehu.api.RemoteResult;
import com.sjl.yuehu.api.SimpleAPICallBack;
import com.sjl.yuehu.data.bean.HomeBean;
import com.sjl.yuehu.data.bean.ThemesBean;
import com.sjl.yuehu.data.bean.TitileBean;
import com.sjl.yuehu.data.bean.WebBean;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 小鹿 on 2017/2/8.
 */
@Singleton
public class DataManager {
    private final ApiService apiService;
    private final Gson gson;

    @Inject
    public DataManager(ApiService apiService, Gson gson) {
        this.apiService = apiService;
        this.gson = gson;
    }

    //左侧标题栏
    public Subscription getLeftContent(Subscriber<TitileBean> subscriber) {
        return apiService.getLeftContent()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //主题数据
    public Subscription onLoad(int id, Subscriber<ThemesBean> subscriber) {
        return apiService.onLoad(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //首页数据
    public Subscription getLatest(Subscriber<HomeBean> subscriber) {
        return apiService.onLatest()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
    //web
    public Subscription onLoadWeb(int id, Subscriber<WebBean> subscriber) {
        return apiService.onLoadWeb(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
//首页过往消息
    public Subscription getGone(Subscriber<HomeBean> subscriber, String date) {
        return apiService.onGone(date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
    //主题过往消息
    public Subscription onLoadThemeGone(String timestamp, Subscriber<ThemesBean> subscriber, int id) {
        return apiService.onThemeGone(timestamp,id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
