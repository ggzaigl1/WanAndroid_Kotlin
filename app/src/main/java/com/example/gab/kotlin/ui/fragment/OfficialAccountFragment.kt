package com.example.gab.kotlin.ui.fragment

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.gab.kotlin.R
import com.example.gab.kotlin.adapter.OfficialAccountAdapter
import com.example.gab.kotlin.api.ApiService
import com.example.gab.kotlin.base.BaseFragment
import com.example.gab.kotlin.bean.OfficialAccountBean
import com.example.gab.kotlin.ui.OfficialAccountListActivity
import com.ggz.baselibrary.retrofit.NetCallBack
import com.ggz.baselibrary.retrofit.RequestUtils
import com.ggz.baselibrary.retrofit.RxHelper
import com.ggz.baselibrary.utils.JumpUtils
import com.kaopiz.kprogresshud.KProgressHUD
import kotlinx.android.synthetic.main.fragment_official_account.*

/**
 * Created by 初夏小溪
 * data :2019/1/7 0007 15:12
 */
class OfficialAccountFragment : BaseFragment() {

    var mAdapter = OfficialAccountAdapter(ArrayList())
    override fun isLazyLoad(): Boolean {
        return false
    }

    override fun initView(view: View?) {
        initView()
    }

    override fun setContentLayout(): Int {
        return R.layout.fragment_official_account
    }

    override fun initData() {
        getChaptersList()
        srl_official_account_fragment.isEnableLoadMore = false
        srl_official_account_fragment.isEnableRefresh = false
    }

    /**
     * 公众号 数据加载
     */
    private fun getChaptersList() {
        mKProgressHUD = KProgressHUD.create(activity).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setCancellable(true).setAnimationSpeed(2).setDimAmount(0.5f).show()
        RequestUtils.create(ApiService::class.java)
                .getChapters()
                .compose(RxHelper.handleResult())
                .compose(RxHelper.bindToLifecycle(activity!!))
                .subscribe(object : NetCallBack<List<OfficialAccountBean>>() {
                    override fun onSuccess(officialAccountBeans: List<OfficialAccountBean>?) {
                        mKProgressHUD.dismiss()
                        mAdapter.setNewData(officialAccountBeans)
                    }

                    override fun updataLayout(flag: Int) {
                    }

                })
    }

    private fun initView() {
        rv_official_account_fm.layoutManager = LinearLayoutManager(activity)
        mAdapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
            val bundle = Bundle()
            bundle.putInt("id", mAdapter.data[position].id)
            JumpUtils.jumpFade(activity as AppCompatActivity?, OfficialAccountListActivity::class.java, bundle)
        }
        rv_official_account_fm.adapter = mAdapter
        mAdapter.setEmptyView(R.layout.activity_null_data, rv_official_account_fm.parent as ViewGroup)
    }
}
