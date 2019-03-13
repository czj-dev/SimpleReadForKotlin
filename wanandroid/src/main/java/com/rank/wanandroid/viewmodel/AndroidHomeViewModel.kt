package com.rank.wanandroid.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.rank.basiclib.Constant
import com.rank.basiclib.ui.ApiPaginator
import com.rank.binddepend_annotation.BindDepend
import com.rank.wanandroid.data.entity.ArticleBody
import com.rank.wanandroid.data.entity.ArticleItem
import com.rank.wanandroid.data.repository.ArticleRepository
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

@BindDepend(Constant.ClassType.VIEWMODEl)
class AndroidHomeViewModel @Inject constructor(application: Application, private val repository: ArticleRepository) : AndroidViewModel(application) {

    private val isFetchingComments = BehaviorSubject.create<Boolean>()
    private val refresh = PublishSubject.create<Any>()
    private val nextPage = PublishSubject.create<Any>()
    private var apiPaginator: ApiPaginator<ArticleItem, ArticleBody, Int>

    init {
        apiPaginator = createApiPaginator()
        apiPaginator
                .isFetching
                .subscribe(isFetchingComments)
    }

    private fun createApiPaginator() = ApiPaginator.builder<ArticleItem, ArticleBody, Int>()
                    .nextPage(this.nextPage)
                    .startOverWith(Observable.just(0))
                    .distinctUntilChanged(true)
                    .envelopeToListOfData(ArticleBody::data)
                    .envelopeToMoreParams { data -> data.curPage + 1 }
                    .loadWithParams(repository::loadList)
                    .build()


    fun list() = apiPaginator.paginatedData()

    fun isFetchingComments(): Observable<Boolean> = isFetchingComments

    fun nextPage() {
        nextPage.onNext(Constant.Any)
    }

    fun refresh() {
        apiPaginator = createApiPaginator()
    }

}