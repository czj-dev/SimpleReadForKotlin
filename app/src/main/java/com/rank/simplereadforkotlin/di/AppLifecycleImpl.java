package com.rank.simplereadforkotlin.di;

import android.content.Context;

import com.google.auto.service.AutoService;
import com.rank.basiclib.application.AppLifecycle;
import com.rank.basiclib.application.BaseApplication;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import dagger.android.AndroidInjector;

/**
 * <pre>
 *     author: ChenZhaoJun
 *     url  :
 *     time  : 2019/1/23
 *     desc  :
 * </pre>
 */
@AutoService(AppLifecycle.class)
public class AppLifecycleImpl implements AppLifecycle {

    @Inject
    Map<Class<? extends ViewModel>, Provider<ViewModel>> viewModels;

    @Inject
    Map<Class<?>, Provider<AndroidInjector.Factory<?>>> classKeyedInjectorFactories;

    @Inject
    Map<String, Provider<AndroidInjector.Factory<?>>> stringKeyedInjectorFactories;

    @Override
    public void attachBaseContext(@NonNull @NotNull Context base) {

    }

    @Override
    public void onCreate(@NonNull @NotNull BaseApplication application) {
        DaggerModuleComponent.builder().appComponent(application.appComponent).build().inject(this);
    }

    @Override
    public void onTerminate(@NonNull @NotNull BaseApplication application) {

    }

    @NotNull
    @Override
    public Map<Class<? extends ViewModel>, Provider<ViewModel>> returnViewModels() {
        return viewModels;
    }


    @NotNull
    @Override
    public Map<Class<?>, Provider<AndroidInjector.Factory<?>>> classKeyedInjectorFactories() {
        return classKeyedInjectorFactories;
    }

    @NotNull
    @Override
    public Map<String, Provider<AndroidInjector.Factory<?>>> stringKeyedInjectorFactories() {
        return stringKeyedInjectorFactories;
    }

}
