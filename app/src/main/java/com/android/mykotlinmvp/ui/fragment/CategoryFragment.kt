package com.android.mykotlinmvp.ui.fragment

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.android.mykotlinmvp.R
import com.android.mykotlinmvp.mvp.contract.CategoryContract
import com.android.mykotlinmvp.mvp.presenter.CategoryPresenter
import com.android.mykotlinmvp.ui.activity.CategoryDetailActivity
import com.android.mykotlinmvp.ui.adapter.CategoryAdapter
import com.android.mykotlinmvp.ui.base.BaseFragment
import com.android.mykotlinmvp.utils.Util
import com.android.mykotlinmvp.view.recyclerview.OnItemClickListener
import com.hazz.kotlinmvp.mvp.model.bean.CategoryBean
import kotlinx.android.synthetic.main.layout_recycler_view.*

/**
 * Created by zhangguanjun on 2018/1/27.
 */
class CategoryFragment : BaseFragment(),CategoryContract.View {
    private val mPresenter by lazy { CategoryPresenter() }
    private lateinit var mCategoryAdapter: CategoryAdapter

    override fun showLoading() {

    }

    override fun dismissLoading() {

    }

    override fun showCategory(categoryList: ArrayList<CategoryBean>) {
        mCategoryAdapter = CategoryAdapter(activity!!,categoryList,R.layout.item_category)
        val layoutManager = GridLayoutManager(activity, 2)
        layoutManager.orientation = GridLayoutManager.VERTICAL
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = mCategoryAdapter
        recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                val position = parent.getChildPosition(view)
                val offset = Util.dp2Px(5)

                outRect.set(if (position % 2 == 0) 0 else offset, offset,
                        if (position % 2 == 0) offset else 0, offset)
            }
        })

        mCategoryAdapter.setOnItemClickListener(object : OnItemClickListener{
            override fun onItemClick(obj: Any?, position: Int) {

                //点击条目跳转到详情页面
                val data = obj as CategoryBean
                val intent = Intent(activity, CategoryDetailActivity::class.java)
                intent.putExtra(CategoryDetailActivity.CATEGORY_INFO,data)
                startActivity(intent)
            }
        })
    }

    override fun showErrorMsg(msg: String, errorCode: Int) {

    }

    private var mTitle:String? = null

    companion object {
        fun getInstance(title: String): CategoryFragment {
            val categoryFragment = CategoryFragment()
            val bundle = Bundle()
            categoryFragment.arguments = bundle
            categoryFragment.mTitle = title
            return categoryFragment
        }
    }

    override fun getLayoutId(): Int = R.layout.layout_recycler_view

    override fun loadData() {
        mPresenter.getCategory()
    }

    override fun initListener() {

    }

    override fun initView() {
        mPresenter.attachView(this)
        mMutipleStatusView = multipleStatusView
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }
}