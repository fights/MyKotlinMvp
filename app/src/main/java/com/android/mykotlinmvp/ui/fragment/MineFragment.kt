package com.android.mykotlinmvp.ui.fragment

import android.os.Bundle
import com.android.mykotlinmvp.R
import com.android.mykotlinmvp.ui.base.BaseFragment

/**
 * Created by zhangguanjun on 2018/1/4.
 */
class MineFragment : BaseFragment() {
    private var mTitle: String? = null

    companion object {
        fun getInstance(title: String): MineFragment {
            val mineFragment = MineFragment()
            val bundle = Bundle()
            mineFragment.arguments = bundle
            mineFragment.mTitle = title
            return mineFragment
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_mine

    override fun loadData() {
    }

    override fun initListener() {
    }

    override fun initView() {
    }

}