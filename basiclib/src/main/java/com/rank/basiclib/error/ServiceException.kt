package com.rank.basiclib.error

import com.rank.basiclib.data.AppResponse

/**
 * <pre>
 *     author: ChenZhaoJun
 *     url  :
 *     time  : 2019/1/30
 *     desc  :
 * </pre>
 */

class ServiceException( val response: AppResponse<*>) : RuntimeException() {

    override val message: String
        get() = response.message() ?: super.message ?: ""
}