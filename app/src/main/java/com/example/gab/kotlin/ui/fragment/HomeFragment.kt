package com.example.gab.kotlin.ui.fragment

import android.annotation.SuppressLint
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.gab.kotlin.R
import com.example.gab.kotlin.adapter.BaseAdapter
import com.example.gab.kotlin.api.ApiService
import com.example.gab.kotlin.base.BaseFragment
import com.example.gab.kotlin.bean.ArticleBean
import com.example.gab.kotlin.bean.BannerBean
import com.example.gab.kotlin.view.BannerImageLoader
import com.example.gab.kotlin.web.WebViewActivity
import com.ggz.baselibrary.retrofit.NetCallBack
import com.ggz.baselibrary.retrofit.RequestUtils
import com.ggz.baselibrary.retrofit.RxHelper
import com.kaopiz.kprogresshud.KProgressHUD
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*

/**
 * Created by 初夏小溪
 * data :2019/1/7 0007 9:32
 */
class HomeFragment : BaseFragment() {

    val mAdapter = BaseAdapter(ArrayList())
    var mPageNo = 0
//    private lateinit var banner: Banner

    override fun initView(view: View?) {
        initRecyle()
    }

    override fun setContentLayout(): Int {
        return R.layout.fragment_home
    }

    override fun initData() {
        home_srl.autoRefresh()
        initRefresh()
    }

    override fun isLazyLoad(): Boolean {
        return false
    }

    private fun bannerView(pic: List<String>, urls: List<String>) {
        val banner = mContext.findViewById<Banner>(R.id.banner_news)
        banner?.run {
            setImageLoader(BannerImageLoader())
            setIndicatorGravity(BannerConfig.RIGHT)
            setImages(pic)
            start()
            setOnBannerListener {
                WebViewActivity.startWebActivity(mContext, urls[it])
            }
        }
    }

    private fun getBanner() {
        RequestUtils.create(ApiService::class.java)
                .getBanner()
                .compose(RxHelper.handleResult())
                .compose(RxHelper.bindToLifecycle(mContext))
                .subscribe(object : NetCallBack<List<BannerBean>>() {
                    override fun onSuccess(bannerBean: List<BannerBean>) {
                        val images = arrayListOf<String>()
                        val urls = arrayListOf<String>()
                        for (bannerData in bannerBean) {
                            images.add(bannerData.imagePath)
                            urls.add(bannerData.url)
                        }
                        bannerView(images, urls)
                    }

                    override fun updataLayout(flag: Int) {
                    }

                })
    }

    @SuppressLint("CheckResult")
    private fun getData(mPageNo: Int) {
        mKProgressHUD = KProgressHUD.create(mContext).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setCancellable(true)
                .setAnimationSpeed(2).setDimAmount(0.5f).show()
        RequestUtils.create(ApiService::class.java)
                .getArticleHomeList(mPageNo)
                .compose(RxHelper.handleResult())
                .compose(RxHelper.bindToLifecycle(mContext))
                .subscribe(object : NetCallBack<ArticleBean>() {
                    override fun onSuccess(articleBean: ArticleBean?) {
                        mKProgressHUD.dismiss()
                        articleBean?.let {
                            when {
                                home_srl.isRefreshing -> {
                                    mAdapter.setNewData(articleBean.datas)
                                    home_srl.finishRefresh()
                                }
                                home_srl.isLoading -> {
                                    mAdapter.data.addAll(articleBean.datas)
                                    home_srl.finishLoadMore()
                                    mAdapter.notifyDataSetChanged()
                                }
                                else -> mAdapter.setNewData(articleBean.datas)
                            }
                        }
                    }

                    override fun updataLayout(flag: Int) {
                    }

                })
    }

    private fun initRecyle() {
        home_recyclerView.run {
            layoutManager = LinearLayoutManager(mContext)
            mAdapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
                WebViewActivity.startWebActivity(mContext, mAdapter.data[position].link)
                mContext.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
            adapter = mAdapter
            mAdapter.setEmptyView(R.layout.activity_null_data, home_recyclerView.parent as ViewGroup)
        }
    }

    private fun initRefresh() {
        home_srl.run {
            setRefreshHeader(ClassicsHeader(mContext))
            setRefreshFooter(ClassicsFooter(mContext))
            setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
                override fun onLoadMore(refreshLayout: RefreshLayout?) {
                    mPageNo += 1
                    getData(mPageNo)
                }

                override fun onRefresh(refreshLayout: RefreshLayout?) {
                    mPageNo = 0
                    getData(0)
                    getBanner()
                    mKProgressHUD.dismiss()
                }
            })
        }
    }

    override fun onStart() {
        super.onStart()
        banner_news.startAutoPlay()
    }

    override fun onPause() {
        super.onPause()
        when {
            home_srl.isRefreshing -> home_srl.finishRefresh()
            home_srl.isLoading -> home_srl.finishLoadMore()
        }
    }

    override fun onStop() {
        super.onStop()
        banner_news.stopAutoPlay()
    }
}

