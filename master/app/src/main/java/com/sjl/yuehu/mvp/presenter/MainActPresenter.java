package com.sjl.yuehu.mvp.presenter;

import com.facebook.stetho.common.LogUtil;
import com.google.gson.JsonObject;
import com.sjl.yuehu.api.ApiException;
import com.sjl.yuehu.api.SimpleAPICallBack;
import com.sjl.yuehu.data.bean.TitileBean;
import com.sjl.yuehu.injector.component.DataManager;
import com.sjl.yuehu.mvp.view.HomePageMvpView;
import com.sjl.yuehu.mvp.view.MainActMvpView;
import com.sjl.yuehu.ui.activity.MainAct;
import com.sjl.yuehu.ui.base.BasePresenter;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by 小鹿 on 2017/2/8.
 */

public class MainActPresenter extends BasePresenter<MainActMvpView> {
    private DataManager dataManager;
    private Subscription subscription;
    private int pageIndex = -1;

    @Inject
    public MainActPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    //
    @Override
    public void detachView() {
        super.detachView();
        if (subscription != null && !subscription.isUnsubscribed())
            subscription.unsubscribe();
    }


    public void onList() {
        Subscriber<TitileBean> subscriber=new Subscriber<TitileBean>(){

            @Override
            public void onCompleted() {
                LogUtil.e("请求完成");
            }

            @Override
            public void onError(Throwable e) {
                LogUtil.e(e + "");
            }

            @Override
            public void onNext(TitileBean titileBean) {
                if (isViewAttached())
                    getMvpView().onList(titileBean);
            }

        };
        subscription = dataManager.getLeftContent(subscriber);
    }

}
