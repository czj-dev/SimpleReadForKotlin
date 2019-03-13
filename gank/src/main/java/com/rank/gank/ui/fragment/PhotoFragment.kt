package com.rank.gank.ui.fragment

import android.annotation.SuppressLint
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.rank.basiclib.binding.FragmentDataBindingComponent
import com.rank.basiclib.di.Injectable
import com.rank.basiclib.ext.CompatFragment
import com.rank.basiclib.ext.application
import com.rank.basiclib.ui.DataBindingAdapter
import com.rank.gank.R
import com.rank.gank.databinding.GankFragmentPhotoBinding
import com.rank.gank.databinding.GankGirlPhotoItemBinding
import com.rank.gank.viewmodel.PhotoViewModel
import com.rank.gank.vo.GirlPhoto
import com.rank.service.Router.Gank.PHOTO
import com.trello.rxlifecycle3.android.lifecycle.kotlin.bindToLifecycle
import javax.inject.Inject

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVVMTemplate on 02/12/2019 14:42
 * ================================================
 */
@Route(path = PHOTO)
class PhotoFragment : CompatFragment<GankFragmentPhotoBinding>(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(PhotoViewModel::class.java) }

    override val layoutId = R.layout.gank_fragment_photo

    private val adapter = DataBindingAdapter<GirlPhoto, GankGirlPhotoItemBinding>(
            layoutId = R.layout.gank_girl_photo_item,
            dataBindingComponent = FragmentDataBindingComponent(this),
            callback = { photo, binding, _ -> binding.photo = photo })

    private var layoutManager: ObservableField<RecyclerView.LayoutManager> = ObservableField(LinearLayoutManager(application()))

    @SuppressLint("CheckResult")
    override fun initViews() {
        with(binding) {
            viewModel = this@PhotoFragment.viewModel
            adapter = this@PhotoFragment.adapter
            layout = this@PhotoFragment.layoutManager
        }
        viewModel.loadList()
                .bindToLifecycle(this)
                .subscribe(adapter::setNewData)
        setHasOptionsMenu(true)
    }

    override fun initEvents() {

    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.gank_menu_photo_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?) = when (item?.itemId) {
        R.id.normal_list -> {
            layoutManager.set(LinearLayoutManager(application()))
            true
        }
        R.id.grid_list -> {
            layoutManager.set(GridLayoutManager(application(), 2))
            true
        }
        R.id.waterfall_flow_list -> {
            layoutManager.set(StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL))
            true
        }
        else -> super.onOptionsItemSelected(item)
    }


}