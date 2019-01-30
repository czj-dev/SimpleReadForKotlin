package com.rank.simplereadforkotlin.entity

import com.rank.basiclib.data.AppResponse

data class GankResult<T>(
        val error: Boolean,
        val results: T
) : AppResponse<T> {
    override fun code() = 0

    override fun success() = error

    override fun data() = results

    override fun message(): String? {
        return null
    }
}