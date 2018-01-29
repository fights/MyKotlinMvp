package com.android.mykotlinmvp.mvp.contract

import com.android.mykotlinmvp.mvp.IBaseView
import com.android.mykotlinmvp.mvp.IPresenter
import com.hazz.kotlinmvp.mvp.model.bean.HomeBean

/**
 * Created by zhangguanjun on 2018/1/25.
 */
interface FollowContract{
    interface View: IBaseView{

        /**
         * 显示关注的话题
         */
        fun showFollowVideo(issue: HomeBean.Issue)

        /**
         * 显示错误信息
         */
        fun showErrorMsg(errorMsg: String, errorCode: Int)

        fun showMoreFollowVideo(issue: HomeBean.Issue)
    }

    interface Preference : IPresenter<View> {
        /**
         * 请求关注的视频信息
         */
        fun loadFollowVideo()

        /**
         * 加载更多
         */
        fun loadMoreData()
    }
}