package com.android.mykotlinmvp.mvp.model

import com.android.mykotlinmvp.utils.Constants
import com.android.mykotlinmvp.utils.SpUtil
import com.hazz.kotlinmvp.mvp.model.bean.HomeBean

/**
 * Created by zhangguanjun on 2018/2/28.
 */
class HistoryModel{

    companion object {
        val MAX_COUNT = 20
    }

    fun loadWatchHistory(): ArrayList<HomeBean.Issue.Item>{
        val allWatchHistory = SpUtil.getAll(Constants.FILE_NAME_OF_WATCH_HISTORY)
        val historyList = ArrayList<HomeBean.Issue.Item>()
        var tempNum = 0
        if (allWatchHistory != null) {
            for ((key, _) in allWatchHistory) {
                if (tempNum == MAX_COUNT) {
                    break
                }
                val data = SpUtil.getObject(Constants.FILE_NAME_OF_WATCH_HISTORY, key) as HomeBean.Issue.Item
                historyList.add(data)
                tempNum++

            }
        }
        return historyList
    }
}