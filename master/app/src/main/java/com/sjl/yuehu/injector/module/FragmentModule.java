package com.sjl.yuehu.injector.module;

import android.content.Context;

import com.sjl.yuehu.ui.base.BaseFg;

import dagger.Module;
import dagger.Provides;

/**
 * Created by 小鹿 on 2017/2/4.
 */
@Module
public class FragmentModule {

    private BaseFg fragment;

    public FragmentModule(BaseFg fragment) {
        this.fragment = fragment;
    }

    @Provides
    BaseFg provideFragment() {
        return fragment;
    }

    @Provides
    Context provideContext() {
        return fragment.getContext();
    }
}
