package com.android.mykotlinmvp.mvp.presenter

import com.android.mykotlinmvp.mvp.BasePresenter
import com.android.mykotlinmvp.mvp.contract.CategoryContract
import com.android.mykotlinmvp.mvp.model.CategoryModel
import com.hazz.kotlinmvp.net.exception.ExceptionHandle

/**
 * Created by Mr Zhang on 2018/1/29.
 */
class CategoryPresenter : CategoryContract.Presentor, BasePresenter<CategoryContract.View>() {
    private val mCategoryModel by lazy { CategoryModel() }

    override fun getCategory() {
        checkViewAttached()
        mRootView?.apply {
            showLoading()
            mCategoryModel.getCategory()
                    .subscribe({
                        data ->
                        dismissLoading()
                        showCategory(data)
                    },{
                        it ->
                        dismissLoading()
                        showErrorMsg(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                    })
        }
    }

}