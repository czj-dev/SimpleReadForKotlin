package com.rank.basiclib.rx.transformers;

import androidx.annotation.NonNull;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;

import static com.rank.basiclib.utils.ObjectUtils.coalesceWith;


public final class CoalesceTransformer<T> implements ObservableTransformer<T, T> {
  private final T theDefault;

  public CoalesceTransformer(final @NonNull T theDefault) {
    this.theDefault = theDefault;
  }


  @Override
  public ObservableSource<T> apply(Observable<T> source) {
    return source
            .map(coalesceWith(this.theDefault));
  }
}
