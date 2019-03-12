package com.rank.basiclib.rx.transformers;

import android.util.Pair;

import androidx.annotation.NonNull;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;

public final class TakePairWhenTransformer<S, T> implements ObservableTransformer<S, Pair<S, T>> {

  @NonNull private final Observable<T> when;

  public TakePairWhenTransformer(final @NonNull Observable<T> when) {
    this.when = when;
  }



  @Override
  public ObservableSource<Pair<S, T>> apply(Observable<S> source) {
    return this.when.withLatestFrom(source, (x, y) -> new Pair<>(y, x));
  }
}
