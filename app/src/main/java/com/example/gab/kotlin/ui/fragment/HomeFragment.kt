package com.example.gab.kotlin.ui.fragment

import android.annotation.SuppressLint
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import com.bigkoo.convenientbanner.listener.OnItemClickListener
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.gab.kotlin.R
import com.example.gab.kotlin.adapter.BaseAdapter
import com.example.gab.kotlin.api.ApiService
import com.example.gab.kotlin.base.BaseFragment
import com.example.gab.kotlin.bean.BaseBean
import com.example.gab.kotlin.web.WebViewActivity
import com.ggz.baselibrary.retrofit.NetCallBack
import com.ggz.baselibrary.retrofit.RequestUtils
import com.ggz.baselibrary.retrofit.RxHelper
import com.kaopiz.kprogresshud.KProgressHUD
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*

/**
 * Created by 初夏小溪
 * data :2019/1/7 0007 9:32
 */
class HomeFragment : BaseFragment(), OnItemClickListener {

    val mAdapter = BaseAdapter(ArrayList())
    var mPageNo = 0

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

    override fun onItemClick(position: Int) {
    }

    private val pics = arrayListOf<String>()
    var images = arrayOf("http://img2.imgtn.bdimg.com/it/u=3093785514,1341050958&fm=21&gp=0.jpg",
            "http://img2.3lian.com/2014/f2/37/d/40.jpg", "http://d.3987.com/sqmy_131219/001.jpg",
            "http://img2.3lian.com/2014/f2/37/d/39.jpg", "http://www.8kmm.com/UploadFiles/2012/8/201208140920132659.jpg",
            "http://f.hiphotos.baidu.com/image/h%3D200/sign=1478eb74d5a20cf45990f9df460b4b0c/d058ccbf6c81800a5422e5fdb43533fa838b4779.jpg",
            "http://f.hiphotos.baidu.com/image/pic/item/09fa513d269759ee50f1971ab6fb43166c22dfba.jpg")


//    private fun bannerView(urls: List<String>, pic: List<String>) {
//        banner_home.setPages(CBViewHolderCreator {
//            NetworkImageHolderView()
//        },)
//                .setPageIndicator(intArrayOf(R.drawable.shape_banner_indicator1, R.drawable.shape_banner_indicator2))
//                .setPointViewVisible(true)
//                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
//                .setPageTransformer(AccordionTransformer())
//                .setOnItemClickListener { position ->
//                    val bundle = Bundle()
//                    bundle.putString("Link", urls[position])
//                }.isManualPageable = true
//    }

    @SuppressLint("CheckResult")
    private fun getData(mPageNo: Int) {
        mKProgressHUD = KProgressHUD.create(mContext).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setCancellable(true)
                .setAnimationSpeed(2).setDimAmount(0.5f).show()
        RequestUtils.create(ApiService::class.java)
                .getArticleHomeList(mPageNo)
                .compose(RxHelper.handleResult())
                .compose(RxHelper.bindToLifecycle(mContext))
                .subscribe(object : NetCallBack<BaseBean>() {
                    override fun onSuccess(baseBean: BaseBean) {
                        mKProgressHUD.dismiss()
                        if (baseBean.datas?.size != 0) {
                            when {
                                home_srl.isRefreshing -> {
                                    mAdapter.setNewData(baseBean.datas)
                                    home_srl.finishRefresh()
                                }
                                home_srl.isLoading -> {
                                    mAdapter.data.addAll(baseBean.datas!!)
                                    home_srl.finishLoadMore()
                                    mAdapter.notifyDataSetChanged()
                                }
                                else -> mAdapter.setNewData(baseBean.datas)
                            }
                        } else {
                            when {
                                home_srl.isLoading ->
                                    home_srl.finishLoadMore()
                                home_srl.isRefreshing ->
                                    home_srl.finishRefresh()
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
                WebViewActivity.startWebActivity(mContext, mAdapter.data[position].link!!,
                        mAdapter.data[position].id,
                        mAdapter.data[position].isCollect)
                mContext.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
            adapter = mAdapter
            mAdapter.setEmptyView(R.layout.activity_null_data, home_recyclerView.parent as ViewGroup)
        }
    }

    private fun initRefresh() {
        home_srl.setRefreshHeader(ClassicsHeader(mContext))
        home_srl.setRefreshFooter(ClassicsFooter(mContext))
        home_srl.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout?) {
                mPageNo += 1
                getData(mPageNo)
            }

            override fun onRefresh(refreshLayout: RefreshLayout?) {
                mPageNo = 0
                getData(0)
                mKProgressHUD.dismiss()
            }
        })
    }

    override fun onPause() {
        super.onPause()
        when {
            home_srl.isRefreshing -> home_srl.finishRefresh()
            home_srl.isLoading -> home_srl.finishLoadMore()
        }
    }
}

