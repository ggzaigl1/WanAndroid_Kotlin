package com.example.gab.kotlin.bean

/**
 * Created by 初夏小溪
 * data :2019/1/7 0007 11:32
 */
data class BannerBean(
        var desc: String,
        var id: Int = 0,
        var imagePath: String,
        var isVisible: Int = 0,
        var order: Int = 0,
        var title: String,
        var type: Int = 0,
        var url: String)

/**
 * desc : 一起来做个App吧
 * id : 10
 * imagePath : http://www.wanandroid.com/blogimgs/50c115c2-cf6c-4802-aa7b-a4334de444cd.png
 * isVisible : 1
 * order : 0
 * title : 一起来做个App吧
 * type : 0
 * url : http://www.wanandroid.com/blog/show/2
 */