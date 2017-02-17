package com.sjl.yuehu;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;

import com.facebook.stetho.Stetho;
import com.sjl.yuehu.injector.component.ApplicationComponent;
import com.sjl.yuehu.injector.component.DaggerApplicationComponent;
import com.sjl.yuehu.injector.module.ApplicationModule;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import java.util.Set;

/**
 * Created by 小鹿 on 2017/1/18.
 */

public class App extends Application {
    private static Context sContext;
    private static ApplicationComponent mApplicationComponent;
    private static RefWatcher sRefWatcher;
    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
        setStrictMode();
        initStetho();
        initLeakCanary();
        setupInjector();
    }

    private void setupInjector() {
        mApplicationComponent = DaggerApplicationComponent.builder().
                applicationModule(new ApplicationModule(this))
                .build();
    }

    private void initLeakCanary() {
        sRefWatcher= LeakCanary.install(this);
    }

    private void initStetho() {
        Stetho.initializeWithDefaults(this);
    }

    private void setStrictMode() {
        if (BuildConfig.DEBUG && Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
        }
    }

    public static Context getContext() {
        return sContext;
    }

    public static ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }
}
