package com.android.mykotlinmvp.ui.fragment

import android.os.Bundle
import com.android.mykotlinmvp.R
import com.android.mykotlinmvp.ui.base.BaseFragment

/**
 * Created by zhangguanjun on 2018/1/27.
 */
class FollowFragment : BaseFragment() {

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

    override fun getLayoutId(): Int = R.layout.fragment_follow

    override fun loadData() {
    }

    override fun initListener() {
    }

    override fun initView() {
    }

}