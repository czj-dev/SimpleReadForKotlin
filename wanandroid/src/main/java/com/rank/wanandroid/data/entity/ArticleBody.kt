package com.rank.wanandroid.data.entity

import com.google.gson.annotations.SerializedName

/**
 * <pre>
 *     author: ChenZhaoJun
 *     url  :
 *     time  : 2019/3/11
 *     desc  :
 * </pre>
 */
data class ArticleBody(var offset: Int,
                       var size: Int,
                       var total: Int,
                       var pageCount: Int,
                       var curPage: Int,
                       var over: Boolean,
                       @SerializedName("datas")
                       var data: List<ArticleItem>?)

data class ArticleItem(var id: Int,
                       var originId: Int,
                       var title: String,
                       var chapterId: Int,
                       var chapterName: String?,
                       var envelopePic: Any,
                       var link: String,
                       var author: String,
                       var origin: Any,
                       var publishTime: Long,
                       var zan: Any,
                       var desc: Any,
                       var visible: Int,
                       var niceDate: String,
                       var courseId: Int,
                       var collect: Boolean)