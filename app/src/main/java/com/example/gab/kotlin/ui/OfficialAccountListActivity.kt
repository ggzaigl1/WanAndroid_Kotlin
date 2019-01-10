package com.example.gab.kotlin.ui

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.gab.kotlin.R
import com.example.gab.kotlin.adapter.BaseAdapter
import com.example.gab.kotlin.api.ApiService
import com.example.gab.kotlin.base.BaseActivity
import com.example.gab.kotlin.bean.BaseBean
import com.example.gab.kotlin.web.WebViewActivity
import com.ggz.baselibrary.application.IBaseActivity
import com.ggz.baselibrary.retrofit.NetCallBack
import com.ggz.baselibrary.retrofit.RequestUtils
import com.ggz.baselibrary.retrofit.RxHelper
import com.kaopiz.kprogresshud.KProgressHUD
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.android.synthetic.main.activity_official_account.*

/**
 * Created by 初夏小溪
 * data :2019/1/7 0007 16:40
 */
class OfficialAccountListActivity : BaseActivity(), IBaseActivity {

    private var mId = 0
    var mPageNo = 0
    var mAdapter = BaseAdapter(ArrayList())

    override fun isShowHeadView(): Boolean {
        return true
    }

    override fun setView(): Int {
        return R.layout.activity_official_account
    }

    override fun setStatusBar(activity: Activity?) {
    }

    override fun initData(activity: Activity?, savedInstanceState: Bundle?) {
        intent.let {
            mId = it.getIntExtra("id", -1)
        }
        initRecycler()
        initRefresh()
        getChaptersList(mPageNo)
    }

    override fun onClick(v: View?) {
    }

    private fun getChaptersList(mPageNo: Int) {
        mKProgressHUD = KProgressHUD.create(this@OfficialAccountListActivity).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setCancellable(true).setAnimationSpeed(2).setDimAmount(0.5f).show()
        RequestUtils.create(ApiService::class.java)
                .getWxarticle(mId, mPageNo)
                .compose(RxHelper.handleResult())
                .compose(RxHelper.bindToLifecycle(this))
                .subscribe(object : NetCallBack<BaseBean>() {
                    override fun onSuccess(baseBean: BaseBean?) {
                        if (baseBean != null) {
                            mKProgressHUD?.dismiss()
                            when {
                                srl_official_account.isRefreshing -> {
                                    mAdapter.setNewData(baseBean.datas)
                                    srl_official_account.finishRefresh()
                                }
                                srl_official_account.isLoading -> {
                                    mAdapter.data.addAll(baseBean.datas!!)
                                    srl_official_account.finishLoadMore()
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

    private fun initRecycler() {
        rv_official_account.run {
            layoutManager = LinearLayoutManager(this@OfficialAccountListActivity)
            mAdapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
                WebViewActivity.startWebActivity(this@OfficialAccountListActivity, mAdapter.data[position].link!!)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
            adapter = mAdapter
            mAdapter.emptyView = LayoutInflater.from(this@OfficialAccountListActivity).inflate(R.layout.activity_null_data, parent as ViewGroup, false)
        }
    }

    /**
     * 分页加载数据
     */
    private fun initRefresh() {
        srl_official_account.run {
            setRefreshHeader(ClassicsHeader(this@OfficialAccountListActivity))
            setRefreshFooter(ClassicsFooter(this@OfficialAccountListActivity))
            setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
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
    }

    override fun onPause() {
        super.onPause()
        when {
            srl_official_account.isRefreshing -> {
                srl_official_account.finishRefresh()
            }
            srl_official_account.isLoading -> {
                srl_official_account.finishLoadMore()
            }
        }
    }
}