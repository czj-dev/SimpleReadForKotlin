package com.rank.wanandroid.data.entity

/**
 * <pre>
 *     author: ChenZhaoJun
 *     url  :
 *     time  : 2019/3/11
 *     desc  :
 * </pre>
 */
data class BannerItem(var id: Int,
                      var url: String,
                      var imagePath: String,
                      var title: String,
                      var desc: String,
                      var isVisible: Int,
                      var order: Int,
                      var type: Int)