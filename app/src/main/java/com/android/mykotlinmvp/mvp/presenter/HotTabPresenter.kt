package com.android.mykotlinmvp.mvp.presenter

import com.android.mykotlinmvp.mvp.BasePresenter
import com.android.mykotlinmvp.mvp.contract.HotTabContract
import com.android.mykotlinmvp.mvp.model.HotTabModel
import com.hazz.kotlinmvp.net.exception.ExceptionHandle

/**
 * Created by zhangguanjun on 2018/2/24.
 */
class HotTabPresenter:BasePresenter<HotTabContract.View>(), HotTabContract.Presenter{
    private val hotTabModel by lazy { HotTabModel() }

    override fun getTabInfo() {
        checkViewAttached()
        mRootView?.showLoading()
        hotTabModel.getHotTabInfo()
                .subscribe({
                    hotTabInfo ->
                    mRootView?.apply {
                        dismissLoading()
                        showTabs(hotTabInfo)
                    }
                },{
                    e ->
                    mRootView?.apply {
                        dismissLoading()
                        showError(ExceptionHandle.handleException(e),ExceptionHandle.errorCode)
                    }
                })
    }

}