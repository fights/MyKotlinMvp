package com.android.mykotlinmvp.ui.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import cn.bingoogolapple.bgabanner.BGABanner
import com.android.mykotlinmvp.R
import com.android.mykotlinmvp.ui.activity.VideoDetailActivity
import com.android.mykotlinmvp.view.glide.GlideApp
import com.android.mykotlinmvp.view.recyclerview.BaseAdapter
import com.android.mykotlinmvp.view.recyclerview.ViewHolder
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import io.reactivex.Observable

/**
 * Created by zhangguanjun on 2018/1/13.
 */
class HomeAdapter(var context: Context, private var mItemList: ArrayList<HomeBean.Issue.Item>) :
        BaseAdapter<HomeBean.Issue.Item>(context, mItemList, -1) {

    companion object {
        val ITEM_BANNER = 1
        val ITEM_TITLE = 2
        val ITEM_VIDEO = 3
    }

    private var mBannerSize: Int = 0

    fun setBannerSize(bannerSize: Int) {
        this.mBannerSize = bannerSize
    }

    override fun bindData(holder: ViewHolder, data: HomeBean.Issue.Item, position: Int) {
        when (getItemViewType(position)) {
            ITEM_BANNER ->{ //填充banner数据
                val bgaBanner = holder.getView<BGABanner>(R.id.banner)
                // 获取mData的前mBannerSize数据作为banner的数据
                val bannerItemData: ArrayList<HomeBean.Issue.Item> = mData.take(mBannerSize).toCollection(ArrayList())
                val bannerTitleList = ArrayList<String>()
                val bannerImgList = ArrayList<String>()

                Observable.fromIterable(bannerItemData)
                        .subscribe {
                            item: HomeBean.Issue.Item ->
                            bannerImgList.add(item.data?.cover?.feed!!)
                            bannerTitleList.add(item.data.title)
                        }

                bgaBanner.apply{
                    setAutoPlayAble(mBannerSize > 1)
                    setAdapter(object : BGABanner.Adapter<ImageView,String>{
                        override fun fillBannerItem(banner: BGABanner?, itemView: ImageView?, model: String?, position: Int) {
                            GlideApp.with(mContext)
                                    .load(model)
                                    .transition(DrawableTransitionOptions().crossFade())
                                    .placeholder(R.mipmap.ic_launcher)
                                    .into(itemView)
                        }
                    })

                    setData(bannerImgList,bannerTitleList)

                    setDelegate{_, itemView, _, position ->
                        goToVideoDetailActivity(mContext as Activity, itemView , mData[position])
                    }
                }
            }
            ITEM_TITLE ->{
                holder.getView<TextView>(R.id.tv_home_header).text = mData[position + mBannerSize -1]
                        .data?.text ?: ""
            }
            ITEM_VIDEO ->{
                dealVideoItemData(holder, mData[position + mBannerSize -1])
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun dealVideoItemData(holder: ViewHolder, item: HomeBean.Issue.Item) {
        holder.apply {
            val ivVideo = getView<ImageView>(R.id.iv_ihv_video_img)
            val ivAuthor = getView<ImageView>(R.id.iv_ihv_author)
            val tvTitle = getView<TextView>(R.id.tv_ihv_title)
            val tvTag = getView<TextView>(R.id.tv_ihv_tag)
            val tvCategory = getView<TextView>(R.id.tv_ihv_category)

            //视频图片
            GlideApp.with(mContext)
                    .load(item.data?.cover?.feed)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .transition(DrawableTransitionOptions().crossFade())
                    .into(ivVideo)

            val authorIcon = item.data?.author?.icon ?: item.data?.provider?.icon

            // 作者头像
            GlideApp.with(mContext)
                    .load(authorIcon)
                    .placeholder(R.mipmap.default_avatar)
                    .error(R.mipmap.default_avatar)
                    .circleCrop()
                    .transition(DrawableTransitionOptions().crossFade())
                    .into(ivAuthor)

            // 标题
            tvTitle.text = item.data?.title ?: ""

            //Category
            tvCategory.text = "#${item.data?.category}"

            //tag
            var tagText = "#"
            item.data?.tags?.take(4)?.forEach {
                tagText += (it.name + "/")
            }

            var videoDuration = com.android.mykotlinmvp.utils.Util.durationFormat(item.data?.duration!!)
            tvTag.text = tagText + videoDuration

            ivVideo.setOnClickListener {
                goToVideoDetailActivity(mContext as Activity, ivVideo, item)
            }
        }
    }

    private fun goToVideoDetailActivity(activity: Activity, itemView: View?, item: HomeBean.Issue.Item) {
        val intent = Intent(activity, VideoDetailActivity::class.java)
        intent.putExtra(VideoDetailActivity.VIDEO_DATA,item)
        intent.putExtra(VideoDetailActivity.IS_TRANSITION,true)
        //当SDK版本大于21时，支持转场动画
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            val pair = Pair(itemView,VideoDetailActivity.TRANSITION_NAME)
            val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, pair)
            activity.startActivity(intent,optionsCompat.toBundle())
        }else{
            activity.startActivity(intent)
            activity.overridePendingTransition(R.anim.anim_activity_in,R.anim.anim_activity_out)
        }
    }

    override fun getItemCount(): Int {
        /*
        由于banner的数据也保存在数据中，并且将整个banner作为第一个条目，因此要重写这个方法，
        单独给出RecyclerView条目个数的计算方式。
         */
        return when{
            mData.size > mBannerSize -> mData.size - mBannerSize + 1
            mData.isEmpty() -> 0
            else -> 1
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when{
            position == 0 -> ITEM_BANNER
            mData[position + mBannerSize - 1].type == "textHeader" -> ITEM_TITLE
            else -> ITEM_VIDEO
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return when(viewType){
            ITEM_BANNER -> ViewHolder(mLayoutInflater!!.inflate(R.layout.item_home_banner,parent, false))
            ITEM_TITLE -> ViewHolder(mLayoutInflater!!.inflate(R.layout.item_home_title,parent, false))
            else -> ViewHolder(mLayoutInflater!!.inflate(R.layout.item_home_video,parent, false))
        }
    }

    override fun getItem(position: Int): HomeBean.Issue.Item {
        return mData[position + mBannerSize - 1]
    }
}