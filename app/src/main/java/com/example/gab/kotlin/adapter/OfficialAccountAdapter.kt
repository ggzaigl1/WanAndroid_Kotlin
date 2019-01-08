package com.example.gab.kotlin.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.gab.kotlin.R
import com.example.gab.kotlin.bean.OfficialAccountBean
import com.ggz.baselibrary.utils.ResourceUtils

/**
 * Created by 初夏小溪
 * data :2019/1/7 0007 16:10
 */
class OfficialAccountAdapter(data : List<OfficialAccountBean>) : BaseQuickAdapter<OfficialAccountBean,BaseViewHolder>(R.layout.item_official_account,data){

    override fun convert(helper: BaseViewHolder?, item: OfficialAccountBean?) {
        helper?.setText(R.id.tv_name,item?.name)
                ?.setTextColor(R.id.tv_name,ResourceUtils.getRandomColor())
    }
}