package com.example.gab.kotlin.ui.fragment

import android.view.View
import com.example.gab.kotlin.R
import com.example.gab.kotlin.base.BaseFragment
import com.ggz.baselibrary.utils.ToastUtils
import kotlinx.android.synthetic.main.fragment_list_floab.*
import org.jetbrains.anko.onClick

/**
 * Created by 初夏小溪
 * data :2019/1/7 0007 15:12
 */
class StarFragment : BaseFragment() {
    override fun isLazyLoad(): Boolean {
        return false
    }

    override fun initView(view: View?) {
    }

    override fun setContentLayout(): Int {
        return R.layout.fragment_list_floab
    }

    override fun initData() {
        fab_top.onClick {
            ToastUtils.showShort("好嗨哦")
        }
    }
}