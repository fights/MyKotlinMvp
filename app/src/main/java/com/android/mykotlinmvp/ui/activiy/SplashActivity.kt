package com.android.mykotlinmvp.ui.activiy

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Typeface
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import com.android.mykotlinmvp.MyApplication
import com.android.mykotlinmvp.R
import com.android.mykotlinmvp.ui.base.BaseActivity
import com.android.mykotlinmvp.utils.AppUtil
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_splash.*
import me.weyye.hipermission.HiPermission
import me.weyye.hipermission.PermissionCallback
import me.weyye.hipermission.PermissionItem

/**
 * Created by zhangguanjun on 2017/12/26.
 */
class SplashActivity : BaseActivity() {


    private var textTypeface: Typeface? = null

    private var descTypeFace: Typeface? = null

    override fun init() {

        //初始化字体格式
        textTypeface = Typeface.createFromAsset(MyApplication.context.assets, "fonts/Lobster-1.4.otf")
        descTypeFace = Typeface.createFromAsset(MyApplication.context.assets, "fonts/FZLanTingHeiS-L-GB-Regular.TTF")

    }

    private fun requestPermission() {
        val permissionItems = arrayListOf<PermissionItem>(
                PermissionItem(Manifest.permission.READ_PHONE_STATE, "手机状态", R.drawable.permission_ic_phone)
                , PermissionItem(android.Manifest.permission.WRITE_EXTERNAL_STORAGE,"sd卡", R.drawable.permission_ic_storage))
        HiPermission.create(this)
                .msg("为了能够正常使用应用，若拒绝，可能会造成应用的无法正常使用")
                .permissions(permissionItems)
                .checkMutiPermission(object : PermissionCallback{
                    override fun onFinish() {
                        Logger.d("权限获取成功")

                        jumpHomeActivity()
                    }

                    override fun onDeny(permission: String?, position: Int) {
                        Logger.e("permission onDeny , permission = " + permission)
                        finish()
                    }

                    override fun onGuarantee(permission: String?, position: Int) {
                        Logger.e("permission onGuarantee , permission = " + permission)
                        finish()
                    }

                    override fun onClose() {
                        Logger.e("读取手机状态权限被关闭，请到应用管理中手动打开 ")
                        finish()
                    }

                })
    }

    override fun getLayoutId(): Int = R.layout.activity_splash

    @SuppressLint("SetTextI18n")
    override fun initView() {
        //设置字体
        tv_app_name.typeface = textTypeface
        tv_splash_desc.typeface = descTypeFace
        tv_version_name.text = "V${AppUtil.getVersionName(this)}"

        //初始化渐变动画
        val alphaAnimation = AlphaAnimation(0.3f, 1.0f)
        alphaAnimation.duration = 2000
        alphaAnimation.setAnimationListener(object :Animation.AnimationListener{
            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {

                //申请需要用到的权限
                requestPermission()
            }

            override fun onAnimationStart(animation: Animation?) {
            }

        })

        rl_splash_layout.startAnimation(alphaAnimation)
    }

    private fun jumpHomeActivity() {
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }

    override fun getData() {
    }

}