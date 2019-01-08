package com.example.gab.kotlin.ui.fragment

import android.view.View
import com.example.gab.kotlin.R
import com.example.gab.kotlin.base.BaseFragment

/**
 * Created by 初夏小溪
 * data :2019/1/7 0007 15:12
 */
class NavigationViewFragment : BaseFragment() {

    override fun isLazyLoad(): Boolean {
        return false
    }

    override fun initView(view: View?) {
    }

    override fun setContentLayout(): Int {
        return R.layout.fragment_navigation
    }

    override fun initData() {
    }
}