package com.android.mykotlinmvp.mvp.presenter

import com.android.mykotlinmvp.mvp.BasePresenter
import com.android.mykotlinmvp.mvp.contract.HistoryContract
import com.android.mykotlinmvp.mvp.model.HistoryModel

/**
 * Created by zhangguanjun on 2018/2/28.
 */
class HistoryPresenter: BasePresenter<HistoryContract.View>(), HistoryContract.Presenter{

    private val historyModel by lazy { HistoryModel() }

    override fun loadHistoryData() {
        checkViewAttached()
        val list = historyModel.loadWatchHistory()
        mRootView?.showHistory(list)
    }

}