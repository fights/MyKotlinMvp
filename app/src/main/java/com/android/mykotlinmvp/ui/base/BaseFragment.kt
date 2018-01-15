package com.android.mykotlinmvp.ui.base


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.multiple_state_view.MultipleStatusView
import com.orhanobut.logger.Logger

/**
 * Created by zhangguanjun on 2017/12/29.
 */
abstract class BaseFragment: Fragment(){
    protected var mRootView: View? = null
    private var mIsViewCreated = false
    private var mHasLoadData = false

    protected var mMutipleStatusView: MultipleStatusView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mRootView = inflater.inflate(getLayoutId(),null)
        return mRootView!!
    }

    abstract fun getLayoutId(): Int

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListener()
        mMutipleStatusView?.setOnRetryClickListener(mRetryClickListener)
        mIsViewCreated = true
        Logger.d("onViewCreated:  mIsVisibleToUser : $userVisibleHint , mHasLoadData: $mHasLoadData , mIsViewCreated: $mIsViewCreated")
        lazyLoadDataIfPrepared()
    }

    open var mRetryClickListener = View.OnClickListener { loadData() }

    private fun lazyLoadDataIfPrepared() {
        Logger.d("mIsVisibleToUser : $userVisibleHint , mHasLoadData: $mHasLoadData , mIsViewCreated: $mIsViewCreated")
        if (userVisibleHint && !mHasLoadData && mIsViewCreated) {
            loadData()
            mHasLoadData = true
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            lazyLoadDataIfPrepared()
        }
    }

    abstract fun loadData()

    abstract fun initListener()

    abstract fun initView()
}