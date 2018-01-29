package com.android.mykotlinmvp.mvp.presenter

import com.android.mykotlinmvp.mvp.BasePresenter
import com.android.mykotlinmvp.mvp.contract.FollowContract
import com.android.mykotlinmvp.mvp.model.FollowModel
import com.hazz.kotlinmvp.net.exception.ExceptionHandle

/**
 * Created by zhangguanjun on 2018/1/25.
 */
class FollowPresenter : FollowContract.Preference, BasePresenter<FollowContract.View>() {
    private val followModel by lazy { FollowModel() }
    private var nextPageUrl: String? = null

    override fun loadFollowVideo() {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = followModel.loadFollowVideos()
                .subscribe({ issue ->
                    mRootView?.apply {
                        dismissLoading()
                        nextPageUrl = issue.nextPageUrl
                        showFollowVideo(issue)
                    }
                }, { it ->
                    mRootView?.apply {
                        dismissLoading()
                        showErrorMsg(ExceptionHandle.handleException(it))
                    }
                })

        addSubscription(disposable)
    }

    override fun loadMoreData() {

        checkViewAttached()
        nextPageUrl?.let {
            mRootView?.showLoading()
            followModel.loadMoreFollowVideos(it)
                    .subscribe({
                        issue ->
                        mRootView?.apply {
                            dismissLoading()
                            nextPageUrl = issue.nextPageUrl
                            showMoreFollowVideo(issue)
                        }
                    },{
                        it ->
                        mRootView?.apply {
                            dismissLoading()
                            showErrorMsg(ExceptionHandle.handleException(it))
                        }
                    })
        }

    }

}