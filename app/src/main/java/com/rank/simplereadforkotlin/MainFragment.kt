package com.rank.simplereadforkotlin

import com.rank.basiclib.di.Injectable
import com.rank.basiclib.ext.CompatFragment
import com.rank.simplereadforkotlin.databinding.AppFragmentMainBinding

/**
 * <pre>
 *     author: ChenZhaoJun
 *     url  :
 *     time  : 2019/1/25
 *     desc  :
 * </pre>
 */
class MainFragment : CompatFragment<AppFragmentMainBinding>(), Injectable {

    override val layoutId = R.layout.app_fragment_main

    override fun initViews() {

    }

    override fun initEvents() {

    }

}