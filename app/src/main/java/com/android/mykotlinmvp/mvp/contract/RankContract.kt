package com.android.mykotlinmvp.mvp.contract

import com.android.mykotlinmvp.mvp.IBaseView
import com.android.mykotlinmvp.mvp.IPresenter
import com.hazz.kotlinmvp.mvp.model.bean.HomeBean

/**
 * Created by zhangguanjun on 2018/2/24.
 */
interface RankContract{
    interface View: IBaseView{
        fun showRank(itemList: ArrayList<HomeBean.Issue.Item>)

        fun showErrorMsg(errorMsg: String, errorCode: Int)
    }

    interface Presenter : IPresenter<View> {
        fun getRankData(url: String)
    }
}