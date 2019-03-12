package com.rank.basiclib.rx.transformers;

import androidx.annotation.NonNull;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;

public final class WaitUntilTransformer<T, R> implements ObservableTransformer<T, T> {
  @NonNull private final Observable<R> until;

  public WaitUntilTransformer(final @NonNull Observable<R> until) {
    this.until = until;
  }



  @Override
  public ObservableSource<T> apply(Observable<T> source) {
    return this.until.take(1).flatMap(__ -> source);
  }
}
