package com.rank.wanandroid.ui.activity

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.alibaba.android.arouter.facade.annotation.Route
import com.rank.basiclib.Constant
import com.rank.basiclib.binding.ActivityDataBindingComponent
import com.rank.basiclib.di.Injectable
import com.rank.basiclib.ext.CompatActivity
import com.rank.basiclib.ui.DataBindingAdapter
import com.rank.basiclib.ui.RecyclerViewPaginator
import com.rank.basiclib.ui.SwipeRefresher
import com.rank.basiclib.utils.Action
import com.rank.binddepend_annotation.BindDepend
import com.rank.service.Router.WanAndroid.HOME
import com.rank.wanandroid.R
import com.rank.wanandroid.data.entity.ArticleItem
import com.rank.wanandroid.databinding.WanActivityAndroidHomeBinding
import com.rank.wanandroid.databinding.WanArticleItemBinding
import com.rank.wanandroid.viewmodel.AndroidHomeViewModel
import com.trello.rxlifecycle3.android.lifecycle.kotlin.bindToLifecycle
import java.util.concurrent.Callable
import javax.inject.Inject


/**
 * ================================================
 * Description:
 * <p>
 * Created by 03/07/2019 14:39
 * ================================================
 */
@BindDepend(Constant.ClassType.ACTIVITY)
@Route(path = HOME)
class AndroidHomeActivity : CompatActivity<WanActivityAndroidHomeBinding>(), Injectable {

    override val layoutId = R.layout.wan_activity_android_home
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(AndroidHomeViewModel::class.java) }

    private lateinit var viewPaginator: RecyclerViewPaginator

    private val adapter = DataBindingAdapter<ArticleItem, WanArticleItemBinding>(
            layoutId = R.layout.wan_article_item,
            dataBindingComponent = ActivityDataBindingComponent(this),
            callback = { article, binding, _ -> binding.article = article })

    override fun initViews() {
        binding.adapter = adapter
        viewPaginator = RecyclerViewPaginator(binding.recyclerView, { viewModel.nextPage() }, viewModel.isFetchingComments())
        SwipeRefresher(this, binding.swipeRefresh, Action { viewModel.refresh() }, Callable { viewModel.isFetchingComments() })
    }

    @SuppressLint("CheckResult")
    override fun initEvents() {
        viewModel.list()
                .bindToLifecycle(this)
                .subscribe(adapter::setNewData)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewPaginator.stop()
    }
}