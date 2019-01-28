package com.rank.simplereadforkotlin

import com.rank.basiclib.di.Injectable
import com.rank.basiclib.ext.CompatFragment

/**
 * <pre>
 *     author: ChenZhaoJun
 *     url  :
 *     time  : 2019/1/25
 *     desc  :
 * </pre>
 */
class MainFragment : CompatFragment<com.rank.simplereadforkotlin.databinding.AppFragmentMainBinding>(), Injectable {

    override val layoutId: Int=R.layout.app_fragment_main

}