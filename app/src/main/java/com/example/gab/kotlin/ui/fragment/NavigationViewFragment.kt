package com.example.gab.kotlin.ui.fragment

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.gab.kotlin.R
import com.example.gab.kotlin.adapter.NavigationLifeAdapter
import com.example.gab.kotlin.adapter.NavigationRightAdapter
import com.example.gab.kotlin.api.ApiService
import com.example.gab.kotlin.base.BaseFragment
import com.example.gab.kotlin.bean.NavigationBean
import com.example.gab.kotlin.web.WebViewActivity
import com.ggz.baselibrary.retrofit.NetCallBack
import com.ggz.baselibrary.retrofit.RequestUtils
import com.ggz.baselibrary.retrofit.RxHelper
import com.kaopiz.kprogresshud.KProgressHUD
import kotlinx.android.synthetic.main.fragment_navigation.*
import java.util.*

/**
 * Created by 初夏小溪
 * data :2019/1/7 0007 15:12
 */
class NavigationViewFragment : BaseFragment() {

    val mAdapter = NavigationLifeAdapter(ArrayList())
    val mAdapterRight = NavigationRightAdapter(ArrayList())

    var mSelectedPos = 0
    private var mLinearLayoutManager: LinearLayoutManager? = null

    override fun isLazyLoad(): Boolean {
        return false
    }

    override fun initView(view: View?) {
        initRecyclerLife()
        initRecyclerRight()
    }

    override fun setContentLayout(): Int {
        return R.layout.fragment_navigation
    }

    override fun initData() {
        getNavigationList()
    }

    private fun getNavigationList() {
        mKProgressHUD = KProgressHUD.create(mContext).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setCancellable(true)
                .setAnimationSpeed(2).setDimAmount(0.5f).show()
        RequestUtils.create(ApiService::class.java)
                .getNavigationList()
                .compose(RxHelper.handleResult())
                .compose(RxHelper.bindToLifecycle(mContext))
                .subscribe(object : NetCallBack<List<NavigationBean>>() {
                    override fun onSuccess(articleBean: List<NavigationBean>) {
                        mKProgressHUD.dismiss()
                        mAdapter.setNewData(articleBean)
                        if (mSelectedPos == 0) {
                            mAdapter.data[mSelectedPos].isSelected = true
                            mAdapterRight.setNewData(articleBean[mSelectedPos].articles)
                        }
                    }

                    override fun updataLayout(flag: Int) {
                    }

                })
    }

    private fun initRecyclerLife() {
        mLinearLayoutManager = LinearLayoutManager(activity)
        rv_title.run {
            layoutManager = LinearLayoutManager(activity)
            mAdapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
                if (mSelectedPos != position) {
                    mAdapter.data[mSelectedPos].isSelected = false
                    mAdapter.notifyItemChanged(mSelectedPos)
                    mSelectedPos = position
                    mAdapter.data[mSelectedPos].isSelected = true
                    mAdapter.notifyItemChanged(mSelectedPos)
                }
                val navigationBean = mAdapter.data[position]
                mAdapterRight.setNewData(navigationBean.articles)
                val childAt = getChildAt(position - mLinearLayoutManager!!.findFirstVisibleItemPosition())
                if (childAt != null) {
                    val y = childAt.top - height / 2
                    smoothScrollBy(0, y)
                }
            }
            adapter = mAdapter
        }
    }

    private fun initRecyclerRight() {
        rv_context.run {
            scrollToPosition(0)
            layoutManager = GridLayoutManager(mContext, 3)
            mAdapterRight.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
                WebViewActivity.startWebActivity(mContext, mAdapterRight.data[position].link!!)
                mContext.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
            adapter = mAdapterRight
        }
    }
}