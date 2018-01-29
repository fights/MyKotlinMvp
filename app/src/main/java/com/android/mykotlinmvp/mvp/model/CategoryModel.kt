package com.android.mykotlinmvp.mvp.model

import com.android.mykotlinmvp.net.RetrofitManager
import com.android.mykotlinmvp.utils.SchedulerUtil
import com.hazz.kotlinmvp.mvp.model.bean.CategoryBean
import io.reactivex.Flowable

/**
 * Created by Mr Zhang on 2018/1/29.
 */
class CategoryModel{
    fun getCategory(): Flowable<ArrayList<CategoryBean>>{
        return RetrofitManager.apiService.getCategoryInfo()
                .compose(SchedulerUtil.ioToMain())
    }
}