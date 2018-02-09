package com.android.mykotlinmvp.mvp.model

import com.android.mykotlinmvp.net.RetrofitManager
import com.android.mykotlinmvp.utils.SchedulerUtil
import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import io.reactivex.Flowable

/**
 * Created by zhangguanjun on 2018/2/9.
 */
class CategoryDetailModel{
    fun getData(id: Long): Flowable<HomeBean.Issue>{
        return RetrofitManager.apiService.getCategoryDetailInfo(id)
                .compose(SchedulerUtil.ioToMain())
    }

    fun getMoreData(url: String): Flowable<HomeBean.Issue> {
        return RetrofitManager.apiService.getCategoryDetailMoreInfo(url)
                .compose(SchedulerUtil.ioToMain())
    }
}