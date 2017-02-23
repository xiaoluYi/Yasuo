package com.sjl.yuehu.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.sjl.yuehu.App;
import com.sjl.yuehu.R;
import com.sjl.yuehu.injector.component.ActivityComponent;
import com.sjl.yuehu.injector.component.DaggerActivityComponent;
import com.sjl.yuehu.injector.module.ActivityModule;
import com.sjl.yuehu.util.StringUtil;

/**
 * Created by 小鹿 on 2017/2/4.
 */
public abstract class BaseAct extends AppCompatActivity implements FragmentListener {
    ActivityComponent mActivityComponent;

    public String currentTag = "";

    public Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public ActivityComponent getActivityComponent() {
        if (mActivityComponent == null) {
            mActivityComponent = DaggerActivityComponent.builder()
                    .applicationComponent(App.getApplicationComponent())
                    .activityModule(new ActivityModule(this))
                    .build();
        }
        return mActivityComponent;
    }

    private void switchFragment(BaseFg fragment, @IdRes int contentRes, boolean addToBackStack) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        if (addToBackStack)
            ft.setCustomAnimations(R.anim.slide_in_right, R.anim.fade_out_center, R.anim.fade_in_center, R.anim.slide_out_right);

        ft.replace(contentRes, fragment, fragment.getName());
        if (addToBackStack)
            ft.addToBackStack(fragment.getName());
        ft.commit();
    }

    @Override
    public void switchFragment(BaseFg fragment, boolean addToBackStack) {
        switchFragment(fragment, R.id.content_layout, addToBackStack);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    public void fragmentVisible(BaseFg fragment) {
        if ((toolbar = getToolbar()) != null && !StringUtil.isEmpty(fragment.getFragmentTitle()))
            toolbar.setTitle(fragment.getFragmentTitle());
        currentTag = fragment.getName();
    }

    @Override
    public void updateTitle(String title) {
        if (toolbar != null)
            toolbar.setTitle(StringUtil.getValue(title, ""));
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
    }

    @Override
    public void finish() {
        super.finish();
    }

    public abstract Toolbar getToolbar();
}
