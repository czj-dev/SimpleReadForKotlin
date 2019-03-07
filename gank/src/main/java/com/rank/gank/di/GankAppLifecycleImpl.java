package com.rank.gank.di;

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
 *     time  : 2019/1/23
 *     desc  :
 * </pre>
 */
@AutoService(AppLifecycle.class)
public class GankAppLifecycleImpl extends BaseAppLifecycle {
    @Override
    public void onCreate(@NonNull @NotNull BaseApplication application) {
        DaggerGankModuleComponent.builder().appComponent(application.appComponent).build().inject(this);
    }
}
