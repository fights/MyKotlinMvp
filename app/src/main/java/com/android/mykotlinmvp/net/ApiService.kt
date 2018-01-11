package com.android.mykotlinmvp.net

import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
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


}