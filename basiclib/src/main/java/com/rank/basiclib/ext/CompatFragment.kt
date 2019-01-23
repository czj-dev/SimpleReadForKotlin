package com.rank.basiclib.ext

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

/**
 * <pre>
 *     author: ChenZhaoJun
 *     url  :
 *     time  : 2019/1/23
 *     desc  :
 * </pre>
 */
abstract class CompatFragment<B : ViewDataBinding> : Fragment() {

    private lateinit var rootView: View

    protected lateinit var binding: B

    abstract val layoutId: Int


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        rootView = LayoutInflater.from(context).inflate(layoutId, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DataBindingUtil.bind(rootView)!!
        with(binding) {
            setLifecycleOwner(this@CompatFragment)
        }
    }

}