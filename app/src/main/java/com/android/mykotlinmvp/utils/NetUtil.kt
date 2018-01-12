package com.android.mykotlinmvp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.android.mykotlinmvp.MyApplication

/**
 * Created by zhangguanjun on 2018/1/10.
 */
object NetUtil{
    fun isNetConnected(): Boolean {
        val connectivityManager = MyApplication.context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = connectivityManager.activeNetworkInfo
        if (null != info && info.isConnected) {
            if (info.state == NetworkInfo.State.CONNECTED) {
                return true
            }
        }
        return false
    }
}