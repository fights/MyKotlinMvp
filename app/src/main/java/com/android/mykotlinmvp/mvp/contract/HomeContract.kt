package com.android.mykotlinmvp.mvp.contract

import com.android.mykotlinmvp.mvp.IBaseView
import com.android.mykotlinmvp.mvp.IPresenter
import com.hazz.kotlinmvp.mvp.model.bean.HomeBean

/**
 * Created by zhangguanjun on 2018/1/11.
 */
interface HomeContract{
    interface View: IBaseView{
        /**
         * 设置第一次请求的数据
         */
        fun showHomeData(homeBean: HomeBean)

        /**
         * 加载更多的数据
         */
        fun showMoreData(itemList: ArrayList<HomeBean.Issue.Item>)

        /**
         * 显示错误的信息
         */
        fun showError(msg: String, errorCode: Int)
    }

    interface Presenter : IPresenter<View> {
        /**
         * 获取首页的数据
         */
        fun loadHomeData(num: Int)

        /**
         * 加载更多
         */
        fun loadMoreData()
    }
}