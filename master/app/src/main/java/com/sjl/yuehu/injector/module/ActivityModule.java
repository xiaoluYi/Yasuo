package com.sjl.yuehu.injector.module;

import android.content.Context;

import com.sjl.yuehu.injector.scope.ForActivity;
import com.sjl.yuehu.ui.base.BaseAct;

import dagger.Module;
import dagger.Provides;

/**
 * Created by 小鹿 on 2017/2/4.
 */
@Module
public class ActivityModule {

    private BaseAct mActivity;

    public ActivityModule(BaseAct activity) {
        mActivity = activity;
    }

    @Provides
    BaseAct provideActivity() {
        return mActivity;
    }

    @Provides
    @ForActivity
    Context providesContext() {
        return mActivity;
    }
}
