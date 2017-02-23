package com.sjl.yuehu.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sjl.yuehu.App;
import com.sjl.yuehu.injector.component.DaggerFragmentComponent;
import com.sjl.yuehu.injector.component.FragmentComponent;
import com.sjl.yuehu.injector.module.ActivityModule;
import com.sjl.yuehu.injector.module.FragmentModule;

import javax.inject.Inject;

/**
 * Created by 小鹿 on 2017/2/4.
 */
public abstract class BaseFg extends Fragment {
    private String title = "";
    private boolean isMenu1Visible = true;

    public FragmentListener fgListener;

    private FragmentComponent fragmentComponent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public FragmentComponent getFragmentComponent() {
        if (fragmentComponent == null) {
            fragmentComponent = DaggerFragmentComponent.builder()
                    .applicationComponent(App.getApplicationComponent())
                    .activityModule(new ActivityModule((BaseAct) getActivity()))
                    .fragmentModule(new FragmentModule(this))
                    .build();
        }

        return fragmentComponent;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fgListener = (FragmentListener) getActivity();
    }

    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (fgListener != null)
            fgListener.fragmentVisible(this);
    }

    public boolean popFromFragment(String starName) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        return fm.popBackStackImmediate(starName, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
    }

    public String getFragmentTitle() {
        return title;
    }

    public void setFragmentTitle(String title) {
        this.title = title;
    }

    public boolean isMenu1Visible() {
        return isMenu1Visible;
    }

    public void isMenu1Visible(boolean isMenu1Visible) {
        this.isMenu1Visible = isMenu1Visible;
    }
}

