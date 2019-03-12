package com.rank.basiclib.rx.transformers;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;

public final class IgnoreValuesTransformer<S> implements ObservableTransformer<S, Object> {


    @Override
    public ObservableSource<Object> apply(Observable<S> source) {
        return source.map(__ -> new Object());
    }
}
