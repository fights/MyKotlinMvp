package com.android.mykotlinmvp.ui.activiy

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.view.ViewCompat
import android.support.v7.widget.LinearLayoutManager
import android.transition.Transition
import android.view.View
import android.widget.ImageView
import com.android.mykotlinmvp.R
import com.android.mykotlinmvp.mvp.contract.VideoDetailContract
import com.android.mykotlinmvp.mvp.presenter.VideoDetailPresenter
import com.android.mykotlinmvp.ui.adapter.VideoDetailAdapter
import com.android.mykotlinmvp.ui.base.BaseActivity
import com.android.mykotlinmvp.utils.Constants
import com.android.mykotlinmvp.utils.SpUtil
import com.android.mykotlinmvp.view.VideoPlayerListener
import com.android.mykotlinmvp.view.glide.GlideApp
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer
import kotlinx.android.synthetic.main.activity_video_detail.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

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
    private var mOrientationUtils : OrientationUtils? = null
    private var mIsPlay = false
    private var mIsPause = false
    private var mDatas = ArrayList<HomeBean.Issue.Item>()
    private lateinit var mAdapter: VideoDetailAdapter

    override fun showLoading() {
    }

    override fun dismissLoading() {
    }

    override fun setVideo(url: String) {
        mVideoView.setUp(url,false,"")
        mVideoView.startPlayLogic()
    }

    override fun setRelativeVideos(items: ArrayList<HomeBean.Issue.Item>) {
        mDatas.addAll(items)
        mAdapter.notifyItemRangeChanged(1,items.size)
    }

    override fun setListBackground(imgUrl: String) {
        GlideApp.with(this)
                .load(imgUrl)
                .centerCrop()
                .transition(DrawableTransitionOptions().crossFade())
                .into(mListBackground)
    }

    override fun setVideoInfo(info: HomeBean.Issue.Item) {
        mDatas.clear()
        mDatas.add(info)
        mAdapter = VideoDetailAdapter(this, mDatas)
        mRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mRecyclerView.adapter = mAdapter
        mPresenter.loadRelativeVideo(info.data?.id!!)
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

        //初始化播放器
        initVideoPlayer()
    }


    private fun initVideoPlayer() {
        //视频封面
        val imageView = ImageView(this)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP

        GlideApp.with(this)
                .load(mVideoData.data?.cover)
                .into(imageView)

        //将title隐藏
        mVideoView.titleTextView.visibility = View.GONE

        mOrientationUtils = OrientationUtils(this, mVideoView)

        mOrientationUtils?.isEnable = false

        mVideoView.setIsTouchWiget(true)
        mVideoView.isRotateViewAuto = false
        mVideoView.isLockLand = false
        mVideoView.isShowFullAnimation = false
        mVideoView.isNeedLockFull = false
        mVideoView.seekRatio = 1F
        mVideoView.setStandardVideoAllCallBack(object : VideoPlayerListener() {
            override fun onPrepared(url: String?, vararg objects: Any?) {
                super.onPrepared(url, *objects)
                mOrientationUtils?.isEnable = true
                mIsPlay = true
            }

            override fun onQuitFullscreen(url: String?, vararg objects: Any?) {
                super.onQuitFullscreen(url, *objects)
                mOrientationUtils?.backToProtVideo()
            }
        })

        mVideoView.setIsTouchWiget(true)


        mVideoView.fullscreenButton.setOnClickListener {
            mOrientationUtils?.resolveByClick()
            mVideoView.startWindowFullscreen(VideoDetailActivity@this,true,true)
        }

        mVideoView.backButton.setOnClickListener{onBackPressed()}
    }

    override fun onBackPressed() {
        mOrientationUtils?.backToProtVideo()
        if (StandardGSYVideoPlayer.backFromWindowFull(this)) {
            return
        }
        super.onBackPressed()
    }

    override fun onPause() {
        getCuPlay().onVideoPause()
        super.onPause()
        mIsPause = true
    }

    override fun onResume() {
        getCuPlay().onVideoResume()
        super.onResume()
        mIsPause = false
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mIsPlay) {
            getCuPlay().release()
        }

        mOrientationUtils?.releaseListener()

        mPresenter.detachView()
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        if (mIsPlay && !mIsPause) {
            mVideoView.onConfigurationChanged(this,newConfig,mOrientationUtils)
        }
    }

    private fun getCuPlay(): GSYVideoPlayer{
        if (mVideoView.fullWindowPlayer != null) {
            return mVideoView.fullWindowPlayer
        }

        return mVideoView
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
        mPresenter.loadVideoInfo(mVideoData)
    }

    override fun getData() {
    }

}