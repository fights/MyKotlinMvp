package com.android.mykotlinmvp.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.android.mykotlinmvp.R
import com.android.mykotlinmvp.mvp.contract.FollowContract
import com.android.mykotlinmvp.mvp.presenter.FollowPresenter
import com.android.mykotlinmvp.showToast
import com.android.mykotlinmvp.ui.adapter.FollowAdapter
import com.android.mykotlinmvp.ui.base.BaseFragment
import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import com.hazz.kotlinmvp.net.exception.ErrorStatus
import kotlinx.android.synthetic.main.layout_recycler_view.*

/**
 * Created by zhangguanjun on 2018/1/27.
 */
class FollowFragment : BaseFragment(), FollowContract.View{
    private val mPresenter : FollowPresenter by lazy { FollowPresenter() }
    private lateinit var mData: ArrayList<HomeBean.Issue.Item>
    private lateinit var mAdapter: FollowAdapter
    private var mIsOnLoading = false

    override fun showLoading() {
    }

    override fun dismissLoading() {
        mIsOnLoading = false
    }

    override fun showFollowVideo(issue: HomeBean.Issue) {
        mData = issue.itemList
        recyclerView.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        mAdapter = FollowAdapter(activity!!, mData, R.layout.item_follow)
        recyclerView.adapter = mAdapter
    }

    override fun showErrorMsg(errorMsg: String, errorCode: Int) {
        mIsOnLoading = false
        showToast(errorMsg)

        when (errorCode) {
            ErrorStatus.NETWORK_ERROR -> mMutipleStatusView?.showNoNetwork()
            else -> mMutipleStatusView?.showError()
        }
    }

    override fun showMoreFollowVideo(issue: HomeBean.Issue) {
        mAdapter.addMoreData(issue.itemList)
    }

    private var mTitle:String? = null

    companion object {
        fun getInstance(title: String): FollowFragment{
            val followFragment = FollowFragment()
            val bundle = Bundle()
            followFragment.arguments = bundle
            followFragment.mTitle = title
            return followFragment
        }
    }

    override fun getLayoutId(): Int = R.layout.layout_recycler_view

    override fun loadData() {
        mPresenter.loadFollowVideo()
    }

    override fun initListener() {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val layoutManager = recyclerView!!.layoutManager as LinearLayoutManager
                    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                    val itemCount = layoutManager.itemCount
                    val childCount = recyclerView.childCount
                    if ((itemCount == firstVisibleItemPosition + childCount) && !mIsOnLoading) {
                        mIsOnLoading = true
                        mPresenter.loadMoreData()
                    }
                }
            }
        })
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