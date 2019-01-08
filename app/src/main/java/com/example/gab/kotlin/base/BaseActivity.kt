package com.example.gab.kotlin.base

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.gab.kotlin.R
import com.example.gab.kotlin.api.ApiService
import com.ggz.baselibrary.application.IBaseActivity
import com.ggz.baselibrary.retrofit.NetCallBack
import com.ggz.baselibrary.retrofit.RequestUtils
import com.ggz.baselibrary.retrofit.RxHelper
import com.ggz.baselibrary.retrofit.ioc.ConfigUtils
import com.ggz.baselibrary.utils.KeyBoardUtils
import com.ggz.baselibrary.utils.NetworkUtils
import com.ggz.baselibrary.utils.T
import com.ggz.baselibrary.utils.permission.PermissionChecker
import com.kaopiz.kprogresshud.KProgressHUD

/**
 * Created by 初夏小溪
 * data :2019/1/4 0004 11:47
 */
open class BaseActivity : AppCompatActivity(), IBaseActivity {

    protected var mKProgressHUD: KProgressHUD? = null
    protected var permissionChecker: PermissionChecker? = null
    protected val PERMISSIONS = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE
            , Manifest.permission.WRITE_EXTERNAL_STORAGE)


    override fun isShowHeadView(): Boolean {
        return false
    }

    override fun setView(): Int {
        return 0
    }

    override fun setStatusBar(activity: Activity?) {
    }

    override fun initData(activity: Activity?, savedInstanceState: Bundle?) {
        if (!NetworkUtils.isNetworkAvailable(ConfigUtils.getAppCtx())) {
            T.showShort("好像没有网络耶~")
        }
    }

    override fun reTry() {
    }

    override fun onClick(v: View?) {
    }


    /**
     * 收藏
     *
     * @param view
     * @param id
     */
    @SuppressLint("CheckResult")
    internal fun collectArticle(view: View, id: Int) {
        mKProgressHUD = KProgressHUD.create(this).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setCancellable(true).setAnimationSpeed(2).setDimAmount(0.5f).show()
        RequestUtils.create(ApiService::class.java)
                .getCollectArticle(id, "")
                .compose(RxHelper.handleResult())
                .compose(RxHelper.bindToLifecycle(this))
                .subscribe(object : NetCallBack<Any>() {
                    override fun onSuccess(t: Any) {
                        mKProgressHUD?.dismiss()
                        Snackbar.make(view, R.string.collection_success, Snackbar.LENGTH_SHORT).setAction("Action", null).show()
                    }

                    override fun updataLayout(flag: Int) {

                    }
                })
    }

    /**
     * 取消收藏
     *
     * @param id
     */
    @SuppressLint("CheckResult")
    internal fun unCollectArticle(id: Int) {
        mKProgressHUD = KProgressHUD.create(this).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setCancellable(true).setAnimationSpeed(2).setDimAmount(0.5f).show()
        RequestUtils.create(ApiService::class.java)
                .unCollectArticle(id, "")
                .compose(RxHelper.handleResult())
                .compose(RxHelper.bindToLifecycle(this))
                .subscribe(object : NetCallBack<Any>() {
                    override fun onSuccess(t: Any) {
                        mKProgressHUD?.dismiss()
                    }

                    override fun updataLayout(flag: Int) {

                    }
                })
    }

    override fun onDestroy() {
        super.onDestroy()
        mKProgressHUD?.dismiss()
    }

    override fun finish() {
        super.finish()
        //重写 Activity 的 finish ⽅法, 并调⽤ overridePendingTransition ⽅法，解决退出动画⽆效
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        KeyBoardUtils.closeKeyBoard(this)
    }
}