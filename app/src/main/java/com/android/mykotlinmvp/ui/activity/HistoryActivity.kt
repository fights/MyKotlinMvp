package com.android.mykotlinmvp.ui.activity

import android.support.v7.widget.LinearLayoutManager
import com.android.mykotlinmvp.R
import com.android.mykotlinmvp.mvp.contract.HistoryContract
import com.android.mykotlinmvp.mvp.presenter.HistoryPresenter
import com.android.mykotlinmvp.ui.adapter.WatchHistoryAdapter
import com.android.mykotlinmvp.ui.base.BaseActivity
import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import kotlinx.android.synthetic.main.activity_history.*

/**
 * Created by zhangguanjun on 2018/2/27.
 */
class HistoryActivity : BaseActivity(),HistoryContract.View {

    private val mPresenter by lazy { HistoryPresenter() }

    override fun showLoading() {
    }

    override fun dismissLoading() {
    }

    override fun showHistory(data: ArrayList<HomeBean.Issue.Item>) {
        val watchHistoryAdapter = WatchHistoryAdapter(this, data, R.layout.item_watch_history)
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        ah_recycler_view.layoutManager = linearLayoutManager
        ah_recycler_view.adapter = watchHistoryAdapter
    }

    override fun init() {
    }

    override fun getLayoutId(): Int = R.layout.activity_history

    override fun initView() {
        mPresenter.attachView(this)
        initToolbar()
    }

    private fun initToolbar() {
        ah_toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

    override fun getData() {
        mPresenter.loadHistoryData()
    }

}