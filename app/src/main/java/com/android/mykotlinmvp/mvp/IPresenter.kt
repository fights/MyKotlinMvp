package com.android.mykotlinmvp.mvp

/**
 * Created by zhangguanjun on 2018/1/10.
 */
interface IPresenter<in V: IBaseView>{
    fun attachView(rootView: V)
    fun detachView()
}