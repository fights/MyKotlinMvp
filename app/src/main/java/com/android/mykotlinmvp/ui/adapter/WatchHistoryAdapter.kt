package com.android.mykotlinmvp.ui.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.app.ActivityOptionsCompat
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.android.mykotlinmvp.R
import com.android.mykotlinmvp.ui.activity.VideoDetailActivity
import com.android.mykotlinmvp.utils.Util
import com.android.mykotlinmvp.view.glide.GlideApp
import com.android.mykotlinmvp.view.recyclerview.BaseAdapter
import com.android.mykotlinmvp.view.recyclerview.ViewHolder
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.hazz.kotlinmvp.mvp.model.bean.HomeBean

/**
 * Created by zhangguanjun on 2018/2/28.
 */
class WatchHistoryAdapter(context: Context, data: ArrayList<HomeBean.Issue.Item>, layoutId: Int) :
        BaseAdapter<HomeBean.Issue.Item>(context, data, layoutId) {

    @SuppressLint("SetTextI18n")
    override fun bindData(holder: ViewHolder, data: HomeBean.Issue.Item, position: Int) {
        holder.apply {
            val ivCover = getView<ImageView>(R.id.iv_iwh_cover)
            val tvCategoryDuration = getView<TextView>(R.id.tv_iwh_category_duration)
            val tvTitle = getView<TextView>(R.id.tv_iwh_title)
            val rlItem = getView<RelativeLayout>(R.id.rl_iwh_item)

            GlideApp.with(mContext)
                    .load(data.data?.cover?.feed)
                    .error(R.mipmap.ic_launcher)
                    .transition(DrawableTransitionOptions().crossFade())
                    .into(ivCover)

            tvCategoryDuration.text = "#${data.data?.category}/${Util.durationFormat(data.data?.duration!!)}"

            tvTitle.text = data.data.title

            rlItem.setOnClickListener {
                transferToVideoActivity(ivCover, data)
            }
        }
    }

    private fun transferToVideoActivity(view: View, data: HomeBean.Issue.Item) {
        val activity = mContext as Activity
        val intent = Intent(activity, VideoDetailActivity::class.java)
        intent.putExtra(VideoDetailActivity.VIDEO_DATA, data)
        intent.putExtra(VideoDetailActivity.IS_TRANSITION, true)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            val transitionAnimation = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, view, VideoDetailActivity.TRANSITION_NAME)
            activity.startActivity(intent,transitionAnimation.toBundle())
        }else{
            activity.startActivity(intent)
            activity.overridePendingTransition(R.anim.anim_activity_in, R.anim.anim_activity_out)
        }

    }
}