package com.sjl.yuehu.mvp.presenter;

import com.facebook.stetho.common.LogUtil;
import com.sjl.yuehu.data.bean.ThemesBean;
import com.sjl.yuehu.data.bean.TitileBean;
import com.sjl.yuehu.injector.component.DataManager;
import com.sjl.yuehu.mvp.view.ThemePageFgMvpView;
import com.sjl.yuehu.ui.base.BasePresenter;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by 小鹿 on 2017/2/10.
 */
public class ThemePageFgPresenter extends BasePresenter<ThemePageFgMvpView>{
    private DataManager dataManager;
    private Subscription subscription;
    private int pageIndex = -1;

    @Inject
    public ThemePageFgPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    //
    @Override
    public void detachView() {
        super.detachView();
        if (subscription != null && !subscription.isUnsubscribed())
            subscription.unsubscribe();
    }


    public void onLoad(int id) {
        Subscriber<ThemesBean> subscriber=new Subscriber<ThemesBean>(){

            @Override
            public void onCompleted() {
                LogUtil.e("请求完成");
            }

            @Override
            public void onError(Throwable e) {
                LogUtil.e(e + "");
            }

            @Override
            public void onNext(ThemesBean bean) {
                if (isViewAttached())
                    LogUtil.e(bean+"");
                    getMvpView().onLoad(bean);
            }

        };
        subscription = dataManager.onLoad(id,subscriber);
    }

    public void onLoadGone(String timestamp, int id) {
        Subscriber<ThemesBean> subscriber=new Subscriber<ThemesBean>(){

            @Override
            public void onCompleted() {
                LogUtil.e("请求完成");
            }

            @Override
            public void onError(Throwable e) {
                LogUtil.e(e + "");
            }

            @Override
            public void onNext(ThemesBean bean) {
                if (isViewAttached())
                    LogUtil.e(bean+"");
                getMvpView().onLoad(bean);
            }

        };
        subscription = dataManager.onLoadThemeGone(timestamp,subscriber,id);
    }
}
