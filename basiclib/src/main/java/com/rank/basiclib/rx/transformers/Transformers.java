package com.rank.basiclib.rx.transformers;


import androidx.annotation.NonNull;
import io.reactivex.Observable;

public final class Transformers {
  private Transformers() {}

  /**
   * Transforms `null` values emitted from an observable into `theDefault`.
   */
  public static @NonNull
  <T> CoalesceTransformer<T> coalesce(final @NonNull T theDefault) {
    return new CoalesceTransformer<>(theDefault);
  }

  /**
   * Prevents an observable from erroring by chaining `onErrorResumeNext`.
   */
  public static <T> NeverErrorTransformer<T> neverError() {
    return new NeverErrorTransformer<>();
  }


  /**
   * Emits the latest value of the source observable whenever the `when`
   * observable emits.
   */
  public static <S, T> TakeWhenTransformer<S, T> takeWhen(final @NonNull Observable<T> when) {
    return new TakeWhenTransformer<>(when);
  }

  /**
   * Emits the latest value of the source `when` observable whenever the
   * `when` observable emits.
   */
  public static <S, T> TakePairWhenTransformer<S, T> takePairWhen(final @NonNull Observable<T> when) {
    return new TakePairWhenTransformer<>(when);
  }

  /**
   * Zips two observables up into an observable of pairs.
   */
  public static <S, T> ZipPairTransformer<S, T> zipPair(final @NonNull Observable<T> second) {
    return new ZipPairTransformer<>(second);
  }

  /**
   * Emits the latest values from two observables whenever either emits.
   */
  public static <S, T> CombineLatestPairTransformer<S, T> combineLatestPair(final @NonNull Observable<T> second) {
    return new CombineLatestPairTransformer<>(second);
  }

  /**
   * Waits until `until` emits one single item and then switches context to the source. This
   * can be useful to delay work until a user logs in:
   *
   * ```
   * somethingThatRequiresAuth
   *   .compose(waitUntil(currentUser.loggedInUser()))
   *   .subscribe(show)
   * ```
   */
  public static @NonNull
  <T, R> WaitUntilTransformer<T, R> waitUntil(final @NonNull Observable<R> until) {
    return new WaitUntilTransformer<>(until);
  }

  /**
   * Converts an observable of any type into an observable of `null`s. This is useful for forcing
   * Java's type system into knowing we have a stream of `Void`. Simply doing `.map(__ -> null)`
   * is not enough since Java doesn't know if that is a `null` String, Integer, Void, etc.
   *
   * This transformer allows the following pattern:
   *
   * ```
   * myObservable
   *   .compose(takeWhen(click))
   *   .compose(ignoreValues())
   *   .subscribe(subject::onNext)
   * ```
   */
  public static @NonNull
  <S> IgnoreValuesTransformer<S> ignoreValues() {
    return new IgnoreValuesTransformer<>();
  }

  /**
   * Emits the number of times the source has emitted for every emission of the source. The
   * first emitted value will be `1`.
   */
  public static @NonNull
  <T> IncrementalCountTransformer<T> incrementalCount() {
    return new IncrementalCountTransformer<>();
  }


  /**
   * If called on the main thread, schedule the work immediately. Otherwise delay execution of the work by adding it
   * to a message queue, where it will be executed on the main thread.
   *
   * This is particularly useful for RecyclerViews; if subscriptions in these views are delayed for a frame, then
   * the view temporarily shows recycled content and frame rate stutters. To address that, we can use `observeForUI()`
   * to execute the work immediately rather than wait for a frame.
   */
  public static @NonNull
  <T> ObserveForUITransformer<T> observeForUI() {
    return new ObserveForUITransformer<>();
  }
}
