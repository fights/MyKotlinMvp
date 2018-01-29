package com.android.mykotlinmvp.mvp.contract

import com.android.mykotlinmvp.mvp.IBaseView
import com.android.mykotlinmvp.mvp.IPresenter
import com.hazz.kotlinmvp.mvp.model.bean.CategoryBean

/**
 * Created by Mr Zhang on 2018/1/29.
 */
interface CategoryContract{
    interface View: IBaseView{
        fun showCategory(categoryList: ArrayList<CategoryBean>)
        fun showErrorMsg(msg: String, errorCode: Int)
    }

    interface Presentor : IPresenter<View> {
        fun getCategory()
    }
}