package com.example.gab.kotlin.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.gab.kotlin.R
import com.example.gab.kotlin.adapter.MyCollectKotlinAdapter
import com.example.gab.kotlin.api.ApiService
import com.example.gab.kotlin.bean.CollectBean
import com.example.gab.kotlin.web.WebViewActivity
import com.ggz.baselibrary.application.IBaseActivity
import com.ggz.baselibrary.retrofit.NetCallBack
import com.ggz.baselibrary.retrofit.RequestUtils
import com.ggz.baselibrary.retrofit.RxHelper
import com.ggz.baselibrary.utils.JumpUtils
import com.ggz.baselibrary.utils.T
import com.kaopiz.kprogresshud.KProgressHUD
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.android.synthetic.main.activity_collect.*
import kotlinx.android.synthetic.main.activity_collect_kotlin.*
import java.util.*

/**
 * Created by 初夏小溪
 * data :2019/1/4 0004 14:44
 */
class MyCollectActivity : AppCompatActivity(), IBaseActivity {

    var mPageNo = 0
    val mAdapter = MyCollectKotlinAdapter(ArrayList())
    lateinit var mKProgressHUD: KProgressHUD //延迟初始化 lateinit

    override fun isShowHeadView(): Boolean {
        return true
    }

    override fun setView(): Int {
        return R.layout.activity_collect_kotlin
    }

    override fun setStatusBar(activity: Activity?) {
    }

    override fun initData(activity: Activity?, savedInstanceState: Bundle?) {
        initRecyle()
        initRefresh()
        getArticleList(mPageNo)
    }

    override fun onClick(v: View?) {
    }

    private fun initRecyle() {
        rv_collect_title.run {
            layoutManager = LinearLayoutManager(this@MyCollectActivity)
            mAdapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
                WebViewActivity.startWebActivity(
                        this@MyCollectActivity,
                        mAdapter.data[position].link!!
                )
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
            mAdapter.onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { adapter, view, position ->
                when (view.id) {
                    R.id.image_collect ->
                        unCollectArticle(
                                mAdapter.data[position].id,
                                mAdapter.data[position].originId,
                                position
                        )
                }
            }
            mAdapter.setEmptyView(R.layout.activity_null_data, parent as ViewGroup)
            adapter = mAdapter
        }
    }

    /**
     * 我的收藏 数据加载
     */
    private fun getArticleList(mPageNo: Int) {
        mKProgressHUD = KProgressHUD.create(this).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setCancellable(true)
                .setAnimationSpeed(2).setDimAmount(0.5f).show()
        RequestUtils.create(ApiService::class.java)
                .getCollectList(mPageNo)
                .compose(RxHelper.handleResult())
                .compose(RxHelper.bindToLifecycle(this))
                .subscribe(object : NetCallBack<CollectBean>() {
                    override fun updataLayout(flag: Int) {
                    }

                    override fun onSuccess(collectBean: CollectBean?) {
                        collectBean?.let {
                            mKProgressHUD.dismiss()
                            when {
                                refreshLayout_collect.isRefreshing -> {
                                    mAdapter.setNewData(collectBean.datas)
                                    refreshLayout_collect.finishRefresh()
                                }
                                refreshLayout_collect.isLoading -> {
                                    mAdapter.data.addAll(collectBean.datas!!)
                                    refreshLayout_collect.finishLoadMore()
                                    mAdapter.notifyDataSetChanged()
                                }
                                else -> mAdapter.setNewData(collectBean.datas)
                            }
                        }
                    }
                })
    }

    /**
     * 我的收藏页面, 取消收藏
     *
     * @param id
     * @param originId
     * @param position
     */
    @SuppressLint("CheckResult")
    private fun unCollectArticle(id: Int, originId: Int, position: Int) {
        mKProgressHUD = KProgressHUD.create(this).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setCancellable(true)
                .setAnimationSpeed(2).setDimAmount(0.5f).show()
        RequestUtils.create(ApiService::class.java)
                .unMyCollectArticle(id, originId)
                .compose(RxHelper.handleResult())
                .compose(RxHelper.bindToLifecycle(this))
                .subscribe(object : NetCallBack<Any>() {
                    override fun onSuccess(t: Any) {
                        mKProgressHUD.dismiss()
                        mAdapter.remove(position)
                        mAdapter.notifyDataSetChanged()
                        T.showShort(getString(R.string.cancel_collection_success))
                    }

                    override fun updataLayout(flag: Int) {

                    }
                })
    }

    /**
     * 分页加载数据
     */
    private fun initRefresh() {
        refreshLayout_collect.run {
            setRefreshHeader(ClassicsHeader(this@MyCollectActivity))
            setRefreshFooter(ClassicsFooter(this@MyCollectActivity))
            setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
                override fun onLoadMore(refreshLayout: RefreshLayout) {
                    mPageNo += 1
                    getArticleList(mPageNo)
                }

                override fun onRefresh(refreshLayout: RefreshLayout) {
                    mPageNo = 0
                    getArticleList(mPageNo)
                }
            })
        }
    }

    override fun onPause() {
        super.onPause()
        when {
            refreshLayout_collect.isRefreshing -> {
                refreshLayout_collect.finishRefresh()
            }
            refreshLayout_collect.isLoading -> {
                refreshLayout_collect.finishLoadMore()
            }
        }
    }
}