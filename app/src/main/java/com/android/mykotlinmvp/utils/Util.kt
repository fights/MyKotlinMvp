package com.android.mykotlinmvp.utils

/**
 * Created by zhangguanjun on 2018/1/15.
 */
object Util{

    /**
     * 将一个视频长度转换为 xx'xx'' 样式的时间格式
     */
    fun durationFormat(duration: Long): String {
        var minutes = duration / 60
        var seconds = duration % 60
        var strMinutes = minutes.toString()
        var strSeconds = seconds.toString()

        if (strMinutes.length < 2) {
            strMinutes = "0" + strMinutes
        }

        if (strSeconds.length < 2) {
            strSeconds = "0" + strSeconds
        }
        return "$strMinutes'$strSeconds''"
    }
}