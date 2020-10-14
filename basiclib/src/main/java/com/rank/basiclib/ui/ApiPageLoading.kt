package com.rank.basiclib.ui

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import com.chad.library.adapter.base.BaseQuickAdapter
import com.rank.basiclib.error.ExceptionHandleFactory
import com.rank.basiclib.rx.transformers.Transformers
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

/**
 * <pre>
 *     author: ChenZhaoJun
 *     url  :
 *     time  : 2019/7/16
 *     desc  :
 * </pre>
 */
class ApiPageLoading<Data, Envelope, Params> : DiffUtil.Callback() {

    fun build(): ApiPageLoading<Data, Envelope, Params> {
        val startObtainData: Observable<Params> = startOverWith.invoke()
        paginationData = startObtainData.switchMap(this::dataWithPagination)
        paginationData.subscribe(
            {
                if (loadingPageValue() == 1) {
                    resultCallback.loadInitial(it)
                } else {
                    resultCallback.loadAfter(it)
                }
            }, ExceptionHandleFactory.instance::handlerResponseError
        )
        startObtainData.switchMap { nextPage.scan(1, { sum, _ -> sum + 1 }) }
            .subscribe(_loadingPage::onNext)
        return this
    }

    private fun dataWithPagination(firstParams: Params): Observable<MutableList<Data>> {
        return _moreParams
            .compose(Transformers.takeWhen<Params, Any>(nextPage))
            .startWith(firstParams)
            .concatMap(loadWithParams::invoke)
            .doOnNext(this::keepMoreParams)
            .map { envelopeToListData.invoke(it) }
            .doOnSubscribe { _isFetching.onNext(true) }
            .doAfterTerminate { _isFetching.onNext(false) }
    }

    private fun keepMoreParams(it: Envelope) {
        val params = enveloperToMoreParams.invoke(it)
        _moreParams.onNext(params)
    }

    private var oldList: MutableList<Data> = arrayListOf()
    private var newList: MutableList<Data> = arrayListOf()

    private var _isFetching = PublishSubject.create<Boolean>()

    private var _loadingPage = BehaviorSubject.createDefault(1)

    private var _moreParams = PublishSubject.create<Params>()

    private lateinit var paginationData: Observable<MutableList<Data>>

    lateinit var itemTheSame: (oldData: Data, newData: Data) -> Boolean

    lateinit var contentsTheSame: (oldData: Data, newData: Data) -> Boolean

    lateinit var envelopeToListData: (envelop: Envelope) -> MutableList<Data>

    lateinit var loadWithParams: (Params) -> Observable<Envelope>

    lateinit var startOverWith: () -> Observable<Params>

    lateinit var enveloperToMoreParams: (envelop: Envelope) -> Params

    lateinit var resultCallback: ResultCallback<Data>

    lateinit var nextPage: Observable<Any>

    fun isFetching(): Observable<Boolean> = _isFetching

    fun loadingPage(): Observable<Int> = _loadingPage

    fun loadingPageValue() = _loadingPage.value!!

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        itemTheSame.invoke(oldList[oldItemPosition], newList[newItemPosition])


    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        contentsTheSame.invoke(oldList[oldItemPosition], newList[newItemPosition])


    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size


    class BaseQuickUpdateListCallback(private val menuAdapter: BaseQuickAdapter<*, *>) : ListUpdateCallback {
        override fun onChanged(position: Int, count: Int, payload: Any?) {
            // 框架 BaseQuickAdapter 目前不支持带 payload 的局部更新，所以这里先刷新整个 item，等待框架完善该功能
            menuAdapter.refreshNotifyItemChanged(position)
        }

        override fun onMoved(fromPosition: Int, toPosition: Int) {
            menuAdapter.notifyItemMoved(
                fromPosition + menuAdapter.headerLayoutCount,
                toPosition + menuAdapter.headerLayoutCount
            )
        }

        override fun onInserted(position: Int, count: Int) {
            menuAdapter.notifyItemRangeInserted(
                position + menuAdapter.headerLayoutCount,
                count
            )
        }

        override fun onRemoved(position: Int, count: Int) {
            menuAdapter.notifyItemRangeRemoved(
                position + menuAdapter.headerLayoutCount,
                count
            )
        }
    }

    interface ResultCallback<T> {

        fun loadInitial(list: MutableList<T>)

        fun loadAfter(list: MutableList<T>)
    }
}
