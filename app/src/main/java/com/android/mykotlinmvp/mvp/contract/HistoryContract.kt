package com.android.mykotlinmvp.mvp.contract

import com.android.mykotlinmvp.mvp.IBaseView
import com.android.mykotlinmvp.mvp.IPresenter
import com.hazz.kotlinmvp.mvp.model.bean.HomeBean

/**
 * Created by zhangguanjun on 2018/2/28.
 */
interface HistoryContract{
    interface View: IBaseView{
        fun showHistory(data: ArrayList<HomeBean.Issue.Item>)
    }

    interface Presenter : IPresenter<View> {
        fun loadHistoryData()
    }
}