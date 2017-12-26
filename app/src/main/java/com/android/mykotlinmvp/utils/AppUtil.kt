package com.android.mykotlinmvp.utils

import android.content.Context

/**
 * Created by zhangguanjun on 2017/12/26.
 */
class AppUtil private constructor(){

    companion object {

        /**
         * 获取版本名称
         */
        fun getVersionName(context: Context): String {
            return context.packageManager.getPackageInfo(context.packageName, 0).versionName
        }

        /**
         * 获取版本名称
         */
        fun getVersionCode(context: Context): Int {
            return context.packageManager.getPackageInfo(context.packageName, 0).versionCode
        }

    }

}