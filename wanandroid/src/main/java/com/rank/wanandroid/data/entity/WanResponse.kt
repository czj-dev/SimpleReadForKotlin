package com.rank.wanandroid.data.entity

import com.rank.basiclib.data.AppResponse

/**
 * <pre>
 *     author: ChenZhaoJun
 *     url  :
 *     time  : 2019/3/11
 *     desc  :
 * </pre>
 */
data class WanResponse<T>(var errorCode: Int,
                          var errorMsg: String?, var data: T) : AppResponse<T> {


    override fun code() = errorCode

    override fun success() = errorCode == 0

    override fun data() = data

    override fun message() = errorMsg

}
