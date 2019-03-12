package com.rank.basiclib.rx.transformers;


import com.rank.basiclib.utils.ThreadUtils;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;

public final class ObserveForUITransformer<T> implements ObservableTransformer<T, T> {


    @Override
    public ObservableSource<T> apply(Observable<T> source) {
        return source.flatMap(value -> {
            if (ThreadUtils.isMainThread()) {
                return Observable.just(value);
            } else {
                return Observable.just(value).observeOn(AndroidSchedulers.mainThread());
            }
        });
    }
}
