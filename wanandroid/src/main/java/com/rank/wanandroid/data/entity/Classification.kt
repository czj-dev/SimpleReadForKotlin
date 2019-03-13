package com.rank.wanandroid.data.entity

/**
 * <pre>
 *     author: ChenZhaoJun
 *     url  :
 *     time  : 2019/3/11
 *     desc  :
 * </pre>
 */

data class Classification(var id: Int,
                          var name: String,
                          var courseId: Int,
                          var parentChapterId: Int,
                          var order: Int,
                          var visible: Int,
                          var children: List<Classification>?)
