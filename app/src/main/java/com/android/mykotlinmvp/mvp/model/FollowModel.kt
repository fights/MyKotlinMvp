package com.android.mykotlinmvp.mvp.model

import com.android.mykotlinmvp.net.RetrofitManager
import com.android.mykotlinmvp.utils.SchedulerUtil
import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import io.reactivex.Flowable

/**
 * Created by zhangguanjun on 2018/1/25.
 */
class FollowModel{
    fun loadFollowVideos(): Flowable<HomeBean.Issue>{
        return RetrofitManager.apiService.getFollowInfo()
                .compose(SchedulerUtil.ioToMain())
    }

    fun loadMoreFollowVideos(url: String): Flowable<HomeBean.Issue> {
        return RetrofitManager.apiService.getMoreData(url)
                .compose(SchedulerUtil.ioToMain())
    }
}