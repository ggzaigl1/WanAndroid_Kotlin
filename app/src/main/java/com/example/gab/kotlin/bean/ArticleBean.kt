package com.example.gab.kotlin.bean

import com.google.gson.annotations.SerializedName

/**
 * Created by 初夏小溪
 * data :2019/1/10 0010 13:59
 */
data class ArticleBean(
        val over: Boolean = false,
        val pageCount: Int = 0,
        val total: Int = 0,
        val curPage: Int = 0,
        val offset: Int = 0,
        val size: Int = 0,
        val datas: List<DatasItem>
)