package com.sjl.yuehu.mvp.presenter;

import com.facebook.stetho.common.LogUtil;
import com.sjl.yuehu.data.bean.ThemesBean;
import com.sjl.yuehu.data.bean.WebBean;
import com.sjl.yuehu.data.bean.WebExtraBean;
import com.sjl.yuehu.injector.component.DataManager;
import com.sjl.yuehu.ui.base.BasePresenter;
import com.sjl.yuehu.mvp.view.WebViewMvpView;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by 小鹿 on 2017/2/13.
 */

public class WebViewPresenter extends BasePresenter<WebViewMvpView> {
    private DataManager dataManager;
    private Subscription subscription;
    private int pageIndex = -1;

    @Inject
    public WebViewPresenter(DataManager dataManager) {
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
        Subscriber<WebBean> subscriber=new Subscriber<WebBean>(){

            @Override
            public void onCompleted() {
                LogUtil.e("请求完成");
            }

            @Override
            public void onError(Throwable e) {
                LogUtil.e(e + "");
            }

            @Override
            public void onNext(WebBean bean) {
                if (isViewAttached())
                    LogUtil.e(bean+"");
                getMvpView().onLoad(bean);
            }

        };
        subscription = dataManager.onLoadWeb(id,subscriber);
    }

    public void onLoadExtra(int id) {
        Subscriber<WebExtraBean> subscriber=new Subscriber<WebExtraBean>(){

            @Override
            public void onCompleted() {
                LogUtil.e("请求完成");
            }

            @Override
            public void onError(Throwable e) {
                LogUtil.e(e + "");
            }

            @Override
            public void onNext(WebExtraBean bean) {
                if (isViewAttached())
                    LogUtil.e(bean+"");
                getMvpView().onLoadExtra(bean);
            }

        };
        subscription = dataManager.onLoadExtra(id,subscriber);
    }
}
