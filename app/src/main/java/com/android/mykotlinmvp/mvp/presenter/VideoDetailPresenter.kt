package com.android.mykotlinmvp.mvp.presenter

import com.android.mykotlinmvp.mvp.BasePresenter
import com.android.mykotlinmvp.mvp.contract.VideoDetailContract
import com.android.mykotlinmvp.mvp.model.VideoDetailModel
import com.android.mykotlinmvp.utils.NetUtil
import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import com.hazz.kotlinmvp.net.exception.ExceptionHandle

/**
 * Created by zhangguanjun on 2018/1/19.
 */
class VideoDetailPresenter : VideoDetailContract.Presenter, BasePresenter<VideoDetailContract.View>() {
    private val videoDetailModel by lazy { VideoDetailModel() }

    override fun loadVideoInfo(item: HomeBean.Issue.Item) {
        val playInfo = item.data?.playInfo
        checkViewAttached()
        val isWifi = NetUtil.isWifi()
        playInfo?.let {
            if (it.size > 1) {
                //当playInfo的长度大于一时， 则可能包含高清，标清视频信息
                when{
                    isWifi -> {
                        //WiFi情况下播放高清视频
                        for (i in it) {
                            if (i.name == "高清") {
                                mRootView?.setVideo(i.url)
                                break
                            }
                        }
                    }
                    else -> {
                        //播放标清
                        for (i in it) {
                            if (i.name == "标清") {
                                mRootView?.setVideo(i.url)
                                break
                            }
                        }
                    }
                }
            }else{
                //当没有时， 就播放默认的url地址的视频
                mRootView?.setVideo(item.data?.playUrl)
            }
        }

        // 设置相关视频列表的背景
        val blurredBgUrl = item.data?.cover?.blurred
        blurredBgUrl?.let { mRootView?.setListBackground(blurredBgUrl) }

        mRootView?.setVideoInfo(item)
    }

    override fun loadRelativeVideo(id: Long) {
        val disposable = id.let {
            mRootView?.showLoading()
            videoDetailModel.getRelativeVideos(id)
                    .subscribe({ issue ->
                        mRootView?.apply {
                            dismissLoading()
                            setRelativeVideos(issue.itemList)
                        }
                    }, {  //出现异常
                        mRootView?.apply {
                            dismissLoading()
                            setErrorMsg(ExceptionHandle.handleException(it))
                        }
                    })
        }

        addSubscription(disposable)
    }

}