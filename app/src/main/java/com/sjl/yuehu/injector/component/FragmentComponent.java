package com.sjl.yuehu.injector.component;

import com.sjl.yuehu.injector.module.ActivityModule;
import com.sjl.yuehu.injector.module.FragmentModule;
import com.sjl.yuehu.injector.scope.ActivityScope;
import com.sjl.yuehu.ui.fragment.CollectMineFg;
import com.sjl.yuehu.ui.fragment.HomePageFg;
import com.sjl.yuehu.ui.fragment.SplashFragment;
import com.sjl.yuehu.ui.fragment.ThemePageFg;

import dagger.Component;

/**
 * Created by 小鹿 on 2017/2/4.
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, FragmentModule.class})
public interface FragmentComponent extends ActivityComponent {
    void inject(HomePageFg homePageFg);

    void inject(ThemePageFg themePageFg);

    void inject(SplashFragment splashFragment);

    void inject(CollectMineFg collectMineFg);
}
