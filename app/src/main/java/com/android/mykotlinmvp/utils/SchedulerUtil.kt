package com.android.mykotlinmvp.utils

import io.reactivex.FlowableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * Created by zhangguanjun on 2018/1/11.
 */
object SchedulerUtil{
    fun <T> ioToMain():  FlowableTransformer<T,T>{
        return FlowableTransformer { o ->
            o.subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }
}