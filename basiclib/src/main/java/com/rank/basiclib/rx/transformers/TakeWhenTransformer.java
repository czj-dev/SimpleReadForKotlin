package com.rank.basiclib.rx.transformers;

import androidx.annotation.NonNull;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;

public final class TakeWhenTransformer<S, T> implements ObservableTransformer<S, S> {
  @NonNull private final Observable<T> when;

  public TakeWhenTransformer(final @NonNull Observable<T> when) {
    this.when = when;
  }


  @Override
  public ObservableSource<S> apply(Observable<S> source) {
    return this.when.withLatestFrom(source, (__, x) -> x);
  }
}
