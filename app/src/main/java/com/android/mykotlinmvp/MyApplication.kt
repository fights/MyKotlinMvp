package com.android.mykotlinmvp

import android.app.Application
import android.content.Context
import android.os.Environment
import android.os.HandlerThread
import com.android.mykotlinmvp.logger.CustomLogSaveHandler
import com.orhanobut.logger.*
import com.tencent.bugly.crashreport.CrashReport
import me.weyye.hipermission.HiPermission
import me.weyye.hipermission.PermissionCallback
import me.weyye.hipermission.PermissionItem
import java.io.File
import kotlin.properties.Delegates

/**
 * Created by zhangguanjun on 2017/12/25.
 *
 */
class MyApplication: Application() {

    companion object {
        var context: Context by Delegates.notNull()
            private set
        val MAX_BYTES: Int = 500*1024
    }

    var isDebugMode: Boolean = BuildConfig.DEBUG

    override fun onCreate() {
        super.onCreate()
        context = applicationContext

        //配置log日志配置
        initLogConfig()

        initTencentBugly()
    }

    private fun initTencentBugly() {

        CrashReport.initCrashReport(applicationContext,"c464352b31",isDebugMode)
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
                return isDebugMode
            }
        })

        Logger.d("set logcat log out")

        HiPermission.create(this)
                .permissions(arrayListOf<PermissionItem>(PermissionItem(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)))
                .msg(resources.getString(R.string.save_log_permission_tips))
                .checkMutiPermission(object : PermissionCallback{
                    override fun onFinish() {

                        //当有读写sd卡权限时， 就配置日志保存
                        val diskPath = Environment.getExternalStorageDirectory().absolutePath
                        val folder: String = diskPath + File.separatorChar + packageName + File.separatorChar + "logger"
                        val handlerThread = HandlerThread("logSave")
                        handlerThread.start()
                        val customLogSaveHandler = CustomLogSaveHandler(handlerThread.looper, folder, MAX_BYTES)
                        val diskLogStrategy = DiskLogStrategy(customLogSaveHandler)
                        val csvFormatStrategy = CsvFormatStrategy.newBuilder()
                                .logStrategy(diskLogStrategy)
                                .tag("MyTag")
                                .build()
                        Logger.addLogAdapter(DiskLogAdapter(csvFormatStrategy))

                        Logger.d("set log save to sd card")
                    }

                    override fun onDeny(permission: String?, position: Int) {
                        Logger.e("onDeny " + permission)
                    }

                    override fun onGuarantee(permission: String?, position: Int) {
                        Logger.e("onGuarantee " + permission)
                    }

                    override fun onClose() {
                        Logger.e("onClose ")
                    }
                })
    }
}