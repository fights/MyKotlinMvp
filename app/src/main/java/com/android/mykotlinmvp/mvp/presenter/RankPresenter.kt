package com.android.mykotlinmvp.mvp.presenter

import com.android.mykotlinmvp.mvp.BasePresenter
import com.android.mykotlinmvp.mvp.contract.RankContract
import com.android.mykotlinmvp.mvp.model.RankModel
import com.hazz.kotlinmvp.net.exception.ExceptionHandle

/**
 * Created by zhangguanjun on 2018/2/24.
 */
class RankPresenter : RankContract.Presenter, BasePresenter<RankContract.View>() {

    private val rankModel by lazy { RankModel() }

    override fun getRankData(url: String) {
        checkViewAttached()
        mRootView?.showLoading()
        rankModel.getRankData(url)
                .subscribe({
                    issue ->
                    mRootView?.apply {
                        dismissLoading()
                        showRank(issue.itemList)
                    }
                }, {
                    e ->
                    mRootView?.apply {
                        dismissLoading()
                        showErrorMsg(ExceptionHandle.handleException(e), ExceptionHandle.errorCode)
                    }
                })
    }

}