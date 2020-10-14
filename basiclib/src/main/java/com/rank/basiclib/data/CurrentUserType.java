package com.rank.basiclib.data;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.reactivex.Observable;

public abstract class CurrentUserType {

    /**
     * Call when a user has logged in. The implementation of `CurrentUserType` is responsible
     * for persisting the user and access token.
     */
    public abstract void user(final @NonNull UserInfo newUser);

    public abstract void token(final @NonNull Token accessToken);

    /**
     * Call when a user should be logged out.
     */
    public abstract void logout();

    /**
     * Get the logged in user's access token.
     */
    public abstract @Nullable
    Token getToken();

    /**
     * Updates the persisted current user with a fresh, new user.
     */
    public abstract void refresh(final @NonNull UserInfo userData);

    /**
     * Returns an observable representing the current user. It emits immediately
     * with the current user, and then again each time the user is updated.
     */
    public abstract @NonNull
    Observable<UserInfo> observable();

    /**
     * Returns the most recently emitted user from the user observable.
     */
    public abstract UserInfo getUser();

    /**
     * Returns a boolean that determines if there is a currently logged in user or not.
     */
    public boolean exists() {
        UserInfo user = getUser();
        return user != null && user.getId() != 0;
    }

    abstract boolean isLogin(UserInfo userInfo);

    /**
     * Emits a boolean that determines if the user is logged in or not. The returned
     * observable will emit immediately with the logged in state, and then again
     * each time the current user is updated.
     */
    public @NonNull
    Observable<Boolean> isLoggedIn() {
        return observable().map(this::isLogin);
    }

    /**
     * Emits only values of a logged in user. The returned observable may never emit.
     */
    public @NonNull
    Observable<UserInfo> loggedInUser() {
        return observable().filter(this::isLogin);
    }

    /**
     * Emits only values of a logged out user. The returned observable may never emit.
     */
    public @NonNull
    Observable<UserInfo> loggedOutUser() {
        return observable().filter(userInfo -> !isLogin(userInfo));
    }
}
