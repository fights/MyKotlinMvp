package com.android.mykotlinmvp

import android.app.Application
import android.content.Context
import com.android.mykotlinmvp.utils.Constants
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import com.tencent.bugly.crashreport.CrashReport
import kotlin.properties.Delegates

/**
 * Created by zhangguanjun on 2017/12/25.
 *
 */
class MyApplication: Application() {

    private var refWatcher: RefWatcher by Delegates.notNull()

    companion object {
        var context: Context by Delegates.notNull()
            private set
        val MAX_BYTES: Int = 500*1024

        fun getRefWatcher(): RefWatcher {
            val myApplication = context as MyApplication
            return myApplication.refWatcher
        }
    }

    var isDebugMode: Boolean = BuildConfig.DEBUG

    override fun onCreate() {
        super.onCreate()
        context = applicationContext

        //配置log日志配置
        initLogConfig()

        //配置腾讯bugly
        initTencentBugly()

        //配置LeakCanary
        refWatcher = initLeakCanary()
    }

    private fun initLeakCanary(): RefWatcher {
        return if(LeakCanary.isInAnalyzerProcess(context))
            RefWatcher.DISABLED
        else
            LeakCanary.install(this)
    }

    private fun initTencentBugly() {

        CrashReport.initCrashReport(applicationContext,Constants.TENCENT_BUGLY_APPID,isDebugMode)
    }

    /**
     * 设置在debug模式下在logcat中显示log，在release模式下将log保存在文件中
     */
    private fun initLogConfig() {
//        val formatStrategy = PrettyFormatStrategy.newBuilder()
//                .showThreadInfo(false)
//                .tag("MyTag")
//                .methodCount(2)
//                .build()
//
//        Logger.addLogAdapter(object :AndroidLogAdapter(formatStrategy){
//            override fun isLoggable(priority: Int, tag: String?): Boolean {
//                return true
//            }
//        })

        val formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // 隐藏线程信息 默认：显示
                .methodCount(0)         // 决定打印多少行（每一行代表一个方法）默认：2
                .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
                .tag("hao_zz")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build()
        Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })

        Logger.d("set logcat log out")

//        HiPermission.create(this)
//                .permissions(arrayListOf<PermissionItem>(PermissionItem(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)))
//                .msg(resources.getString(R.string.save_log_permission_tips))
//                .checkMutiPermission(object : PermissionCallback{
//                    override fun onFinish() {
//
//                        //当有读写sd卡权限时， 就配置日志保存
//                        val diskPath = Environment.getExternalStorageDirectory().absolutePath
//                        val folder: String = diskPath + File.separatorChar + packageName + File.separatorChar + "logger"
//                        val handlerThread = HandlerThread("logSave")
//                        handlerThread.start()
//                        val customLogSaveHandler = CustomLogSaveHandler(handlerThread.looper, folder, MAX_BYTES)
//                        val diskLogStrategy = DiskLogStrategy(customLogSaveHandler)
//                        val csvFormatStrategy = CsvFormatStrategy.newBuilder()
//                                .logStrategy(diskLogStrategy)
//                                .tag("MyTag")
//                                .build()
//                        Logger.addLogAdapter(DiskLogAdapter(csvFormatStrategy))
//
//                        Logger.d("set log save to sd card")
//                    }
//
//                    override fun onDeny(permission: String?, position: Int) {
//                        Logger.e("onDeny " + permission)
//                    }
//
//                    override fun onGuarantee(permission: String?, position: Int) {
//                        Logger.e("onGuarantee " + permission)
//                    }
//
//                    override fun onClose() {
//                        Logger.e("onClose ")
//                    }
//                })
    }
}