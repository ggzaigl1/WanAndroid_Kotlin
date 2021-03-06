package com.example.gab.kotlin.ui.fragment

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.gab.kotlin.R
import com.example.gab.kotlin.adapter.OfficialAccountAdapter
import com.example.gab.kotlin.api.ApiService
import com.example.gab.kotlin.base.BaseFragment
import com.example.gab.kotlin.base.BaseFragments
import com.example.gab.kotlin.bean.OfficialAccountBean
import com.example.gab.kotlin.ui.OfficialAccountListActivity
import com.ggz.baselibrary.retrofit.NetCallBack
import com.ggz.baselibrary.retrofit.RequestUtils
import com.ggz.baselibrary.retrofit.RxHelper
import com.kaopiz.kprogresshud.KProgressHUD
import kotlinx.android.synthetic.main.fragment_official_account.*
import org.jetbrains.anko.startActivity

/**
 * Created by 初夏小溪
 * data :2019/1/7 0007 15:12
 */
class OfficialAccountFragment : BaseFragment() {

    var mAdapter = OfficialAccountAdapter(ArrayList())
    override fun isLazyLoad(): Boolean {
        return false
    }

    override fun initView(view: View) {
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
                        officialAccountBeans?.let {
                            mKProgressHUD?.dismiss()
                            mAdapter.setNewData(officialAccountBeans)
                        }
                    }

                    override fun updataLayout(flag: Int) {
                    }

                })
    }

    private fun initView() {
        rv_official_account_fm.layoutManager = LinearLayoutManager(activity)
        mAdapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->

            //            val bundleOf = bundleOf("id" to mAdapter.data[position].id)
//            JumpUtils.jumpFade(activity as AppCompatActivity?,OfficialAccountListActivity::class.java,bundleOf)

            mContext.startActivity<OfficialAccountListActivity>("id" to mAdapter.data[position].id)
            activity?.overridePendingTransition(com.ggz.baselibrary.R.anim.anim_slide_left_in, com.ggz.baselibrary.R.anim.anim_slide_left_out)
        }
        rv_official_account_fm.adapter = mAdapter
        mAdapter.setEmptyView(R.layout.activity_null_data, rv_official_account_fm.parent as ViewGroup)
    }
}
