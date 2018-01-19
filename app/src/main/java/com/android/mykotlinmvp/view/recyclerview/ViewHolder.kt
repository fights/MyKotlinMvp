package com.android.mykotlinmvp.view.recyclerview

import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.View

@Suppress("UNCHECKED_CAST")
/**
 * Created by zhangguanjun on 2018/1/13.
 */
class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    //缓存已找的View，减少findViewById的次数
    private var mViewList: SparseArray<View> = SparseArray()

    fun <T : View> getView(viewId: Int): T {
        var view = mViewList.get(viewId)
        if (view == null) {
            view = itemView.findViewById(viewId)
            mViewList.put(viewId,view)
        }
        return view as T
    }

}