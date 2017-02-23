package com.sjl.yuehu.injector.component;

import android.app.Application;
import android.content.SharedPreferences;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.sjl.yuehu.App;
import com.sjl.yuehu.api.ApiService;
import com.sjl.yuehu.injector.module.ApiModule;
import com.sjl.yuehu.injector.module.ApplicationModule;
import javax.inject.Singleton;

import dagger.Component;
import io.realm.Realm;

/**
 * Created by 小鹿 on 2017/2/4.
 */
@Singleton
@Component(modules = {ApplicationModule.class, ApiModule.class,RealmHelper.class})
public interface ApplicationComponent {
    App application();

    DataManager dataManager();

    ApiService apiservice();

    Realm realm();
}
