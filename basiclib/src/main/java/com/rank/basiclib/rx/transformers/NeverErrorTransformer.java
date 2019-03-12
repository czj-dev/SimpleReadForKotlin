package com.rank.basiclib.rx.transformers;

import androidx.annotation.Nullable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Consumer;

public final class NeverErrorTransformer<T> implements ObservableTransformer<T, T> {
  private final @Nullable Consumer<Throwable> errorAction;

  protected NeverErrorTransformer() {
    this.errorAction = null;
  }

  protected NeverErrorTransformer(final @Nullable Consumer<Throwable> errorAction) {
    this.errorAction = errorAction;
  }


  @Override
  public ObservableSource<T> apply(Observable<T> source) {
    return source
            .doOnError(e -> {
              if (this.errorAction != null) {
                this.errorAction.accept(e);
              }
            })
            .onErrorResumeNext(Observable.empty());
  }
}
