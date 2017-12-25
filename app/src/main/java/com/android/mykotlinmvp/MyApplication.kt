package com.android.mykotlinmvp

import android.app.Application
import android.content.Context
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import kotlin.properties.Delegates

/**
 * Created by zhangguanjun on 2017/12/25.
 *
 */
class MyApplication: Application() {

    companion object {
        var context: Context by Delegates.notNull()
            private set

    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext

        //配置log日志配置
        initLogConfig()


    }

    /**
     * 设置在debug模式下在logcat中显示log，在release模式下将log保存在文件中
     */
    private fun initLogConfig() {
        val formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)
                .tag("MyTag")
                .methodCount(2)
                .build()

        Logger.addLogAdapter(object :AndroidLogAdapter(formatStrategy){
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })

        Logger.w("hahhahahhahahahhahahahahh--------------------")
    }
}