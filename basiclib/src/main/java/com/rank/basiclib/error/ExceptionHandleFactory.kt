package com.rank.basiclib.error

import android.app.Application
import retrofit2.HttpException

/**
 * <pre>
 *     author: ChenZhaoJun
 *     url  :
 *     time  : 2018/8/8
 *     desc  :
 * </pre>
 */
class ExceptionHandleFactory constructor(private val application: Application) {

    private fun convertStatusCode(httpException: HttpException): String {
        return when {
            httpException.code() == 500 -> "服务器发生错误"
            httpException.code() == 404 -> "请求地址不存在"
            httpException.code() == 403 -> "请求被服务器拒绝"
            httpException.code() == 307 -> "请求被重定向到其他页面"
            else -> "未知错误 错误码${httpException.code()}"
        }
    }

    companion object {
        const val TAG = "HttpLog-Error"
        const val ERROE_PARAMS_REQUEST = "10100"
    }
}