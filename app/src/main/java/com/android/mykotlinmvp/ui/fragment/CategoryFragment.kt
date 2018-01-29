package com.android.mykotlinmvp.ui.fragment

import android.os.Bundle
import android.util.Log
import com.android.mykotlinmvp.R
import com.android.mykotlinmvp.mvp.contract.CategoryContract
import com.android.mykotlinmvp.mvp.presenter.CategoryPresenter
import com.android.mykotlinmvp.ui.base.BaseFragment
import com.hazz.kotlinmvp.mvp.model.bean.CategoryBean

/**
 * Created by zhangguanjun on 2018/1/27.
 */
class CategoryFragment : BaseFragment(),CategoryContract.View {
    private val mPresenter by lazy { CategoryPresenter() }

    override fun showLoading() {

    }

    override fun dismissLoading() {

    }

    override fun showCategory(categoryList: ArrayList<CategoryBean>) {
      Log.e("data", categoryList.toString())
    }

    override fun showErrorMsg(msg: String, errorCode: Int) {

    }

    private var mTitle:String? = null

    companion object {
        fun getInstance(title: String): CategoryFragment {
            val categoryFragment = CategoryFragment()
            val bundle = Bundle()
            categoryFragment.arguments = bundle
            categoryFragment.mTitle = title
            return categoryFragment
        }
    }

    override fun getLayoutId(): Int = R.layout.layout_recycler_view

    override fun loadData() {
        mPresenter.getCategory()
    }

    override fun initListener() {

    }

    override fun initView() {
        mPresenter.attachView(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }
}