package com.android.mykotlinmvp.ui.base

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.android.multiple_state_view.MultipleStatusView

/**
 * Created by zhangguanjun on 2017/12/26.
 */
abstract class BaseActivity : AppCompatActivity() {

    protected var mMultipleStatusView:MultipleStatusView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        init()
        initView()
        getData()
        initListener()
    }

    /**
     * 前期初始化操作
     */
    abstract fun init()

    /**
     * 获取布局id
     */
    abstract fun getLayoutId(): Int

    /**
     * 初始化view
     */
    abstract fun initView()

    /**
     * 请求数据
     */
    abstract fun getData()

    open fun initListener(){
        mMultipleStatusView?.setOnRetryClickListener(mRetryClickListener)
    }

    open val mRetryClickListener: View.OnClickListener = View.OnClickListener {
        getData()
    }

    /**
     * 打开软键盘
     */
    fun openKeyBord(editText: EditText, context: Context) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editText, InputMethodManager.RESULT_SHOWN)
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    /**
     * 关闭软键盘
     */
    fun closeKeyBord(editText: EditText, context: Context) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromInputMethod(editText.windowToken, 0)
    }
}