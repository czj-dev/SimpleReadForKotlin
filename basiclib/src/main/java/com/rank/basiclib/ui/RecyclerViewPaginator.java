package com.rank.basiclib.ui;

import android.util.Pair;

import com.jakewharton.rxbinding3.recyclerview.RxRecyclerView;
import com.rank.basiclib.utils.Action;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

import static com.rank.basiclib.rx.transformers.Transformers.combineLatestPair;


public final class RecyclerViewPaginator {
  private final @NonNull RecyclerView recyclerView;
  private final @NonNull Action nextPage;
  private Observable<Boolean> isLoading;
  private Disposable disposable;
  private static final int DIRECTION_DOWN = 1;

  public RecyclerViewPaginator(final @NonNull RecyclerView recyclerView, final @NonNull Action nextPage, final @NonNull Observable<Boolean> isLoading) {
    this.recyclerView = recyclerView;
    this.nextPage = nextPage;
    this.isLoading = isLoading;
    start();
  }

  /**
   * Begin listening to the recycler view scroll events to determine
   * when pagination should happen.
   */
  public void start() {
    stop();

    final Observable<Pair<Integer, Integer>> lastVisibleAndCount = RxRecyclerView.scrollEvents(this.recyclerView)
      .filter(__ -> this.recyclerView.canScrollVertically(DIRECTION_DOWN))
      .map(__ -> this.recyclerView.getLayoutManager())
      .ofType(LinearLayoutManager.class)
      .map(this::displayedItemFromLinearLayout)
      .filter(item -> item.second != 0)
      .distinctUntilChanged();

    final Observable<Boolean> isNotLoading = this.isLoading
      .distinctUntilChanged()
      .filter(loading -> !loading);

    final Observable<Pair<Integer, Integer>> loadNextPage = lastVisibleAndCount
      .compose(combineLatestPair(isNotLoading))
      .distinctUntilChanged()
      .map(p -> p.first)
      .filter(this::visibleItemIsCloseToBottom);

    this.disposable = loadNextPage
      .subscribe(__ -> this.nextPage.run());
  }

  /**
   * Stop listening to recycler view scroll events and discard the
   * associated resources. This should be done when the object that
   * created `this` is released.
   */
  public void stop() {
    if (this.disposable != null) {
      this.disposable.dispose();
      this.disposable = null;
    }
  }

  /**
   * Returns a (visibleItem, totalItemCount) pair given a linear layout manager.
   */
  private @NonNull
  Pair<Integer, Integer> displayedItemFromLinearLayout(final @NonNull LinearLayoutManager manager) {
    return new Pair<>(manager.findLastVisibleItemPosition(), manager.getItemCount());
  }

  private boolean visibleItemIsCloseToBottom(final @NonNull Pair<Integer, Integer> visibleItemOfTotal) {
    return visibleItemOfTotal.first == visibleItemOfTotal.second - 1;
  }
}
