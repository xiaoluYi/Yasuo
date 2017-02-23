package com.sjl.yuehu.data.bean;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by 小鹿 on 2017/2/22.
 */

public class CollectBean extends RealmObject {
    @PrimaryKey
    private int id;
    private String title;
    private String icon;
    private boolean isCollect;

    public boolean isCollect() {
        return isCollect;
    }

    public void setCollect(boolean collect) {
        isCollect = collect;
    }

    public CollectBean(int id, String title,String icon, boolean isCollect) {
        this.id = id;
        this.isCollect = isCollect;
        this.icon = icon;
        this.title = title;
    }

    public CollectBean() {
    }

    public CollectBean(int id, String title, String icon) {
        this.id = id;
        this.title = title;
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

}
