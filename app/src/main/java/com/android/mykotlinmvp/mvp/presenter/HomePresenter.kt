package com.android.mykotlinmvp.mvp.presenter

import com.android.mykotlinmvp.mvp.BasePresenter
import com.android.mykotlinmvp.mvp.contract.HomeContract
import com.android.mykotlinmvp.mvp.model.HomeModel
import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import com.hazz.kotlinmvp.net.exception.ExceptionHandle

/**
 * Created by zhangguanjun on 2018/1/11.
 */
class HomePresenter: BasePresenter<HomeContract.View>(), HomeContract.Presenter{

    private var nextPageUrl: String? = null

    private var homeBean: HomeBean? = null

    private val homeModel by lazy { HomeModel() }

    override fun loadHomeData(num: Int) {
        //检测是否绑定View
        checkViewAttached()
        mRootView?.showLoading()

        /**
         * 返回的数据中type为banner和horizontalScrollCard的消息为无用信息，将其过滤掉，
         * 另外将首次请求的数据作为轮播图的数据，然后再请求下一页的数据作为首次展示的数据。
         */
        val disposable = homeModel.loadHomeData(num)
                .flatMap { bean ->
                    // 将返回的数据中的无用信息去除
                    val bannerItemList = bean.issueList[0].itemList
                    removeBannerMsg(bannerItemList)

                    homeBean = bean

                    //再请求下一页的数据作为要展示的信息
                    homeModel.loadMoreData(bean.nextPageUrl)
                }.subscribe(
                { bean ->
                    mRootView?.apply {
                        dismissLoading()
                        nextPageUrl = bean.nextPageUrl
                        val itemList = bean.issueList[0].itemList
                        removeBannerMsg(itemList)

                        //将count记录为第一页的视频数据的数量作为轮播图的个数
                        homeBean!!.issueList[0].count = homeBean!!.issueList[0].itemList.size

                        //将这次请求到的数据添加到原集合中
                        homeBean!!.issueList[0].itemList.addAll(itemList)
                        showHomeData(homeBean!!)
                    }
                }, { t ->
            mRootView?.apply {
                dismissLoading()
                showError(ExceptionHandle.handleException(t), ExceptionHandle.errorCode)
            }
        }
        )

        addSubscription(disposable)
    }

    private fun removeBannerMsg(bannerItemList: ArrayList<HomeBean.Issue.Item>) {
        bannerItemList.filter { item ->
            item.type == "banner2" || item.type == "horizontalScrollCard"
        }.forEach { item: HomeBean.Issue.Item ->
            bannerItemList.remove(item)
        }
    }

    override fun loadMoreData() {
        val disposable = nextPageUrl?.let {
            homeModel.loadMoreData(it)
                    .subscribe({ bean ->
                        mRootView?.apply {
                            val itemList = bean.issueList[0].itemList
                            removeBannerMsg(itemList)
                            nextPageUrl = bean.nextPageUrl
                            showMoreData(itemList)
                        }
                    }, { e ->
                        mRootView?.apply {
                            showError(ExceptionHandle.handleException(e), ExceptionHandle.errorCode)
                        }
                    })
        }

        if (disposable != null) {
            addSubscription(disposable)
        }
    }

}
