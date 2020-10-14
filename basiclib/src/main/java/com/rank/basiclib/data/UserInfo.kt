package com.rank.basiclib.data

import java.io.Serializable


data class UserInfo(
        var id: Int? = 0,
        val username: String? = null,
        val name: String? = null
) : Serializable

