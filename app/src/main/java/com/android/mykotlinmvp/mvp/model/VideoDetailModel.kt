package com.android.mykotlinmvp.mvp.model

import com.android.mykotlinmvp.net.RetrofitManager
import com.android.mykotlinmvp.utils.SchedulerUtil
import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import io.reactivex.Flowable

/**
 * Created by zhangguanjun on 2018/1/19.
 */
class VideoDetailModel{
    fun getRelativeVideos(id: Long): Flowable<HomeBean.Issue> {
        return RetrofitManager.apiService.getRelativeVideos(id)
                .compose(SchedulerUtil.ioToMain())
    }
}