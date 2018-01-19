package com.android.mykotlinmvp.mvp.contract

import com.android.mykotlinmvp.mvp.IBaseView
import com.android.mykotlinmvp.mvp.IPresenter
import com.hazz.kotlinmvp.mvp.model.bean.HomeBean

/**
 * Created by zhangguanjun on 2018/1/18.
 */
interface VideoDetailContract{
    interface View: IBaseView{
        fun setVideo(url: String)

        fun setRelativeVideos(items: ArrayList<HomeBean.Issue.Item>)

        fun setListBackground(imgUrl: String)

        fun setVideoInfo(info: HomeBean.Issue.Item)

        fun setErrorMsg(errorMsg: String)
    }

    interface Presenter : IPresenter<View> {
        fun loadVideoInfo(item: HomeBean.Issue.Item)

        fun loadRelativeVideo(id: Long)
    }
}