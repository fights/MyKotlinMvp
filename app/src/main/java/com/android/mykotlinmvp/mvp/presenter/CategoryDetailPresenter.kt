package com.android.mykotlinmvp.mvp.presenter

import com.android.mykotlinmvp.mvp.BasePresenter
import com.android.mykotlinmvp.mvp.contract.CategoryDetailContract
import com.android.mykotlinmvp.mvp.model.CategoryDetailModel
import com.hazz.kotlinmvp.net.exception.ExceptionHandle

/**
 * Created by zhangguanjun on 2018/2/9.
 */
class CategoryDetailPresenter: BasePresenter<CategoryDetailContract.View>(), CategoryDetailContract.Presenter{

    private val categoryDetailModel: CategoryDetailModel by lazy { CategoryDetailModel() }
    private var nextPageUrl: String? = null

    override fun getData(id: Long) {
        checkViewAttached()
        mRootView?.apply {
            showLoading()
            val disposable = categoryDetailModel.getData(id)
                    .subscribe({ issue ->
                        dismissLoading()
                        nextPageUrl = issue.nextPageUrl
                        showData(issue.itemList)
                    }, { e ->
                        dismissLoading()
                        showError(ExceptionHandle.handleException(e), ExceptionHandle.errorCode)
                    })

            addSubscription(disposable)
        }
    }

    override fun getMoreData() {
        nextPageUrl?.let {
            mRootView?.apply {
                showLoading()
                categoryDetailModel.getMoreData(nextPageUrl!!)
                        .subscribe({
                            issue ->
                            dismissLoading()
                            nextPageUrl = issue.nextPageUrl
                            showMoreData(issue.itemList)
                        },{
                            e ->
                            dismissLoading()
                            showError(ExceptionHandle.handleException(e), ExceptionHandle.errorCode)
                        })
            }
        }
    }

}