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

class ServiceException() : RuntimeException() {

    var response: AppResponse<*>? = null
    private var errorMessage: String? = null
    private var errorCode: Int = 0

    constructor(errorMessage: String, errorCode: Int) : this() {
        this.errorCode = errorCode
        this.errorMessage = errorMessage
    }

    constructor (response: AppResponse<*>) : this() {
        this.response = response
        this.errorMessage = response.message()
        this.errorCode = response.code()
    }

    override val message: String
        get() = errorMessage ?: super.message ?: ""
}