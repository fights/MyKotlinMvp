package com.android.mykotlinmvp.view.recyclerview

/**
 * Created by zhangguanjun on 2018/1/13.
 */
interface OnItemLongClickListener{
    fun onItemLongClick(obj: Any?, position: Int): Boolean
}