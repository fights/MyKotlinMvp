package com.android.mykotlinmvp.ui.activiy

import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import com.android.mykotlinmvp.R
import com.android.mykotlinmvp.mvp.model.bean.TabEntity
import com.android.mykotlinmvp.showToast
import com.android.mykotlinmvp.ui.base.BaseActivity
import com.android.mykotlinmvp.ui.fragment.DiscoveryFragment
import com.android.mykotlinmvp.ui.fragment.HomeFragment
import com.android.mykotlinmvp.ui.fragment.HotFragment
import com.android.mykotlinmvp.ui.fragment.MineFragment
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by zhangguanjun on 2018/1/3.
 */
class MainActivity : BaseActivity() {

    companion object {
        private val TAG_HOME_FRAGMENT = "home"
        private val TAG_DISCOVERY_FRAGMENT = "discovery"
        private val TAG_HOT_FRAGMENT = "hot"
        private val TAG_MINE_FRAGMENT = "mine"
        private val KEY_OF_CURRENT_TAB = "current_tab"
    }

    private val mTitles = arrayOf("每日精选","发现","热门","我的")

    //未选中时的图标
    private val mIconUnselectedIds = intArrayOf(R.mipmap.ic_home_normal,R.mipmap.ic_discovery_normal,R.mipmap.ic_hot_normal,R.mipmap.ic_mine_normal)

    //选中时的图标
    private val mIconSelectedIds = intArrayOf(R.mipmap.ic_home_selected,R.mipmap.ic_discovery_selected,R.mipmap.ic_hot_selected,R.mipmap.ic_mine_selected)

    private val mTabEntities = ArrayList<CustomTabEntity>()

    private var mCurrentTabIndex = 0

    private var mHomeFragment: HomeFragment? = null
    private var mDiscoveryFragment: DiscoveryFragment? = null
    private var mHotFragment: HotFragment? = null
    private var mMineFragment: MineFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            mCurrentTabIndex = savedInstanceState.getInt(KEY_OF_CURRENT_TAB)
        }
        super.onCreate(savedInstanceState)
    }

    override fun init() {
        intTab()
    }

    private fun intTab() {
        (0 until mTitles.size).mapTo(mTabEntities){
            TabEntity(mTitles[it],mIconUnselectedIds[it],mIconSelectedIds[it])
        }
        tab_layout.setTabData(mTabEntities)
        tab_layout.setOnTabSelectListener(object: OnTabSelectListener{
            override fun onTabSelect(position: Int) {
                switchFragment(position)
            }

            override fun onTabReselect(position: Int) {
            }

        })
        tab_layout.currentTab = mCurrentTabIndex
        switchFragment(mCurrentTabIndex)
    }

    private fun switchFragment(position: Int) {
        //这里纯粹是为了想用以一下kotlin的switch语句，所以才这样写，其实完全可以将所有的Fragment全部创建好
        //给到tabLayout来处理，看了他内部的实现，和如下代码的实现是类似的。
        val transaction = supportFragmentManager.beginTransaction()
        hideFragment(transaction)

        when (position) {
            0 ->{
                if (mHomeFragment == null) {
                    val homeFragment = supportFragmentManager.findFragmentByTag(TAG_HOME_FRAGMENT)
                    if (homeFragment != null) {
                        transaction.remove(homeFragment)
                    }

                    mHomeFragment = HomeFragment.getInstance(TAG_HOME_FRAGMENT)
                    transaction.add(R.id.container,mHomeFragment, TAG_HOME_FRAGMENT)
                }else{
                    transaction.show(mHomeFragment)
                }
            }
            1 -> {
                if (mDiscoveryFragment == null) {
                    val discoveryFragment = supportFragmentManager.findFragmentByTag(TAG_DISCOVERY_FRAGMENT)
                    if (discoveryFragment != null) {
                        transaction.remove(discoveryFragment)
                    }

                    mDiscoveryFragment = DiscoveryFragment.getInstance(TAG_DISCOVERY_FRAGMENT)
                    transaction.add(R.id.container,mDiscoveryFragment, TAG_DISCOVERY_FRAGMENT)
                }else{
                    transaction.show(mDiscoveryFragment)
                }
            }
            2 -> {
                if (mHotFragment == null) {
                    val hotFragment = supportFragmentManager.findFragmentByTag(TAG_HOT_FRAGMENT)
                    if (hotFragment != null) {
                        transaction.remove(hotFragment)
                    }

                    mHotFragment = HotFragment.getInstance(TAG_HOT_FRAGMENT)
                    transaction.add(R.id.container,mHotFragment, TAG_HOT_FRAGMENT)
                }else{
                    transaction.show(mHotFragment)
                }
            }
            3 -> {
                if (mMineFragment == null) {
                    val mineFragment = supportFragmentManager.findFragmentByTag(TAG_MINE_FRAGMENT)
                    if (mineFragment != null) {
                        transaction.remove(mineFragment)
                    }

                    mMineFragment = MineFragment.getInstance(TAG_MINE_FRAGMENT)
                    transaction.add(R.id.container,mMineFragment, TAG_MINE_FRAGMENT)
                }else{
                    transaction.show(mMineFragment)
                }
            }
        }

        tab_layout.currentTab = position
        mCurrentTabIndex = position
        transaction.commitAllowingStateLoss()
    }

    private fun hideFragment(transaction: FragmentTransaction) {
        val homeFragment = supportFragmentManager.findFragmentByTag(TAG_HOME_FRAGMENT)
        val discoveryFragment = supportFragmentManager.findFragmentByTag(TAG_DISCOVERY_FRAGMENT)
        val hotFragment = supportFragmentManager.findFragmentByTag(TAG_HOT_FRAGMENT)
        val mineFragment = supportFragmentManager.findFragmentByTag(TAG_MINE_FRAGMENT)

        if (null != homeFragment) {
            transaction.hide(homeFragment)
        }
        if (null != discoveryFragment) {
            transaction.hide(discoveryFragment)
        }
        if (null != hotFragment) {
            transaction.hide(hotFragment)
        }
        if (null != mineFragment) {
            transaction.hide(mineFragment)
        }
    }

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initView() {
    }

    override fun getData() {
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putInt(KEY_OF_CURRENT_TAB,mCurrentTabIndex)
    }

    private var mBackPressedFirstTime: Long = 0

    override fun onBackPressed() {
        if (System.currentTimeMillis().minus(mBackPressedFirstTime) <= 2000) {
            finish()
        }else{
            mBackPressedFirstTime = System.currentTimeMillis()
            showToast(getString(R.string.toast_when_click_back))
        }
    }
}