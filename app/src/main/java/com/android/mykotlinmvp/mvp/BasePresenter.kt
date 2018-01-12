package com.android.mykotlinmvp.mvp

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by zhangguanjun on 2018/1/10.
 */
open class BasePresenter<T: IBaseView>: IPresenter<T>{

    var mRootView: T? = null

    private var mCompositeDisposable = CompositeDisposable()

    private val isViewAttached: Boolean
        get() = mRootView != null

    override fun attachView(rootView: T) {
        mRootView = rootView
    }

    override fun detachView() {
        mRootView = null

        if (!mCompositeDisposable.isDisposed) {
            mCompositeDisposable.clear()
        }
    }

    fun checkViewAttached() {
        if (!isViewAttached) {
            throw RuntimeException("the view is not attached")
        }
    }

    fun addSubscription(disposable: Disposable) {
        mCompositeDisposable.add(disposable)
    }
}