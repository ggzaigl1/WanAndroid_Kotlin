package com.example.gab.wanandroid_kotlin.ui.fragment

import android.view.View
import com.example.gab.wanandroid_kotlin.R
import com.example.gab.wanandroid_kotlin.base.BaseFragment

/**
 * Created by 初夏小溪
 * data :2019/1/7 0007 15:12
 */
class OfficialAccountFragment : BaseFragment() {
    override fun isLazyLoad(): Boolean {
        return false
    }

    override fun initView(view: View?) {
    }

    override fun setContentLayout(): Int {
        return R.layout.activity_official_account
    }

    override fun initData() {
    }
}