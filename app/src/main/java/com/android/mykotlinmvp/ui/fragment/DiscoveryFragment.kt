package com.android.mykotlinmvp.ui.fragment

import android.os.Bundle
import com.android.mykotlinmvp.R
import com.android.mykotlinmvp.ui.base.BaseFragment

/**
 * Created by zhangguanjun on 2018/1/4.
 */
class DiscoveryFragment : BaseFragment() {

    private var mTitle: String? = null

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
    }

}