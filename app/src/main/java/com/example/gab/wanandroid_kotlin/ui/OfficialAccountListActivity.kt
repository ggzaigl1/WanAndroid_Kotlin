package com.example.gab.wanandroid_kotlin.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.gab.wanandroid_kotlin.R
import com.example.gab.wanandroid_kotlin.adapter.BaseAdapter
import com.example.gab.wanandroid_kotlin.adapter.OfficialAccountAdapter
import com.example.gab.wanandroid_kotlin.api.ApiService
import com.example.gab.wanandroid_kotlin.bean.BaseBean
import com.example.gab.wanandroid_kotlin.web.WebViewActivity
import com.ggz.baselibrary.retrofit.NetCallBack
import com.ggz.baselibrary.retrofit.RequestUtils
import com.ggz.baselibrary.retrofit.RxHelper
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.android.synthetic.main.activity_collect_kotlin.*
import kotlinx.android.synthetic.main.activity_official_account.*

/**
 * Created by 初夏小溪
 * data :2019/1/7 0007 16:40
 */
class OfficialAccountListActivity : AppCompatActivity() {

    var mId = 0
    var mPageNo = 0
    var mAdapter = BaseAdapter(ArrayList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_official_account)
        mId = intent.getIntExtra("id", -1)
        initRecyle()
        initRefresh()
        getChaptersList(mPageNo)
    }

    private fun getChaptersList(mPageNo: Int) {
        RequestUtils.create(ApiService::class.java)
                .getWxarticle(mId, mPageNo)
                .compose(RxHelper.handleResult())
                .compose(RxHelper.bindToLifecycle(this))
                .subscribe(object : NetCallBack<BaseBean>() {
                    override fun onSuccess(baseBean: BaseBean?) {
                        if (baseBean != null) {
                            when {
                                refreshLayout_official_account_fragment.isRefreshing -> {
                                    mAdapter.setNewData(baseBean.datas)
                                    refreshLayout_official_account_fragment.finishRefresh()
                                }
                                refreshLayout_official_account_fragment.isLoading -> {
                                    mAdapter.data.addAll(baseBean.datas!!)
                                    refreshLayout_official_account_fragment.finishLoadMore()
                                    mAdapter.notifyDataSetChanged()
                                }
                                else -> mAdapter.setNewData(baseBean.datas)
                            }
                        }
                    }

                    override fun updataLayout(flag: Int) {
                    }

                })
    }

    private fun initRecyle() {
        rv_official_account.layoutManager = LinearLayoutManager(this)
        mAdapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
            WebViewActivity.startWebActivity(this, mAdapter.data[position].link!!
                    , mAdapter.data[position].id
                    , mAdapter.data[position].isCollect)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
        rv_official_account.adapter = mAdapter
        mAdapter.emptyView = LayoutInflater.from(this).inflate(R.layout.activity_null_data, rv_official_account.parent as ViewGroup, false)

    }

    /**
     * 分页加载数据
     */
    private fun initRefresh() {
        refreshLayout_official_account_fragment.setRefreshHeader(ClassicsHeader(this))
        refreshLayout_official_account_fragment.setRefreshFooter(ClassicsFooter(this))
        refreshLayout_official_account_fragment.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                mPageNo += 1
                getChaptersList(mPageNo)
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                mPageNo = 0
                getChaptersList(mPageNo)
            }
        })
    }

    override fun onPause() {
        super.onPause()
        when {
            refreshLayout_official_account_fragment.isRefreshing -> {
                refreshLayout_official_account_fragment.finishRefresh()
            }
            refreshLayout_official_account_fragment.isLoading -> {
                refreshLayout_official_account_fragment.finishLoadMore()
            }
        }
    }
}