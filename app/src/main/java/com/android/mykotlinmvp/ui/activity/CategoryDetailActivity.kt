package com.android.mykotlinmvp.ui.activity

import android.annotation.SuppressLint
import android.graphics.Color
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.android.mykotlinmvp.R
import com.android.mykotlinmvp.mvp.contract.CategoryDetailContract
import com.android.mykotlinmvp.mvp.presenter.CategoryDetailPresenter
import com.android.mykotlinmvp.ui.adapter.CategoryDetailAdapter
import com.android.mykotlinmvp.ui.base.BaseActivity
import com.android.mykotlinmvp.view.glide.GlideApp
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.hazz.kotlinmvp.mvp.model.bean.CategoryBean
import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import com.hazz.kotlinmvp.net.exception.ErrorStatus
import kotlinx.android.synthetic.main.activity_category_detail.*

/**
 * Created by zhangguanjun on 2018/2/6.
 */
class CategoryDetailActivity : BaseActivity(),CategoryDetailContract.View {
    private val mPresenter: CategoryDetailPresenter by lazy { CategoryDetailPresenter() }

    private lateinit var mCategoryInfo: CategoryBean

    private var mData: ArrayList<HomeBean.Issue.Item> = ArrayList()

    private lateinit var mAdapter: CategoryDetailAdapter

    private lateinit var mLinearLayoutManager: LinearLayoutManager

    private var mOnLoading = false

    companion object {
        val CATEGORY_INFO = "category_info"
    }

    override fun showLoading() {
    }

    override fun dismissLoading() {
        mOnLoading = false
    }

    override fun showData(data: ArrayList<HomeBean.Issue.Item>) {
        mData = data
        mLinearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mAdapter = CategoryDetailAdapter(this, mData, R.layout.item_category_detail)
        mRecyclerView.layoutManager = mLinearLayoutManager
        mRecyclerView.adapter = mAdapter
    }

    override fun showMoreData(data: ArrayList<HomeBean.Issue.Item>) {
        mData.addAll(data)
        mAdapter.notifyItemRangeChanged(mData.size - data.size, data.size)
    }

    override fun showError(errorMsg: String, errorCode: Int) {
        when (errorCode) {
            ErrorStatus.NETWORK_ERROR -> mMultipleStatusView?.showNoNetwork()
            else -> mMultipleStatusView?.showError()
        }
    }

    override fun init() {
        //获取通过intent传递过来的类别信息
        mCategoryInfo = intent.getSerializableExtra(CATEGORY_INFO) as CategoryBean
    }

    override fun getLayoutId(): Int = R.layout.activity_category_detail

    override fun initView() {
        mPresenter.attachView(this)
        showHeader()
        mMultipleStatusView = multipleStatusView
    }

    override fun initListener() {
        super.initListener()
        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                val itemCount = linearLayoutManager.itemCount
                val firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition()
                val childCount = recyclerView.childCount

                if (childCount + firstVisibleItemPosition == itemCount && !mOnLoading) {
                    mOnLoading = true
                    //滑动到了底部
                    mPresenter.getMoreData()
                }

            }
        })
    }

    /**
     * 展示Toolbar header info
     */
    @SuppressLint("SetTextI18n")
    private fun showHeader() {
        val headerImage = mCategoryInfo.headerImage
        val name = mCategoryInfo.name
        val description = mCategoryInfo.description

        setSupportActionBar(tb_acd_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        tb_acd_toolbar.setNavigationOnClickListener { finish() }

        GlideApp.with(this)
                .load(headerImage)
                .centerCrop()
                .error(R.mipmap.ic_launcher)
                .transition(DrawableTransitionOptions().crossFade())
                .into(iv_acd_bg)

        tv_acd_desc.text = "#$description#"

        collapsing_toolbar_layout.title = name
        collapsing_toolbar_layout.setExpandedTitleColor(Color.WHITE) //设置展开时的title的颜色
        collapsing_toolbar_layout.setCollapsedTitleTextColor(Color.BLACK) // 设置折叠时候title的颜色

    }

    override fun getData() {
        mPresenter.getData(mCategoryInfo.id)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

}