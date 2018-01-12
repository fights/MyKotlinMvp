package com.android.mykotlinmvp.mvp.model

import com.android.mykotlinmvp.net.RetrofitManager
import com.android.mykotlinmvp.utils.SchedulerUtil
import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import io.reactivex.Flowable

/**
 * Created by zhangguanjun on 2018/1/11.
 */
class HomeModel{
    /**
     * 获取首页数据
     */
    fun loadHomeData(num: Int): Flowable<HomeBean> {
        return RetrofitManager.apiService.getFirstHomeData(num)
                .compose(SchedulerUtil.ioToMain())
    }

    /**
     * 获取更多数据
     */
    fun loadMoreData(url: String): Flowable<HomeBean> {
        return RetrofitManager.apiService.getMoreHomeData(url)
                .compose(SchedulerUtil.ioToMain())
    }
}