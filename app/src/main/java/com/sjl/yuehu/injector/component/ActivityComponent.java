package com.sjl.yuehu.injector.component;

import com.sjl.yuehu.injector.module.ActivityModule;
import com.sjl.yuehu.injector.scope.ActivityScope;
import com.sjl.yuehu.ui.activity.GuiderActivity;
import com.sjl.yuehu.ui.activity.MainAct;

import dagger.Component;

/**
 * Created by 小鹿 on 2017/2/4.
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(GuiderActivity guiderActivity);

    void inject(MainAct mainAct);
}
