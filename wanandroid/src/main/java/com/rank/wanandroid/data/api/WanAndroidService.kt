package com.rank.wanandroid.data.api

import com.rank.basiclib.di.HttpClientModule
import com.rank.wanandroid.data.entity.*
import io.reactivex.Observable
import retrofit2.http.*

/**
 * <pre>
 *     author: ChenZhaoJun
 *     url  :
 *     time  : 2019/3/11
 *     desc  :
 * </pre>
 */
interface WanAndroidService {

    companion object {
        private const val DOMAIN = "Domain-Name: ${HttpClientModule.DOMAIN.NAME_WANANDROID}"
    }

    /**
     *
     * http://www.wanandroid.com/article/list/0/json
     * @param page page
     */
    @Headers(DOMAIN)
    @GET("/article/list/{page}/json")
    fun articleList(
            @Path("page") page: Int
    ): Observable<WanResponse<ArticleBody>>

    /**
     * 知识体系
     * http://www.wanandroid.com/tree/json
     */
    @Headers(DOMAIN)
    @GET("/tree/json")
    fun getTypeTreeList(): Observable<WanResponse<List<Classification>>>

    /**
     * 知识体系下的文章
     * http://www.wanandroid.com/article/list/0/json?cid=168
     * @param page page
     * @param cid cid
     */
    @Headers(DOMAIN)
    @GET("/article/list/{page}/json")
    fun getArticleListById(
            @Path("page") page: Int,
            @Query("cid") cid: Int
    ): Observable<WanResponse<ArticleBody>>

    /**
     * 常用网站
     * http://www.wanandroid.com/friend/json
     */
    @Headers(DOMAIN)
    @GET("/friend/json")
    fun getFriendList(): Observable<WanResponse<List<LinkData>>>

    /**
     * 大家都在搜
     * http://www.wanandroid.com/hotkey/json
     */
    @Headers(DOMAIN)
    @GET("/hotkey/json")
    fun getHotKeyList(): Observable<WanResponse<List<LinkData>>>

    /**
     * 搜索
     * http://www.wanandroid.com/article/query/0/json
     * @param page page
     * @param k POST search key
     */
    @Headers(DOMAIN)
    @POST("/article/query/{page}/json")
    @FormUrlEncoded
    fun getSearchList(
            @Path("page") page: Int,
            @Field("k") k: String
    ): Observable<WanResponse<ArticleBody>>

    /**
     * 登录
     * @param username username
     * @param password password
     * @return Observable<LoginResponse>
     */
    @Headers(DOMAIN)
    @POST("/user/login")
    @FormUrlEncoded
    fun loginWanAndroid(
            @Field("username") username: String,
            @Field("password") password: String
    ): Observable<WanResponse<UserInfo>>

    /**
     * 注册
     * @param username username
     * @param password password
     * @param repassword repassword
     * @return Observable<LoginResponse>
     */
    @Headers(DOMAIN)
    @POST("/user/register")
    @FormUrlEncoded
    fun registerWanAndroid(
            @Field("username") username: String,
            @Field("password") password: String,
            @Field("repassword") repassowrd: String
    ): Observable<WanResponse<UserInfo>>

    /**
     * 获取自己收藏的文章列表
     * @param page page
     * @return Observable<HomeListResponse>
     */
    @Headers(DOMAIN)
    @GET("/lg/collect/list/{page}/json")
    fun getLikeList(
            @Path("page") page: Int
    ): Observable<WanResponse<ArticleBody>>

    /**
     * 收藏文章
     * @param id id
     * @return Observable<HomeListResponse>
     */
    @Headers(DOMAIN)
    @POST("/lg/collect/{id}/json")
    fun addCollectArticle(
            @Path("id") id: Int
    ): Observable<WanResponse<ArticleBody>>

    /**
     * 收藏站外文章
     * @param title title
     * @param author author
     * @param link link
     * @return Observable<HomeListResponse>
     */
    @Headers(DOMAIN)
    @POST("/lg/collect/add/json")
    @FormUrlEncoded
    fun addCollectOutsideArticle(
            @Field("title") title: String,
            @Field("author") author: String,
            @Field("link") link: String
    ): Observable<WanResponse<ArticleBody>>

    /**
     * 删除收藏文章
     * @param id id
     * @param originId -1
     * @return Observable<HomeListResponse>
     */
    @Headers(DOMAIN)
    @POST("/lg/uncollect/{id}/json")
    @FormUrlEncoded
    fun removeCollectArticle(
            @Path("id") id: Int,
            @Field("originId") originId: Int = -1
    ): Observable<WanResponse<ArticleBody>>

    /**
     * 首页Banner
     * @return BannerResponse
     */
    @Headers(DOMAIN)
    @GET("/banner/json")
    fun getBanner(): Observable<WanResponse<List<BannerItem>>>

    /**
     * 我的常用网址
     * @return FriendListResponse
     */
    @Headers(DOMAIN)
    @GET("/lg/collect/usertools/json")
    fun getBookmarkList(): Observable<WanResponse<List<LinkData>>>

}