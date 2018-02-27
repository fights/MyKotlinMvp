package com.android.mykotlinmvp.ui.fragment

import android.content.Intent
import android.os.Bundle
import com.android.mykotlinmvp.R
import com.android.mykotlinmvp.ui.activity.HistoryActivity
import com.android.mykotlinmvp.ui.base.BaseFragment
import com.android.mykotlinmvp.view.glide.GlideApp
import kotlinx.android.synthetic.main.fragment_mine.*

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
        tv_fm_history.setOnClickListener {
            //跳转到观看历史记录界面
            startActivity(Intent(activity, HistoryActivity::class.java))
        }
    }

    override fun initView() {
        GlideApp.with(this)
                .load(R.mipmap.default_avatar)
                .circleCrop()
                .into(iv_fm_header)
    }

}