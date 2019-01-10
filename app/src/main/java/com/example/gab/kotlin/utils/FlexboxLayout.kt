package com.example.gab.kotlin.utils

import android.content.Context
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ggz.baselibrary.utils.DensityUtils
import com.google.android.flexbox.FlexboxLayout

/**
 * Created by 初夏小溪
 * data :2019/1/10 0010 16:28
 */
fun FlexboxLayout.addCommonView(context: Context,
                                name: String, // 名称
                                @ColorRes textColor: Int, // 字体颜色
                                @DrawableRes drawable: Int, // 背景
                                append: Boolean = true, // 添加方式
                                viewClick: (View) -> Unit) { // 点击事件

    val view = TextView(context)
    view.setOnClickListener(viewClick)
    view.text = name
    view.setTextColor(context.resources.getColor(textColor))
    view.background = context.resources.getDrawable(drawable)
    val padding1 = DensityUtils.dp2px(context, 10f)
    val padding2 = DensityUtils.dp2px(context, 5f)
    view.setPadding(padding1, padding2, padding1, padding2)
    val params = FlexboxLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    val margin1 = DensityUtils.dp2px(context, 6f)
    val margin2 = DensityUtils.dp2px(context, 10f)
    params.setMargins(margin2, margin1, margin2, margin1)
    view.layoutParams = params
    if (append) {
        this.addView(view)
    } else {
        this.addView(view, 0)
    }
}