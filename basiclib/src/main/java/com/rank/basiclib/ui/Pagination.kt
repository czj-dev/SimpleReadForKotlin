package com.rank.basiclib.ui


/**
 * <pre>
 *     author: ChenZhaoJun
 *     url  :
 *     time  : 2019/7/16
 *     desc  :
 * </pre>
 */
fun <Data, Enveloper, Params> pagination(apiPageLoading: ApiPageLoading<Data, Enveloper, Params>.() -> Unit): ApiPageLoading<Data, Enveloper, Params> {
   return ApiPageLoading<Data, Enveloper, Params>().apply(apiPageLoading).build()
}