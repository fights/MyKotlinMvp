package com.android.mykotlinmvp.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import com.android.mykotlinmvp.R
import com.android.mykotlinmvp.view.glide.GlideApp
import com.android.mykotlinmvp.view.recyclerview.BaseAdapter
import com.android.mykotlinmvp.view.recyclerview.ViewHolder
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.hazz.kotlinmvp.mvp.model.bean.CategoryBean

/**
 * Created by zhangguanjun on 2018/2/3.
 */
class CategoryAdapter(context:Context, data: ArrayList<CategoryBean>, layoutId: Int): BaseAdapter<CategoryBean>(
        context, data, layoutId){

    @SuppressLint("SetTextI18n")
    override fun bindData(holder: ViewHolder, data: CategoryBean, position: Int) {
        holder.apply {
            val tvCategory = getView<TextView>(R.id.tv_ic_category)
            val ivCover = getView<ImageView>(R.id.iv_ic_cover)

            tvCategory.text = "#${data.name}"
            GlideApp.with(mContext)
                    .load(data.headerImage)
                    .centerCrop()
                    .error(R.mipmap.ic_launcher)
                    .transition(DrawableTransitionOptions().crossFade())
                    .into(ivCover)

        }
    }

}