package com.android.mykotlinmvp.ui.fragment

import android.os.Bundle
import com.android.mykotlinmvp.R
import com.android.mykotlinmvp.mvp.contract.HomeContract
import com.android.mykotlinmvp.mvp.presenter.HomePresenter
import com.android.mykotlinmvp.ui.base.BaseFragment
import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import com.orhanobut.logger.Logger
import com.scwang.smartrefresh.header.MaterialHeader
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * Created by zhangguanjun on 2018/1/4.
 */
class HomeFragment : BaseFragment(), HomeContract.View {

    private val mHomePresenter: HomePresenter by lazy { HomePresenter() }
    private var mPageNum: Int = 1
    private var mIsRefresh = false
    private var mRefreshHeader: MaterialHeader? = null

    override fun showHomeData(homeBean: HomeBean) {
        Logger.d(homeBean)
    }

    override fun showMoreData(itemList: ArrayList<HomeBean.Issue.Item>) {
    }

    override fun showError(msg: String, errorCode: Int) {
    }

    override fun showLoading() {
    }

    override fun dismissLoading() {
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