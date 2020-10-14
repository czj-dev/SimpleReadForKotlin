package com.rank.basiclib.data

/**
 * <pre>
 *     author: XuPei
 *     url  :
 *     time  : 2019/3/21
 *     desc  : 数据包装基类
 * </pre>
 */

data class AppResponseImpl<T, E>(
    val code: Int,
    val msg: String,
    val data: T?,
    val extData: E?
) : AppResponse<T> {
    override fun code() = code

    override fun success() = code == 1

    override fun data() = data

    fun extData() = extData

    override fun message(): String? {
        return msg
    }
}