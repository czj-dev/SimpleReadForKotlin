package com.rank.basiclib.rx.transformers;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;

public final class IncrementalCountTransformer<T> implements ObservableTransformer<T, Integer> {
  final int firstPage;

  public IncrementalCountTransformer() {
    this.firstPage = 1;
  }

  public IncrementalCountTransformer(final int firstPage) {
    this.firstPage = firstPage;
  }

  @Override
  public ObservableSource<Integer> apply(Observable<T> source) {
    return source.scan(this.firstPage-1, (accum, __) -> accum + 1).skip(1);
  }
}
