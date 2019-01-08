package com.ggz.baselibrary.utils

import android.annotation.SuppressLint
import android.content.Context
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import com.ggz.baselibrary.R
import com.ggz.baselibrary.retrofit.ioc.ConfigUtils

/**
 * Created by 初夏小溪
 * data :2019/1/8 0008 14:23
 */

object ToastUtils {

    var isShow = true
    private var toast: Toast? = null

    /**
     * 短时间显示Toast
     *
     * @param message
     */
    fun showShort(message: CharSequence) {
        show(message.toString(), Toast.LENGTH_SHORT)
    }

    /**
     * 短时间显示Toast
     *
     * @param message
     */
    fun showShort(message: Int) {
        show(ResourceUtils.getStr(message), Toast.LENGTH_SHORT)
    }

    /**
     * 长时间显示Toast
     *
     * @param message
     */
    fun showLong(message: CharSequence) {
        show(message.toString(), Toast.LENGTH_LONG)
    }

    /**
     * 长时间显示Toast
     *
     * @param message
     */
    fun showLong(message: Int) {
        show(ResourceUtils.getStr(message), Toast.LENGTH_LONG)
    }

    @SuppressLint("ShowToast")
    private fun show(message: String, duration: Int) {
        if (isShow) {
            toast = if (null == toast) {
                Toast.makeText(ConfigUtils.getAppCtx(), "", duration)
            } else {
                toast?.cancel()
                Toast.makeText(ConfigUtils.getAppCtx(), "", duration)
            }

            toast?.setText(message)
            toast?.show()
        }
    }

    /**
     * 自定义 布局的Toast
     *
     * @param duration
     * @param message
     * @param LayoutId 自定义布局
     */
    fun showQulifier(duration: Int, message: CharSequence, @LayoutRes LayoutId: Int) {
        if (null == toast) {
            val inflate = ConfigUtils.getAppCtx().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view = inflate.inflate(LayoutId, null)

            toast = Toast(ConfigUtils.getAppCtx())
            toast?.view = view
            toast?.duration = duration
        } else {
            toast?.setText(message)
        }

        show(message.toString(), Toast.LENGTH_LONG)
    }

    /**
     * 自定义Toast
     */

    enum class CustomToast {

        @SuppressLint("StaticFieldLeak")
        INSTANCE;

        private var toast: Toast? = null
        private var mTvToast: TextView? = null

        private fun showToast(context: String) {
            if (toast == null) {
                toast = Toast(ConfigUtils.getAppCtx())
                toast?.duration = Toast.LENGTH_SHORT
                val root =
                        LayoutInflater.from(ConfigUtils.getAppCtx()).inflate(R.layout.item_toast_custom_common, null, false)
                mTvToast = root.findViewById(R.id.tvCustomToast)
                toast?.view = root
            }

            mTvToast?.text = context
            toast?.show()
        }

        fun showToast(stringId: Int) {
            showToast(ConfigUtils.getAppCtx().getString(stringId))
        }

        fun cancelToast() {
            if (toast != null) {
                toast?.cancel()
                toast = null
                mTvToast = null
            }
        }
    }
}