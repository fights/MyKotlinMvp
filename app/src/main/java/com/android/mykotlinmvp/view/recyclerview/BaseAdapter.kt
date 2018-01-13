package com.android.mykotlinmvp.view.recyclerview

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

/**
 * Created by zhangguanjun on 2018/1/13.
 */
abstract class BaseAdapter<T>(var mContext: Context, var mData: ArrayList<T>, var mLayoutId: Int): RecyclerView.Adapter<ViewHolder>(){


    constructor(context: Context, data: ArrayList<T>, type: MultipleType<T>) : this(context, data, -1){
        this.mMultipleType = type
    }
    private var mMultipleType: MultipleType<T>? = null

    protected var mLayoutInflater: LayoutInflater? = null

    private var mOnItemClickListener: OnItemClickListener? = null
    private var mOnItemLongClickListener: OnItemLongClickListener? = null

    init {
        mLayoutInflater = LayoutInflater.from(mContext)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        if (mMultipleType != null) {

            // 当使用多布局的构造器时，这个viewType传递的是相应布局的resId
            mLayoutId = viewType
        }

        //创建ViewHolder
        val view = mLayoutInflater?.inflate(mLayoutId, parent, false)
        return ViewHolder(view!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //填充数据
        bindData(holder, mData[position],position)

        if (mOnItemClickListener != null) {
            holder.itemView?.setOnClickListener{ mOnItemClickListener!!.onItemClick(mData[position], position)}
        }

        if (mOnItemLongClickListener != null) {
            holder.itemView?.setOnLongClickListener {  mOnItemLongClickListener!!.onItemLongClick(mData[position], position) }
        }

    }

    abstract fun bindData(holder: ViewHolder, data: T, position: Int)

    override fun getItemCount(): Int = mData.size

    override fun getItemViewType(position: Int): Int {

        return mMultipleType?.getLayoutId(mData[position],position) ?: super.getItemViewType(position)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.mOnItemClickListener = listener
    }

    fun setOnItemLongClickListener(listener: OnItemLongClickListener) {
        this.mOnItemLongClickListener = listener
    }

    fun addMoreData(moreData: ArrayList<T>) {
        mData.addAll(moreData)
        notifyDataSetChanged()
    }

    open fun getItem(position: Int): T{
        return mData[position]
    }
}