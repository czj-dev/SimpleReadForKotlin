package com.rank.basiclib.data

/**
 * <pre>
 *     author: ChenZhaoJun
 *     url  :
 *     time  : 2019/1/30
 *     desc  :
 * </pre>
 */

interface AppResponse<T> {
    fun code(): Int
    fun success(): Boolean
    fun data(): T?
    fun message(): String?
}