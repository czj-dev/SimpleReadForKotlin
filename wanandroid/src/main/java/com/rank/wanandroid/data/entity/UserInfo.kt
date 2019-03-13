package com.rank.wanandroid.data.entity

/**
 * <pre>
 *     author: ChenZhaoJun
 *     url  :
 *     time  : 2019/3/11
 *     desc  :
 * </pre>
 */
data class UserInfo(var id: Int,
                    var username: String,
                    var password: String,
                    var icon: String?,
                    var type: Int,
                    var collectIds: List<Int>?)