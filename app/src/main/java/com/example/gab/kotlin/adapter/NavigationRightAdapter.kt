package com.example.gab.kotlin.adapter

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.gab.kotlin.R
import com.example.gab.kotlin.bean.NavigationBean
import com.example.gab.kotlin.utils.addCommonView
import com.example.gab.kotlin.web.WebViewActivity
import com.ggz.baselibrary.utils.ResourceUtils
import com.google.android.flexbox.FlexboxLayout
import kotlinx.android.synthetic.main.fragment_navi_detail.*

/**
 * Created by 初夏小溪
 * data :2019/1/10 0010 16:04
 */
class NavigationRightAdapter(navigationBean: List<NavigationBean.ArticlesBean>) : BaseQuickAdapter<NavigationBean.ArticlesBean, BaseViewHolder>(R.layout.item_navigation_right, navigationBean) {

    private lateinit var articles: ArrayList<NavigationBean.ArticlesBean>

    fun isCountlateinit() = ::articles.isInitialized

    override fun convert(helper: BaseViewHolder, item: NavigationBean.ArticlesBean) {
//        val navDetailFL = helper.getView<FlexboxLayout>(R.id.navDetailFL)

//        if (isCountlateinit()) {
//            for (website in articles) {
//                navDetailFL.addCommonView(mContext, website.title!!, R.color.button_normals, R.drawable.selector_item_bg) {
//                    WebViewActivity.startWebActivity(mContext, website.link!!)
//                }
//            }
//        }

        helper.setText(R.id.tv_date, item.title)
                .setTextColor(R.id.tv_date, ResourceUtils.getRandomColor())
        val tvData = helper.getView<TextView>(R.id.tv_date)
        tvData.run {
            text = item.title
            setTextColor(ResourceUtils.getRandomColor())
        }
    }
}