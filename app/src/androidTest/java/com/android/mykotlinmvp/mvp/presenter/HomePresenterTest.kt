package com.android.mykotlinmvp.mvp.presenter

import org.junit.Test

/**
 * Created by zhangguanjun on 2018/1/11.
 */
class HomePresenterTest {
    @Test
    fun loadHomeData() {
        val homePresenter = HomePresenter()
        homePresenter.loadHomeData(1)
    }

    @Test
    fun loadMoreData() {
    }

}