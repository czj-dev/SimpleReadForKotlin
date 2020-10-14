package com.rank.basiclib.data;


import android.app.Application;

import com.rank.basiclib.ext.CommonExtKt;
import com.rank.basiclib.utils.Preferences;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.subjects.BehaviorSubject;

/**
 * @author chenzhaojun
 * @date 2017/10/31
 * @description
 */
public class CurrentUser extends CurrentUserType {
    private final BehaviorSubject<UserInfo> user = BehaviorSubject.create();
    private Token token;

    public @Inject
    CurrentUser(Application application) {
        userInit();
        tokenInit();
    }

    private UserInfo userInit() {
        UserInfo userInfo = CommonExtKt.get(Preferences.Companion.userPreferences(), Preferences.USER);
        if (userInfo == null) {
            return null;
        }
        this.user.onNext(userInfo);
        return userInfo;
    }

    private void tokenInit() {
        Token token = CommonExtKt.get(Preferences.Companion.userPreferences(), Preferences.TOKEN);
        if (token == null ) {
            return;
        }
        this.token = token;
    }

    @Override
    public void user(@NonNull UserInfo newUser) {
        refresh(newUser);
    }

    @Override
    public void token(@NonNull Token accessToken) {
        this.token = accessToken;
        Preferences.Companion.userPreferences().put(Preferences.REFRESH_TOKEN_DATE, String.valueOf(System.currentTimeMillis()));
        saveToken(accessToken);
    }

    private void saveToken(Token token) {
        CommonExtKt.put(Preferences.Companion.userPreferences(), Preferences.TOKEN, token);
    }

    private void saveUser(UserInfo userData) {
        Preferences.Companion.configPreferences().put(Preferences.SETTING_HISTORY_MOBILE, userData.getUsername());
        CommonExtKt.put(Preferences.Companion.userPreferences(), Preferences.USER, userData);
    }

    @Override
    public void logout() {
        user.onNext(new UserInfo());
        token = null;
        Preferences.Companion.userPreferences().clear();
    }


    @Nullable
    @Override
    public Token getToken() {
        if (token == null) {
            tokenInit();
        }
        return token;
    }

    @Override
    public void refresh(@NonNull UserInfo userData) {
        saveUser(userData);
        user.onNext(userData);
    }

    @NonNull
    @Override
    public Observable<Boolean> isLoggedIn() {
        return user.map(this::isLogin);
    }

    @NonNull
    @Override
    public Observable<UserInfo> observable() {
        // 应用崩溃后内存中不在存有 User 的信息
        if (getUser() == null) {
            userInit();
        }
        return user.filter(this::isLogin).observeOn(AndroidSchedulers.mainThread());
    }

    @NonNull
    @Override
    public Observable<UserInfo> loggedOutUser() {
        return user.filter(userInfo -> !isLogin(userInfo));
    }

    /**
     * 可能会得到错误的结果，尽可能的使用 {@link #observable()}
     */
    @Override
    public UserInfo getUser() {
        final UserInfo userInfo = user.getValue();
        if (userInfo != null && userInfo.getId() != 0) {
            return userInfo;
        } else if (userInfo == null) {
            return userInit();
        }
        return null;
    }

    @Override
    boolean isLogin(UserInfo userInfo) {
        return userInfo != null && userInfo.getId() != 0;
    }

}
