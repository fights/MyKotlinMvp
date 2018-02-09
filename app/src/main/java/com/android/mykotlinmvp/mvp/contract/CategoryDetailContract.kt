package com.android.mykotlinmvp.mvp.contract

import com.android.mykotlinmvp.mvp.IBaseView
import com.android.mykotlinmvp.mvp.IPresenter
import com.hazz.kotlinmvp.mvp.model.bean.HomeBean

/**
 * Created by zhangguanjun on 2018/2/9.
 */
interface CategoryDetailContract{
    interface View: IBaseView{
        fun showData(data: ArrayList<HomeBean.Issue.Item>)

        fun showMoreData(data: ArrayList<HomeBean.Issue.Item>)

        fun showError(errorMsg: String, errorCode: Int)
    }

    interface Presenter : IPresenter<View> {
        fun getData(id: Long)

        fun getMoreData()
    }
}