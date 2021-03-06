package com.example.gab.kotlin.base

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ggz.baselibrary.application.IBaseActivity
import com.ggz.baselibrary.utils.KeyBoardUtils
import com.ggz.baselibrary.utils.permission.PermissionChecker
import com.kaopiz.kprogresshud.KProgressHUD
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 * Created by 初夏小溪
 * data :2019/1/4 0004 11:47
 */
abstract class BaseActivity : AppCompatActivity(), IBaseActivity, AnkoLogger {

    protected var mKProgressHUD: KProgressHUD? = null
    protected var permissionChecker: PermissionChecker? = null
    protected val PERMISSIONS = arrayOf(
        READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE
    )
    private lateinit var mContext: BaseActivity

    override fun initData(activity: Activity?, savedInstanceState: Bundle?) {
        mContext = this@BaseActivity
    }

    /**
     * 子线程做处理
     */
    protected fun myToast(msg: String) {
        runOnUiThread { toast(msg) }
    }

    override fun onDestroy() {
        super.onDestroy()
        mKProgressHUD?.dismiss()
    }

    override fun finish() {
        super.finish()
        //重写 Activity 的 finish ⽅法, 并调⽤ overridePendingTransition ⽅法，解决退出动画⽆效
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        KeyBoardUtils.closeKeyBoard(this@BaseActivity)
    }


    inline fun <reified T : AppCompatActivity> startActivityForFinish() {
        startActivity<T>()
        finish()
    }
}