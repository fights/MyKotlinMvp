package com.android.mykotlinmvp.mvp.contract

import com.android.mykotlinmvp.mvp.IBaseView
import com.android.mykotlinmvp.mvp.IPresenter
import com.hazz.kotlinmvp.mvp.model.bean.TabInfoBean

/**
 * Created by zhangguanjun on 2018/2/23.
 */
interface HotTabContract{
    interface View:IBaseView{
        fun showTabs(tabInfoBean: TabInfoBean)

        /**
         * 显示错误的信息
         */
        fun showError(msg: String, errorCode: Int)
    }

    interface Presenter : IPresenter<View> {
        fun getTabInfo()
    }
}