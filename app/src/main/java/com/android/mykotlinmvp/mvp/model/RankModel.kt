package com.android.mykotlinmvp.mvp.model

import com.android.mykotlinmvp.net.RetrofitManager
import com.android.mykotlinmvp.utils.SchedulerUtil
import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import io.reactivex.Flowable

/**
 * Created by zhangguanjun on 2018/2/24.
 */
class RankModel{
    fun getRankData(url: String): Flowable<HomeBean.Issue> {
        return RetrofitManager.apiService.getRankData(url)
                .compose(SchedulerUtil.ioToMain())
    }
}