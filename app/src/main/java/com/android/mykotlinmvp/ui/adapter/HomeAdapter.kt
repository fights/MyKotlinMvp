package com.android.mykotlinmvp.ui.adapter

import android.content.Context
import android.view.ViewGroup
import cn.bingoogolapple.bgabanner.BGABanner
import com.android.mykotlinmvp.R
import com.android.mykotlinmvp.view.recyclerview.BaseAdapter
import com.android.mykotlinmvp.view.recyclerview.ViewHolder
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


            }
            ITEM_TITLE ->{

            }
            ITEM_VIDEO ->{

            }
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