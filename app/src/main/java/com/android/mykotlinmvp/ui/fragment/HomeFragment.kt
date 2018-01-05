package com.android.mykotlinmvp.ui.fragment

import android.os.Bundle
import com.android.mykotlinmvp.R
import com.android.mykotlinmvp.ui.base.BaseFragment

/**
 * Created by zhangguanjun on 2018/1/4.
 */
class HomeFragment : BaseFragment() {

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
    }

    override fun initListener() {
    }

    override fun initView() {
    }

}