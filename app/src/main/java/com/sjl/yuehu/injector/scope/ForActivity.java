package com.sjl.yuehu.injector.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by 小鹿 on 2017/2/4.
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface ForActivity {
}
