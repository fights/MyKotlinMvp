package com.android.mykotlinmvp.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.android.mykotlinmvp.R
import com.android.mykotlinmvp.mvp.contract.RankContract
import com.android.mykotlinmvp.mvp.presenter.RankPresenter
import com.android.mykotlinmvp.showToast
import com.android.mykotlinmvp.ui.adapter.CategoryDetailAdapter
import com.android.mykotlinmvp.ui.base.BaseFragment
import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import com.hazz.kotlinmvp.net.exception.ErrorStatus
import kotlinx.android.synthetic.main.layout_recycler_view.*

/**
 * Created by zhangguanjun on 2018/2/24.
 */
class RankFragment : BaseFragment(), RankContract.View {

    private val mPresenter by lazy { RankPresenter() }

    override fun showLoading() {
        mMutipleStatusView?.showLoading()
    }

    override fun dismissLoading() {
        mMutipleStatusView?.showContent()
    }

    override fun showRank(itemList: ArrayList<HomeBean.Issue.Item>) {

        //由于这里的条目布局和分类详情里面的一样，所以复用其适配器
        val categoryDetailAdapter = CategoryDetailAdapter(activity!!, itemList, R.layout.item_category_detail)
        val linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = categoryDetailAdapter
    }

    override fun showErrorMsg(errorMsg: String, errorCode: Int) {
        showToast(errorMsg)

        when (errorCode) {
            ErrorStatus.NETWORK_ERROR -> mMutipleStatusView?.showNoNetwork()
            else -> mMutipleStatusView?.showError()
        }
    }

    private lateinit var mDataUrl: String

    companion object {
        fun getInstance(dataUrl: String): RankFragment{
            val rankFragment = RankFragment()
            val bundle = Bundle()
            rankFragment.arguments = bundle
            rankFragment.mDataUrl = dataUrl
            return rankFragment
        }
    }

    override fun getLayoutId() = R.layout.layout_recycler_view

    override fun loadData() {
        mPresenter.getRankData(mDataUrl)
    }

    override fun initListener() {

    }

    override fun initView() {
        mPresenter.attachView(this)

        mMutipleStatusView = multipleStatusView
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

}