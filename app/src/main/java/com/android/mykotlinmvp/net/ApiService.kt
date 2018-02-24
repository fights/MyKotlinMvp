package com.android.mykotlinmvp.net

import com.hazz.kotlinmvp.mvp.model.bean.CategoryBean
import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import com.hazz.kotlinmvp.mvp.model.bean.TabInfoBean
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * Created by zhangguanjun on 2018/1/5.
 */
interface ApiService{

    /**
     * 首页精品
     */
    @GET("v2/feed?")
    fun getFirstHomeData(@Query("num") num: Int): Flowable<HomeBean>

    /**
     * 首页获取更多数据
     */
    @GET
    fun getMoreHomeData(@Url url: String): Flowable<HomeBean>

    /**
     * 获取相关视频
     */
    @GET("v4/video/related?")
    fun getRelativeVideos(@Query("id") id: Long): Flowable<HomeBean.Issue>

    /**
     * 关注
     */
    @GET("v4/tabs/follow")
    fun getFollowInfo(): Flowable<HomeBean.Issue>


    /**
     * 获取更多数据
     */
    @GET
    fun getMoreData(@Url url: String): Flowable<HomeBean.Issue>

    /**
     * 获取分类信息
     */
    @GET("v4/categories")
    fun getCategoryInfo(): Flowable<ArrayList<CategoryBean>>

    /**
     * 分类详情
     */
    @GET("v4/categories/videoList")
    fun getCategoryDetailInfo(@Query("id") id: Long): Flowable<HomeBean.Issue>

    /**
     * 分类详情更多数据
     */
    @GET
    fun getCategoryDetailMoreInfo(@Url url: String): Flowable<HomeBean.Issue>

    /**
     * 获取热门tab信息
     */
    @GET("v4/rankList")
    fun getHotTabInfo(): Flowable<TabInfoBean>

    /**
     * 获取热门中每个tab中对应的详细信息
     */
    @GET
    fun getRankData(@Url url: String): Flowable<HomeBean.Issue>

}