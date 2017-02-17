package com.sjl.yuehu.ui.activity;

import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;

import com.sjl.yuehu.R;
import com.sjl.yuehu.ui.base.BaseAct;
import com.sjl.yuehu.ui.fragment.GuideFragment;
import com.sjl.yuehu.ui.fragment.SplashFragment;
import com.sjl.yuehu.util.SpUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.provider.ContactsContract.Directory.PACKAGE_NAME;

public class GuiderActivity extends BaseAct {
//    @Bind(R.id.toolbar)
//    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guider);
        getActivityComponent().inject(this);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        switchFragment(new SplashFragment(), false);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        RxToolbar.navigationClicks(toolbar).subscribe(aVoid -> {
//            onBackPressed();
//        });

        //默认是第一次启动
//        PackageInfo info = null;
//        try {
//            info = getPackageManager().getPackageInfo(getPackageName(), 0);
//            int currentVersion = info.versionCode;
//            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
//            int lastVersion = prefs.getInt("VERSION_KEY", 0);
//            if (currentVersion > lastVersion) {
//                //如果当前版本大于上次版本，该版本属于第一次启动
//                //将当前版本写入preference中，则下次启动的时候，据此判断，不再为首次启动
//                switchFragment(new GuideFragment(), false);
//                prefs.edit().putInt("VERSION_KEY", currentVersion).commit();
//            } else {
//                switchFragment(new SplashFragment(), false);
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public Toolbar getToolbar() {
        return null;
    }

}
