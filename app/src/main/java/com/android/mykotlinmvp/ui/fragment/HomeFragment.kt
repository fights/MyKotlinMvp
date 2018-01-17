package com.android.mykotlinmvp.ui.fragment

import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.android.mykotlinmvp.R
import com.android.mykotlinmvp.mvp.contract.HomeContract
import com.android.mykotlinmvp.mvp.presenter.HomePresenter
import com.android.mykotlinmvp.ui.adapter.HomeAdapter
import com.android.mykotlinmvp.ui.base.BaseFragment
import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import com.hazz.kotlinmvp.net.exception.ErrorStatus
import com.orhanobut.logger.Logger
import com.scwang.smartrefresh.header.MaterialHeader
import kotlinx.android.synthetic.main.fragment_home.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by zhangguanjun on 2018/1/4.
 */
class HomeFragment : BaseFragment(), HomeContract.View {

    private val mHomePresenter: HomePresenter by lazy { HomePresenter() }
    private var mPageNum: Int = 1
    private var mIsRefresh = false
    private var mIsOnLoading = false
    private var mRefreshHeader: MaterialHeader? = null
    private var mHomeAdapter: HomeAdapter? = null
    private val mLinearLayoutManager by lazy { LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false) }
    private val mSimpleDateFormat by lazy {
        SimpleDateFormat("- MMM. dd, 'Brunch' -", Locale.ENGLISH)
    }

    override fun showHomeData(homeBean: HomeBean) {
        Logger.d(homeBean)
        mIsRefresh = false
        mHomeAdapter = HomeAdapter(activity!!, homeBean.issueList[0].itemList)
        mHomeAdapter?.setBannerSize(homeBean.issueList[0].count)
        mRecyclerView.layoutManager = mLinearLayoutManager
        mRecyclerView.adapter = mHomeAdapter
        mRecyclerView.itemAnimator = DefaultItemAnimator()
    }

    override fun showMoreData(itemList: ArrayList<HomeBean.Issue.Item>) {
        mIsOnLoading = false
        mHomeAdapter?.addMoreData(itemList)
    }

    override fun showError(msg: String, errorCode: Int) {
        mIsOnLoading = false
        mIsRefresh = false
        when (errorCode) {
            ErrorStatus.NETWORK_ERROR -> mMutipleStatusView?.showNoNetwork()
            else -> mMutipleStatusView?.showError()
        }
    }

    override fun showLoading() {

        //下拉刷新时不显示loading
        if (!mIsRefresh) {
            mMutipleStatusView?.showLoading()
        }
    }

    override fun dismissLoading() {
        if (mIsRefresh) {
            mIsRefresh = false
            mRefreshLayout.finishRefresh()
        }
        mIsOnLoading = false
        mMutipleStatusView?.showContent()
    }

    private var mTitle: String? = null

    companion object {
        fun getInstance(title: String): HomeFragment {
            val homeFragment = HomeFragment()
            val bundle = Bundle()
            homeFragment.arguments = bundle
            homeFragment.mTitle = title
            return homeFragment
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_home


    override fun loadData() {
        mHomePresenter.loadHomeData(mPageNum)
    }

    override fun initListener() {
        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                /*
                在这个方法中处理加载更多的逻辑
                当滑动到最后一个条目时则请求网络加载更多数据
                 */
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //参数>0表示判断是否能够向下滚动， <0表示判断是否能够向上滚动
//                    if (!mRecyclerView.canScrollVertically(1) && !mIsOnLoading) {
//                        mIsOnLoading = true
//                        mHomePresenter.loadMoreData()
//                    }

                    val linearLayoutManager = mRecyclerView.layoutManager as LinearLayoutManager
                    val childCount = mRecyclerView.childCount
                    val itemCount = linearLayoutManager.itemCount
                    val findFirstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition()
                    if (childCount + findFirstVisibleItemPosition == itemCount && !mIsOnLoading) {
                        mIsOnLoading = true
                        mHomePresenter.loadMoreData()
                    }
                }
            }

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                /*
                在这个方法中处理滑动过程中ToolBar的是否显示以及显示的内容
                 */
                val linearLayoutManager = mRecyclerView.layoutManager as LinearLayoutManager
                val position = linearLayoutManager.findFirstVisibleItemPosition()
                if (position == 0) {
                    mToolBar.setBackgroundColor(getColor(R.color.color_translucent))
                    mIvSearch.setImageResource(R.mipmap.ic_action_search_white)
                    mHeaderTitle.text = ""
                }else{
                    if (mHomeAdapter?.mData!!.size > 1) {
                        mToolBar.setBackgroundColor(getColor(R.color.color_title_bg))
                        mIvSearch.setImageResource(R.mipmap.ic_action_search_black)
                        val item = mHomeAdapter?.getItem(position)
                        if(item?.type == "textHeader"){
                            mHeaderTitle.text = item.data?.text
                        }else{
                            mHeaderTitle.text = mSimpleDateFormat.format(item?.data?.date)
                        }
                    }
                }
            }
        })
    }

    private fun getColor(color_translucent: Int): Int {
        return activity!!.resources.getColor(color_translucent)
    }

    override fun initView() {
        mHomePresenter.attachView(this)
        mRefreshLayout.setOnRefreshListener{
            mIsRefresh = true
            mHomePresenter.loadHomeData(mPageNum)
        }
        mRefreshHeader = mRefreshLayout.refreshHeader as MaterialHeader
        mRefreshHeader?.setShowBezierWave(true)
        mRefreshHeader?.setColorSchemeColors(R.color.color_light_black, R.color.color_title_bg)

        mMutipleStatusView = multpleStatusView
    }

    override fun onDestroy() {
        super.onDestroy()
        mHomePresenter.detachView()
    }

}