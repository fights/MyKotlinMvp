package com.android.mykotlinmvp.ui.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.android.mykotlinmvp.R
import com.android.mykotlinmvp.ui.activity.VideoDetailActivity
import com.android.mykotlinmvp.utils.Util
import com.android.mykotlinmvp.view.glide.GlideApp
import com.android.mykotlinmvp.view.recyclerview.BaseAdapter
import com.android.mykotlinmvp.view.recyclerview.ViewHolder
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.hazz.kotlinmvp.mvp.model.bean.HomeBean

/**
 * Created by zhangguanjun on 2018/2/9.
 */
class CategoryDetailAdapter(context: Context, data: ArrayList<HomeBean.Issue.Item>, layoutId: Int) :
        BaseAdapter<HomeBean.Issue.Item>(context, data, layoutId) {

    @SuppressLint("SetTextI18n")
    override fun bindData(holder: ViewHolder, data: HomeBean.Issue.Item, position: Int) {
        holder.apply {
            val ivFeed = getView<ImageView>(R.id.iv_icd_feed)
            val tvTitle = getView<TextView>(R.id.tv_icd_title)
            val tvCategoryTime = getView<TextView>(R.id.tv_icd_category_time)

            GlideApp.with(mContext)
                    .load(data.data?.cover?.feed)
                    .error(R.mipmap.ic_launcher)
                    .apply(RequestOptions().placeholder(R.drawable.placeholder_banner))
                    .centerCrop()
                    .transition(DrawableTransitionOptions().crossFade())
                    .into(ivFeed)

            tvTitle.text = data.data?.title
            tvCategoryTime.text = "#${data.data?.category}/${Util.durationFormat(data.data?.duration!!)}"

            ivFeed.setOnClickListener {
                //点击播放详情界面
                goToPlayerActivity(mContext as Activity, data, ivFeed)
            }
        }
    }

    fun goToPlayerActivity(activity: Activity, data: HomeBean.Issue.Item, view: View) {
        val intent = Intent(activity, VideoDetailActivity::class.java)
        intent.putExtra(VideoDetailActivity.IS_TRANSITION, true)
        intent.putExtra(VideoDetailActivity.VIDEO_DATA, data)

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            val pair = Pair(view,VideoDetailActivity.TRANSITION_NAME)
            val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, pair)
            activity.startActivity(intent,optionsCompat.toBundle())
        }else{
            activity.startActivity(intent)
            activity.overridePendingTransition(R.anim.anim_activity_in, R.anim.anim_activity_out)
        }
    }

}