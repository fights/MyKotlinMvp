package com.android.mykotlinmvp.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import com.android.mykotlinmvp.R
import com.android.mykotlinmvp.mvp.contract.HotTabContract
import com.android.mykotlinmvp.mvp.presenter.HotTabPresenter
import com.android.mykotlinmvp.showToast
import com.android.mykotlinmvp.ui.adapter.BaseFragmentAdapter
import com.android.mykotlinmvp.ui.base.BaseFragment
import com.hazz.kotlinmvp.mvp.model.bean.TabInfoBean
import com.hazz.kotlinmvp.net.exception.ErrorStatus
import kotlinx.android.synthetic.main.fragment_hot.*

/**
 * Created by zhangguanjun on 2018/1/4.
 */
class HotFragment : BaseFragment(), HotTabContract.View {

    private val mPresenter by lazy { HotTabPresenter() }
    private var mRankFragments: ArrayList<Fragment> = ArrayList()
    private var mTitles: ArrayList<String> = ArrayList()

    override fun showLoading() {
        mMutipleStatusView?.showLoading()
    }

    override fun dismissLoading() {
        mMutipleStatusView?.showContent()
    }

    override fun showTabs(tabInfoBean: TabInfoBean) {
        //根据获取到的tab信息来展示
        initTabAndViewPager(tabInfoBean)
    }

    private fun initTabAndViewPager(tabInfoBean: TabInfoBean) {

        for (tab in tabInfoBean.tabInfo.tabList) {
            val rankFragment = RankFragment.getInstance(tab.apiUrl)
            mRankFragments.add(rankFragment)
            mTitles.add(tab.name)
        }

        fh_viewpager.adapter = BaseFragmentAdapter(childFragmentManager,mRankFragments,mTitles)
        fh_viewpager.offscreenPageLimit = mRankFragments.size
        fh_tab_layout.setupWithViewPager(fh_viewpager)
    }

    override fun showError(msg: String, errorCode: Int) {
        showToast(msg)

        when (errorCode) {
            ErrorStatus.NETWORK_ERROR -> mMutipleStatusView?.showNoNetwork()
            else -> mMutipleStatusView?.showError()
        }
    }

    private var mTitle: String? = null

    companion object {
        fun getInstance(title: String): HotFragment {
            val hotFragment = HotFragment()
            val bundle = Bundle()
            hotFragment.arguments = bundle
            hotFragment.mTitle = title
            return hotFragment
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_hot

    override fun loadData() {
        mPresenter.getTabInfo()
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