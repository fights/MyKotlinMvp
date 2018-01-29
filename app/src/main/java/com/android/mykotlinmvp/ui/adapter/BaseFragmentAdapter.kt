package com.android.mykotlinmvp.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Created by zhangguanjun on 2018/1/27.
 */
class BaseFragmentAdapter : FragmentPagerAdapter {

    private var mFragmentManager: FragmentManager? = null
    private var mFragments: ArrayList<Fragment>? = null
    private var mTitles: Array<String>? = null

    constructor(fragmentManager: FragmentManager, fragments: ArrayList<Fragment>, titles: Array<String>) : super(fragmentManager){
        this.mTitles = titles
        mFragmentManager = fragmentManager
        mFragments = fragments
    }

    override fun getItem(position: Int): Fragment = mFragments?.get(position)!!

    override fun getCount(): Int = mFragments?.size!!

    override fun getPageTitle(position: Int): CharSequence? {
        return mTitles?.get(position)
    }


}