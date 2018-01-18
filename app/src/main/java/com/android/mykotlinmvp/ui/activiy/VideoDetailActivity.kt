package com.android.mykotlinmvp.ui.activiy

import android.support.v4.view.ViewCompat
import com.android.mykotlinmvp.R
import com.android.mykotlinmvp.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_video_detail.*

/**
 * Created by zhangguanjun on 2018/1/17.
 */
class VideoDetailActivity : BaseActivity() {

    companion object {
        val TRANSITION_NAME = "transition_name"
        val IS_TRANSITION = "is_transition"
        val VIDEO_DATA = "video_data"
    }

    override fun init() {
    }

    override fun getLayoutId(): Int = R.layout.activity_video_detail

    override fun initView() {
        ViewCompat.setTransitionName(tvVideoTitle,"haha")
    }

    override fun getData() {
    }

}