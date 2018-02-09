package com.android.mykotlinmvp.ui.activity

import com.android.mykotlinmvp.R
import com.android.mykotlinmvp.mvp.contract.CategoryDetailContract
import com.android.mykotlinmvp.mvp.presenter.CategoryDetailPresenter
import com.android.mykotlinmvp.ui.base.BaseActivity
import com.hazz.kotlinmvp.mvp.model.bean.CategoryBean
import com.hazz.kotlinmvp.mvp.model.bean.HomeBean

/**
 * Created by zhangguanjun on 2018/2/6.
 */
class CategoryDetailActivity : BaseActivity(),CategoryDetailContract.View {
    private val mPresenter: CategoryDetailPresenter by lazy { CategoryDetailPresenter() }

    private lateinit var mCategoryInfo: CategoryBean

    companion object {
        val CATEGORY_INFO = "category_info"
    }

    override fun showLoading() {
    }

    override fun dismissLoading() {
    }

    override fun showData(data: ArrayList<HomeBean.Issue.Item>) {

    }

    override fun showMoreData(data: ArrayList<HomeBean.Issue.Item>) {
    }

    override fun showError(errorMsg: String, errorCode: Int) {
    }

    override fun init() {
        //获取通过intent传递过来的类别信息
        mCategoryInfo = intent.getSerializableExtra(CATEGORY_INFO) as CategoryBean
    }

    override fun getLayoutId(): Int = R.layout.activity_category_detail

    override fun initView() {
        mPresenter.attachView(this)
        showHeader()
    }

    /**
     * 展示Toolbar header info
     */
    private fun showHeader() {
        val headerImage = mCategoryInfo.headerImage
        val name = mCategoryInfo.name
        val description = mCategoryInfo.description

    }

    override fun getData() {
        mPresenter.getData(mCategoryInfo.id)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

}