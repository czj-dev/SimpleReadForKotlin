package com.rank.basiclib.ui

import android.annotation.SuppressLint
import androidx.lifecycle.LifecycleOwner
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.jakewharton.rxbinding3.swiperefreshlayout.refreshes
import com.rank.basiclib.utils.Action
import com.trello.rxlifecycle3.android.lifecycle.kotlin.bindToLifecycle
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.Callable

/**
 * <pre>
 *     author: ChenZhaoJun
 *     url  :
 *     time  : 2019/3/12
 *     desc  :
 * </pre>
 */
@SuppressLint("CheckResult")
class SwipeRefresher(lifecycleOwner: LifecycleOwner, layout: SwipeRefreshLayout, action: Action, isRefreshing: Callable<Observable<Boolean>>) {
    init {

        // Emits when user has signaled to refresh layout
        layout
                .refreshes()
                .bindToLifecycle(lifecycleOwner)
                .subscribe { action.run() }

        // Emits when the refreshing status changes. Hides loading spinner when feed is no longer refreshing.
        isRefreshing.call()
                .filter { refreshing -> !refreshing }
                .bindToLifecycle(lifecycleOwner)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(layout::setRefreshing)
    }
}