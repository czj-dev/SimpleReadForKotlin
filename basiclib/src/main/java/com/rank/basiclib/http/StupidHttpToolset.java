package com.rank.basiclib.http;

import androidx.annotation.NonNull;
import androidx.collection.ArrayMap;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.GsonBuilder;
import com.rank.basiclib.di.ApiManager;
import com.rank.basiclib.ext.CommonExtKt;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;

/**
 * <pre>
 *     author: ChenZhaoJun
 *     url  :
 *     time  : 2019/5/22
 *     desc  : 网络请求工具，不校验 https，没有必要的情况下不要使用。
 * </pre>
 */
public class StupidHttpToolset {


    private static final int CONFIG_NETWORK_TIME_OUT = 30;

    private volatile static Retrofit sInstance;
    private volatile static Retrofit.Builder sBuilder;
    private volatile static ArrayMap<String, Object> sApis;

    public static void recreate() {
        sApis.clear();
        sInstance = null;
        sBuilder = null;
    }

    /**
     * 创建网络请求
     */
    public static <T> T createApi(Class<T> aClass) {
        if (sInstance == null) {
            synchronized (StupidHttpToolset.class) {
                if (sInstance == null) {
                    createRetrofit();
                }
            }
        }
        return getApiImpl(aClass);
    }

    private static <T> T getApiImpl(Class<T> aClass) {
        if (sApis == null) {
            sApis = new ArrayMap<>();
            return initApi(aClass);
        } else {
            Object api = sApis.get(aClass.getName());
            if (api == null) {
                return initApi(aClass);
            } else {
                try {
                    return (T) api;
                } catch (Exception e) {
                    return initApi(aClass);
                }
            }
        }
    }

    private static <T> T initApi(Class<T> aClass) {
        T value = sInstance.create(aClass);
        sApis.put(aClass.getName(), value);
        return value;
    }

    private static void createRetrofit() {
        OkHttpClient.Builder client = providerClient();
        Retrofit.Builder retrofit = getBuilder();
        sBuilder = retrofit;
        retrofit.client(client.build());
        sInstance = retrofit.build();
    }

    @NonNull
    private static OkHttpClient.Builder providerClient() {
        OkHttpClient.Builder client = new OkHttpClient.Builder().connectTimeout(CONFIG_NETWORK_TIME_OUT, TimeUnit.SECONDS).readTimeout(CONFIG_NETWORK_TIME_OUT, TimeUnit.SECONDS);
        if (CommonExtKt.debug()) {
            client.addNetworkInterceptor(new StethoInterceptor());
        }
        client.sslSocketFactory(SSLSocketClient.getSSLSocketFactory());
        client.hostnameVerifier(SSLSocketClient.getHostnameVerifier());
        return client;
    }

    @NonNull
    private static Retrofit.Builder getBuilder() {
        return new Retrofit.Builder()
                .baseUrl(ApiManager.getInstance().getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
    }

    public static Retrofit.Builder getRetrofit() {
        if (sBuilder == null) {
            sBuilder = getBuilder();
        }
        return sBuilder;
    }



}
