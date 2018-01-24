package com.android.mykotlinmvp.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import com.android.mykotlinmvp.R
import com.android.mykotlinmvp.utils.Util
import com.android.mykotlinmvp.view.glide.GlideApp
import com.android.mykotlinmvp.view.recyclerview.BaseAdapter
import com.android.mykotlinmvp.view.recyclerview.MultipleType
import com.android.mykotlinmvp.view.recyclerview.ViewHolder
import com.hazz.kotlinmvp.mvp.model.bean.HomeBean

/**
 * Created by zhangguanjun on 2018/1/22.
 */
class VideoDetailAdapter(context: Context, var datas: ArrayList<HomeBean.Issue.Item>)
    : BaseAdapter<HomeBean.Issue.Item>(context, datas, object : MultipleType<HomeBean.Issue.Item>{
    override fun getLayoutId(item: HomeBean.Issue.Item, position: Int): Int {
        return when{
            position == 0 -> R.layout.item_video_detail_info
            datas[position].type == "textCard" -> R.layout.item_video_detail_textcard
            datas[position].type == "videoSmallCard" -> R.layout.item_video_detail_small_card
            else -> throw IllegalAccessException("Api 解析出错，出现其他类型")
        }
    }
})

{

    @SuppressLint("SetTextI18n")
    override fun bindData(holder: ViewHolder, data: HomeBean.Issue.Item, position: Int) {
        when{
            position == 0 -> {
                //视频信息
               holder.apply {
                   val tvTitle = getView<TextView>(R.id.tv_ivdi_title)
                   val tvCategoryTime = getView<TextView>(R.id.tv_ivdi_category_time)
                   val tvDesc = getView<TextView>(R.id.tv_ivdi_desc)
                   val tvLove = getView<TextView>(R.id.tv_ivdi_love)
                   val tvShare = getView<TextView>(R.id.tv_ivdi_share)
                   val tvComment = getView<TextView>(R.id.tv_ivdi_comment)
                   val tvOffline = getView<TextView>(R.id.tv_ivdi_offline)
                   val tvAuthorName = getView<TextView>(R.id.tv_ivdi_author_name)
                   val tvAuthorDesc = getView<TextView>(R.id.tv_ivdi_author_desc)
                   val ivAuthorHeader = getView<ImageView>(R.id.iv_ivdi_author_header)

                   tvTitle.text = data.data?.title
                   tvCategoryTime.text = "#${data.data?.category}/${Util.durationFormat(data.data?.duration!!)}"
                   tvDesc.text = data.data.description
                   tvLove.text = data.data.consumption.collectionCount.toString()
                   tvShare.text = data.data.consumption.shareCount.toString()
                   tvComment.text = data.data.consumption.replyCount.toString()
                   tvAuthorName.text = data.data.author.name
                   tvAuthorDesc.text = data.data.author.description
                   tvOffline.text = "缓存"
                   GlideApp.with(mContext)
                           .load(data.data.author.icon)
                           .placeholder(R.mipmap.default_avatar)
                           .error(R.mipmap.default_avatar)
                           .circleCrop()
                           .into(ivAuthorHeader)
               }
            }

            datas[position].type == "textCard" -> {
                //title信息
                holder.apply {
                    val tvText = getView<TextView>(R.id.tv_ivdtc_text)
                    tvText.text = data.data?.text
                }
            }
            datas[position].type == "videoSmallCard" -> {
                //相关视频条目
                holder.apply {
                    val ivFeed = getView<ImageView>(R.id.iv_ivdsc_feed)
                    val tvTitle = getView<TextView>(R.id.tv_ivdsc_title)
                    val tvCatgoryTime = getView<TextView>(R.id.tv_ivdsc_catgory_time)
                    tvTitle.text = data.data?.title
                    tvCatgoryTime.text = "#${data.data?.category}/${Util.durationFormat(data.data?.duration!!)}"
                    GlideApp.with(mContext)
                            .load(data.data.cover.feed)
                            .centerCrop()
                            .into(ivFeed)
                }
            }
        }
    }

}