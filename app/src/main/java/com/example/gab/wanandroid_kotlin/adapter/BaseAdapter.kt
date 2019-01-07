package com.example.gab.wanandroid_kotlin.adapter

import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatImageView
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.gab.wanandroid_kotlin.R
import com.example.gab.wanandroid_kotlin.bean.BaseBean
import com.ggz.baselibrary.utils.ResourceUtils

/**
 * Created by 初夏小溪
 * data :2019/1/7 0007 16:49
 */
class BaseAdapter(data: List<BaseBean.DatasBean>) : BaseQuickAdapter<BaseBean.DatasBean, BaseViewHolder>(R.layout.item_base_list, data) {

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int, payloads: List<Any>) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            val dataBean = mData[position]
            initImage(dataBean, holder.getView(R.id.image_collect))
        }
    }

    override fun convert(helper: BaseViewHolder, item: BaseBean.DatasBean) {
        val mTitle = helper.getView<TextView>(R.id.tv_title)
        val mTag = helper.getView<TextView>(R.id.tv_tag)
        val mFresh = helper.getView<TextView>(R.id.tv_fresh)

        helper.setText(R.id.tv_title, item.title)
            .setText(R.id.tv_author_name, "作者：" + item.author)
            .setTextColor(R.id.tv_author_name, ResourceUtils.getRandomColor())
            .setText(R.id.tv_date, item.niceDate)
            .setText(R.id.tv_chapterName, "分类：" + item.chapterName)
            .setTextColor(R.id.tv_chapterName, ResourceUtils.getRandomColor())

        //判断是否有图片
        if (TextUtils.isEmpty(item.envelopePic)) {
            helper.getView<View>(R.id.Ll_item_pic).visibility = View.GONE
        } else {
            helper.getView<View>(R.id.Ll_item_pic).visibility = View.VISIBLE
            val options = RequestOptions()
                .override(100, 200)
            Glide.with(mContext)
                .load(item.envelopePic)
                .apply(options)
                .into(helper.getView<View>(R.id.item_list_iv) as AppCompatImageView)
            //            ImgLoadUtils.loadImage(mContext, item.getEnvelopePic(), helper.getView(R.id.item_list_iv));
        }

        //判断tag对象是否有数据
        if (item.tags?.size !== 0 && !item.tags?.isEmpty()!!) {
            mTag.visibility = View.VISIBLE
            mTag.text = item.tags?.get(0)?.name
        } else {
            mTag.visibility = View.GONE
        }

        //判断是否是最新的item
        if (item.isFresh) {
            mFresh.visibility = View.VISIBLE
        } else {
            mFresh.visibility = View.GONE
        }

        val title = item.title
        //"Android <em class='highlight'>Studio3</em>.0正式版填坑路
        //如果包含 截取返回第一次出现的字符串
        if (title!!.contains("<em")) {
            //返回指定字符在字符串中第一次出现处的索引，如果此字符串中没有这样的字符，则返回 -1。
            val startPre = title.indexOf("<")
            // substring() 方法返回字符串的子字符串 截取。
            // beginIndex -- 起始索引（包括）, 索引从 0 开始。
            // endIndex -- 结束索引（不包括）。
            val pre = title.substring(0, startPre)
            val startNext = title.indexOf(">")
            //截取返回最后一次出现
            //返回指定字符在此字符串中最后一次出现处的索引，如果此字符串中没有这样的字符，则返回 -1。
            val endPre = title.lastIndexOf("<")
            val centerStr = title.substring(startNext + 1, endPre)
            val end = title.lastIndexOf(">")
            //拼接
            val endStr = title.substring(end + 1, title.length)
            mTitle.text = pre + centerStr + endStr
        } else {
            mTitle.text = title
        }

        helper.addOnClickListener(R.id.image_collect)
        initImage(item, helper.getView(R.id.image_collect))

    }

    private fun initImage(baseBean: BaseBean.DatasBean, imageView: AppCompatImageView) {
        if (baseBean.isCollect) {
            imageView.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.vector_collect))
        } else {
            imageView.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.vector_collect_false))

        }
    }
}