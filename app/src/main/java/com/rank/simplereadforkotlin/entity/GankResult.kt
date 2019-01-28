package com.rank.simplereadforkotlin.entity

data class GankResult<T>(
        val error: Boolean,
        val results: T
)