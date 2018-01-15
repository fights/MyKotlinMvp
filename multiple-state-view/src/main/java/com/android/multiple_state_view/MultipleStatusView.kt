package com.android.multiple_state_view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout

/**
 * Created by Mr Zhang on 2017/12/24.
 */
class MultipleStatusView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : RelativeLayout(context,attrs,defStyleAttr) {

    companion object {
        public val STATUS_CONTENT = 0
        public val STATUS_EMPTY = 1
        public val STATUS_ERROR = 2
        public val STATUS_LOADING = 3
        public val STATUS_NO_NETWORK = 4
        public var mCurrentState = STATUS_CONTENT
        private val TAG = this::class.java.simpleName
        val DEFAULT_LAYOUT_PARAMS = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT)
    }

    private var mContext: Context? = null
    private var mEmptyViewId: Int? = null
    private var mErrorViewId: Int? = null
    private var mLoadingViewId: Int? = null
    private var mNoNetworkViewId: Int? = null
    private var mEmptyView: View? = null
    private var mErrorView: View? = null
    private var mLoadingView: View? = null
    private var mNoNetworkView: View? = null
    private var mOtherIds = ArrayList<Int>()


    constructor(context: Context, attrs: AttributeSet?):this(context, attrs,-1)
    constructor(context: Context):this(context,null)

    init {
        val at = context.obtainStyledAttributes(attrs, R.styleable.MultipleStatusView, defStyleAttr, 0)
        mEmptyViewId = at.getResourceId(R.styleable.MultipleStatusView_emptyVIew, R.layout.view_empty)
        mErrorViewId = at.getResourceId(R.styleable.MultipleStatusView_errorVIew, R.layout.view_error)
        mLoadingViewId = at.getResourceId(R.styleable.MultipleStatusView_loadingVIew, R.layout.view_loading)
        mNoNetworkViewId = at.getResourceId(R.styleable.MultipleStatusView_noNetworkVIew, R.layout.view_no_network)
        at.recycle()
        mContext = context
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        inflateViews()
    }

    private fun inflateViews() {
        mEmptyView = LayoutInflater.from(mContext).inflate(this.mEmptyViewId!!, null)
        mErrorView = LayoutInflater.from(mContext).inflate(this.mErrorViewId!!, null)
        mLoadingView = LayoutInflater.from(mContext).inflate(this.mLoadingViewId!!, null)
        mNoNetworkView = LayoutInflater.from(mContext).inflate(this.mNoNetworkViewId!!, null)

        addView(mEmptyView,0, DEFAULT_LAYOUT_PARAMS)
        addView(mErrorView,0, DEFAULT_LAYOUT_PARAMS)
        addView(mLoadingView,0, DEFAULT_LAYOUT_PARAMS)
        addView(mNoNetworkView,0, DEFAULT_LAYOUT_PARAMS)

        mOtherIds.add(mEmptyView?.id!!)
        mOtherIds.add(mErrorView?.id!!)
        mOtherIds.add(mLoadingView?.id!!)
        mOtherIds.add(mNoNetworkView?.id!!)
        showContent()
    }

    /**
     * 显示主内容, 隐藏几种状态view
     */
    public fun showContent() {
        mCurrentState = STATUS_CONTENT
        showSingleView(null)
    }

    public fun showEmpty(){
        mCurrentState = STATUS_EMPTY
        showSingleView(mEmptyView)
    }

    public fun showError(){
        mCurrentState = STATUS_ERROR
        showSingleView(mErrorView)
    }

    public fun showNoNetwork(){
        mCurrentState = STATUS_NO_NETWORK
        showSingleView(mNoNetworkView)
    }

    public fun showLoading(){
        mCurrentState = STATUS_LOADING
        showSingleView(mLoadingView)
    }

    private fun showSingleView(view: View?) {

        (0..childCount)
                .map { getChildAt(it) }
                .forEach {
                    if (view != null) {
                        if (it?.id == view.id) {
                            it.visibility = View.VISIBLE
                        }else{
                            it?.visibility = View.GONE
                        }
                    }else{
                        if (mOtherIds.contains(it?.id)) {
                            it?.visibility = View.GONE
                        }else{
                            it?.visibility = View.VISIBLE
                        }
                    }
                }

    }

    public fun setOnRetryClickListener(onRetryClickListener: OnClickListener){
        mEmptyView?.findViewById<View>(R.id.view_empty_retry)?.setOnClickListener(onRetryClickListener)
        mErrorView?.findViewById<View>(R.id.view_error_retry)?.setOnClickListener(onRetryClickListener)
        mNoNetworkView?.findViewById<View>(R.id.view_no_network_retry)?.setOnClickListener(onRetryClickListener)
    }
}