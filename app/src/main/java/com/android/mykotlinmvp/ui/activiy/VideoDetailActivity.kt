package com.android.mykotlinmvp.ui.activiy

import android.annotation.SuppressLint
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.view.ViewCompat
import android.transition.Transition
import com.android.mykotlinmvp.R
import com.android.mykotlinmvp.mvp.contract.VideoDetailContract
import com.android.mykotlinmvp.mvp.presenter.VideoDetailPresenter
import com.android.mykotlinmvp.ui.base.BaseActivity
import com.android.mykotlinmvp.utils.Constants
import com.android.mykotlinmvp.utils.SpUtil
import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import kotlinx.android.synthetic.main.activity_video_detail.*
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
/**
 * Created by zhangguanjun on 2018/1/17.
 */
class VideoDetailActivity : BaseActivity(),VideoDetailContract.View {

    companion object {
        val TRANSITION_NAME = "transition_name"
        val IS_TRANSITION = "is_transition"
        val VIDEO_DATA = "video_data"
    }

    private lateinit var mVideoData: HomeBean.Issue.Item
    private var mIsTransition = false

    private val mPresenter by lazy { VideoDetailPresenter() }
    private val mDateFormat by lazy { SimpleDateFormat("yyyyMMddHHmmss") }

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
        mVideoData = intent.getSerializableExtra(VIDEO_DATA) as HomeBean.Issue.Item
        mIsTransition = intent.getBooleanExtra(IS_TRANSITION, false)

        // 保存观看记录
        saveWatchHistory(mVideoData)
    }

    private fun saveWatchHistory(item: HomeBean.Issue.Item) {
        val historyMap = SpUtil.getAll(Constants.FILE_NAME_OF_WATCH_HISTORY)

        //在保存观看记录之前，先遍历之前观看记录，若发现有这条记录，则先删除在保存
        if (historyMap != null) {
            for ((key, _) in historyMap) {
                val historyItem = SpUtil.getObject(Constants.FILE_NAME_OF_WATCH_HISTORY, key) as HomeBean.Issue.Item
                if (historyItem == item) {
                    SpUtil.remove(Constants.FILE_NAME_OF_WATCH_HISTORY, key)
                    break
                }
            }
            SpUtil.putObject(Constants.FILE_NAME_OF_WATCH_HISTORY, mDateFormat.format(Date()),item)
        }
    }

    override fun getLayoutId(): Int = R.layout.activity_video_detail

    override fun initView() {
        mPresenter.attachView(this)

        // 初始化动画
        initTransitionAnim()

    }

    private fun initTransitionAnim() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP && mIsTransition) {
            postponeEnterTransition() //延迟转场动画
            //设置共享元素
            ViewCompat.setTransitionName(mVideoView, TRANSITION_NAME)
            setTransitionListener()
            startPostponedEnterTransition()//开始转场动画
        }else{
            loadVideoInfo()
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setTransitionListener() {
        val transition = window.sharedElementEnterTransition
        transition?.addListener(object : Transition.TransitionListener{
            override fun onTransitionEnd(transition: Transition?) {
                //动画结束时，加载视频
                loadVideoInfo()
                transition?.removeListener(this)
            }

            override fun onTransitionResume(transition: Transition?) {
            }

            override fun onTransitionPause(transition: Transition?) {
            }

            override fun onTransitionCancel(transition: Transition?) {
            }

            override fun onTransitionStart(transition: Transition?) {
            }

        })
    }

    private fun loadVideoInfo() {
//        mPresenter.loadVideoInfo(mVideoData)
    }

    override fun getData() {
    }

}