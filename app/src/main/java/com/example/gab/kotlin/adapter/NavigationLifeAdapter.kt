package com.example.gab.kotlin.adapter

import android.support.v4.content.ContextCompat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.gab.kotlin.R
import com.example.gab.kotlin.bean.NavigationBean

/**
 * Created by 初夏小溪
 * data :2019/1/10 0010 15:39
 */
class NavigationLifeAdapter(navigationBean: List<NavigationBean>) : BaseQuickAdapter<NavigationBean, BaseViewHolder>(R.layout.item_navigation, navigationBean) {

    override fun convert(helper: BaseViewHolder, item: NavigationBean) {

        if (item.isSelected) {
            helper.setText(R.id.tv_date, item.name)
                    .setBackgroundColor(R.id.tv_date, ContextCompat.getColor(mContext, R.color.white))
                    .setTextColor(R.id.tv_date, ContextCompat.getColor(mContext, R.color.shallow_green))
        } else {
            helper.setText(R.id.tv_date, item.name)
                    .setBackgroundColor(R.id.tv_date, ContextCompat.getColor(mContext, R.color.deep_grey))
                    .setTextColor(R.id.tv_date, ContextCompat.getColor(mContext, R.color.black))
        }
    }
}