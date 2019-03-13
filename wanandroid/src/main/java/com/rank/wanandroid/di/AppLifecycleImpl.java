package com.rank.wanandroid.di;

import com.google.auto.service.AutoService;
import com.rank.basiclib.application.AppLifecycle;
import com.rank.basiclib.application.BaseApplication;
import com.rank.basiclib.di.BaseAppLifecycle;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;

/**
 * <pre>
 *     author: ChenZhaoJun
 *     url  :
 *     time  : 2019/3/11
 *     desc  :
 * </pre>
 */
@AutoService(AppLifecycle.class)
public class AppLifecycleImpl extends BaseAppLifecycle {

    @Override
    public void onCreate(@NonNull @NotNull BaseApplication application) {
        DaggerModuleComponent.builder().appComponent(application.appComponent).build().inject(this);
    }

}
