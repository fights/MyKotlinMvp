package com.android.mykotlinmvp.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import com.android.mykotlinmvp.R
import com.android.mykotlinmvp.ui.adapter.BaseFragmentAdapter
import com.android.mykotlinmvp.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_discovery.*

/**
 * Created by zhangguanjun on 2018/1/4.
 */
class DiscoveryFragment : BaseFragment() {

    private var mTitle: String? = null
    private var mTitles = arrayListOf("关注","分类")
    private val mFragments = ArrayList<Fragment>()

    companion object {
        fun getInstance(title: String): DiscoveryFragment {
            val discoveryFragment = DiscoveryFragment()
            val bundle = Bundle()
            discoveryFragment.arguments = bundle
            discoveryFragment.mTitle = title
            return discoveryFragment
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_discovery

    override fun loadData() {
    }

    override fun initListener() {
    }

    override fun initView() {
        mFragments.add(FollowFragment.getInstance(mTitles[0]))
        mFragments.add(CategoryFragment.getInstance(mTitles[1]))

        viewPager.adapter = BaseFragmentAdapter(childFragmentManager,mFragments,mTitles)
        tabLayout.setupWithViewPager(viewPager)

        mMutipleStatusView = multipleStatusView
    }

}