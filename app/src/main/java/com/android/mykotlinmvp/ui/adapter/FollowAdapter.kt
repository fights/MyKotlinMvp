package com.android.mykotlinmvp.ui.adapter

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import android.widget.TextView
import com.android.mykotlinmvp.R
import com.android.mykotlinmvp.view.glide.GlideApp
import com.android.mykotlinmvp.view.recyclerview.BaseAdapter
import com.android.mykotlinmvp.view.recyclerview.ViewHolder
import com.hazz.kotlinmvp.mvp.model.bean.HomeBean

/**
 * Created by zhangguanjun on 2018/1/29.
 */
class FollowAdapter(var context: Context, var data: ArrayList<HomeBean.Issue.Item>, layoutId: Int) :
        BaseAdapter<HomeBean.Issue.Item>(context, data, layoutId) {

    override fun bindData(holder: ViewHolder, data: HomeBean.Issue.Item, position: Int) {
        holder.apply {
            val ivAuthorHeader = getView<ImageView>(R.id.iv_if_author_header)
            val tvAuthorDesc = getView<TextView>(R.id.tv_if_author_desc)
            val tvAuthorName = getView<TextView>(R.id.tv_if_author_name)
            val rvHorizontal = getView<RecyclerView>(R.id.rv_if_horizontal_recycler)

            GlideApp.with(mContext)
                    .load(data.data?.header?.icon)
                    .circleCrop()
                    .error(R.mipmap.default_avatar)
                    .placeholder(R.mipmap.default_avatar)
                    .into(ivAuthorHeader)

            tvAuthorDesc.text = data.data?.header?.description
            tvAuthorName.text = data.data?.header?.title

            //横向的RecyclerView
            rvHorizontal.layoutManager = LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false)
            rvHorizontal.adapter = FollowHorizontalAdapter(mContext, data.data?.itemList!!, R.layout.item_follow_horizontal)
        }
    }
}