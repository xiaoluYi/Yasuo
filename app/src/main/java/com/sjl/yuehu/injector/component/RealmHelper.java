package com.sjl.yuehu.injector.component;

import android.content.Context;

import com.sjl.yuehu.injector.scope.ForApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by 小鹿 on 2017/2/22.
 */
@Module
public class RealmHelper {
    private Realm realm;

    @Provides
    @Singleton
    Realm provideRealmHelper(@ForApplication Context context) {
        Realm.init(context);
        realm = Realm.getInstance(new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .name("jifeng.realm")
                .build()

        );
        return realm;
    }
}
