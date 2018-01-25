package com.android.mykotlinmvp.view.recyclerview

import com.hazz.kotlinmvp.mvp.model.bean.HomeBean

/**
 * Created by zhangguanjun on 2018/1/24.
 */
interface OnRelativeVideoItemClickListener{
    fun onItemClick(videoData: HomeBean.Issue.Item)
}