package com.android.mykotlinmvp.ui.activity

import com.android.mykotlinmvp.R
import com.android.mykotlinmvp.ui.base.BaseActivity

/**
 * Created by zhangguanjun on 2018/2/6.
 */
class CategoryDetailActivity : BaseActivity() {
    private var mCategoryId : Int = 0

    companion object {
        val CATEGORY_ID = "category_id"
    }

    override fun init() {
    }

    override fun getLayoutId(): Int = R.layout.activity_category_detail

    override fun initView() {
    }

    override fun getData() {
    }

}