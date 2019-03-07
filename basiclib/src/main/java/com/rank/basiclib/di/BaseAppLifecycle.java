package com.rank.basiclib.di;

import android.content.Context;

import com.rank.basiclib.application.AppLifecycle;
import com.rank.basiclib.application.BaseApplication;
import com.rank.basiclib.error.ServiceException;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import dagger.android.AndroidInjector;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * <pre>
 *     author: ChenZhaoJun
 *     url  :
 *     time  : 2019/3/6
 *     desc  :
 * </pre>
 */
public abstract class BaseAppLifecycle implements AppLifecycle {

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
    public abstract void onCreate(@NonNull @NotNull BaseApplication application);

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

    @NotNull
    @Override
    public Response onHttpResultResponse(@Nullable String httpResult, @NotNull Interceptor.Chain chain, @NotNull Response response) {
        return response;
    }

    @NotNull
    @Override
    public Request onHttpRequestBefore(@NotNull Interceptor.Chain chain, @NotNull Request request) {
        return request;
    }

    @Override
    public boolean handlerServiceException(@NotNull ServiceException t) {
        return false;
    }
}

