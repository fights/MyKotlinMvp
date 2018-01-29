package com.android.mykotlinmvp.ui.fragment

import android.os.Bundle
import com.android.mykotlinmvp.R
import com.android.mykotlinmvp.ui.base.BaseFragment

/**
 * Created by zhangguanjun on 2018/1/27.
 */
class CategoryFragment : BaseFragment() {

    private var mTitle:String? = null

    companion object {
        fun getInstance(title: String): CategoryFragment {
            val categoryFragment = CategoryFragment()
            val bundle = Bundle()
            categoryFragment.arguments = bundle
            categoryFragment.mTitle = title
            return categoryFragment
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_category

    override fun loadData() {
    }

    override fun initListener() {
    }

    override fun initView() {
    }

}