package com.android.mykotlinmvp.ui.fragment

import android.os.Bundle
import com.android.mykotlinmvp.R
import com.android.mykotlinmvp.ui.base.BaseFragment

/**
 * Created by zhangguanjun on 2018/1/4.
 */
class HotFragment : BaseFragment() {

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
    }

    override fun initListener() {
    }

    override fun initView() {
    }

}