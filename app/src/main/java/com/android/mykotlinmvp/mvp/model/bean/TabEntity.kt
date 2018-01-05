package com.android.mykotlinmvp.mvp.model.bean

import com.flyco.tablayout.listener.CustomTabEntity

/**
 * Created by zhangguanjun on 2018/1/4.
 */
class TabEntity(var title: String, private var unSelectedIcon: Int, private var selectedIcon: Int) : CustomTabEntity {

    override fun getTabUnselectedIcon(): Int =  unSelectedIcon

    override fun getTabSelectedIcon(): Int = selectedIcon

    override fun getTabTitle(): String = title

}