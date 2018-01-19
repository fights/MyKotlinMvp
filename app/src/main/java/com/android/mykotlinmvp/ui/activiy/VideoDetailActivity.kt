package com.android.mykotlinmvp.ui.activiy

import android.support.v4.view.ViewCompat
import com.android.mykotlinmvp.R
import com.android.mykotlinmvp.mvp.contract.VideoDetailContract
import com.android.mykotlinmvp.mvp.presenter.VideoDetailPresenter
import com.android.mykotlinmvp.ui.base.BaseActivity
import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import kotlinx.android.synthetic.main.activity_video_detail.*

/**
 * Created by zhangguanjun on 2018/1/17.
 */
class VideoDetailActivity : BaseActivity(),VideoDetailContract.View {

    companion object {
        val TRANSITION_NAME = "transition_name"
        val IS_TRANSITION = "is_transition"
        val VIDEO_DATA = "video_data"
    }

    private val mPresenter by lazy { VideoDetailPresenter() }

    override fun showLoading() {
    }

    override fun dismissLoading() {
    }

    override fun setVideo(url: String) {
    }

    override fun setRelativeVideos(items: ArrayList<HomeBean.Issue.Item>) {
    }

    override fun setListBackground(imgUrl: String) {
    }

    override fun setVideoInfo(info: HomeBean.Issue.Item) {
    }

    override fun setErrorMsg(errorMsg: String) {
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