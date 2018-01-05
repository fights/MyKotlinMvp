package com.android.mykotlinmvp.net

import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by zhangguanjun on 2018/1/5.
 */
interface ApiService{

    /**
     * 首页精品
     */
    @GET("v2/feed?")
    fun getFirstHomeData(@Query("num") num: Int): Flowable<>
}