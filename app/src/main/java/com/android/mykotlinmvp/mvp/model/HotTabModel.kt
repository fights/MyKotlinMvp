package com.android.mykotlinmvp.mvp.model

import com.android.mykotlinmvp.net.RetrofitManager
import com.android.mykotlinmvp.utils.SchedulerUtil
import com.hazz.kotlinmvp.mvp.model.bean.TabInfoBean
import io.reactivex.Flowable

/**
 * Created by zhangguanjun on 2018/2/11.
 */
class HotTabModel{
    fun getHotTabInfo(): Flowable<TabInfoBean> {
        return RetrofitManager.apiService.getHotTabInfo()
                .compose(SchedulerUtil.ioToMain())
    }
}