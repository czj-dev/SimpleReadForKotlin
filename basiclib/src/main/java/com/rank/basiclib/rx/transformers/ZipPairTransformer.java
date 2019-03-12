package com.rank.basiclib.rx.transformers;

import android.util.Pair;

import androidx.annotation.NonNull;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;

public final class ZipPairTransformer<T, R> implements ObservableTransformer<T, Pair<T, R>> {
  @NonNull private final Observable<R> second;

  public ZipPairTransformer(final @NonNull Observable<R> second) {
    this.second = second;
  }

  @Override
  public ObservableSource<Pair<T, R>> apply(Observable<T> first) {
    return Observable.zip(first, this.second, Pair::new);
  }
}
