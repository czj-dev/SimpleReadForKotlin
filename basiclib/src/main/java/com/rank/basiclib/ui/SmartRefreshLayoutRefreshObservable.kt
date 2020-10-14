package com.rank.basiclib.ui

import androidx.annotation.CheckResult
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.MainThreadDisposable


/**
 * Create an observable of refresh events on `view`.
 *
 * *Warning:* The created observable keeps a strong reference to `view`. Unsubscribe
 * to free this reference.
 */
@CheckResult
fun SmartRefreshLayout.refreshes(): Observable<Unit> {
    return SwipeRefreshLayoutRefreshObservable(this)
}

private class SwipeRefreshLayoutRefreshObservable(
    private val view: SmartRefreshLayout
) : Observable<Unit>() {

    override fun subscribeActual(observer: Observer<in Unit>) {
        if (!checkMainThread(observer)) {
            return
        }
        val listener = Listener(view, observer)
        observer.onSubscribe(listener)
        view.setOnRefreshListener(listener)
    }

    private class Listener(
        private val view: SmartRefreshLayout,
        private val observer: Observer<in Unit>
    ) : MainThreadDisposable(), OnRefreshListener {
        override fun onRefresh(refreshLayout: RefreshLayout) {
            if (!isDisposed) {
                observer.onNext(Unit)
            }
        }

        override fun onDispose() {
            view.setOnRefreshListener(null)
        }
    }
}