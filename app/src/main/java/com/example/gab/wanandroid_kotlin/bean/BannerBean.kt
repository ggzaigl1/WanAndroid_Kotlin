package com.example.gab.wanandroid_kotlin.bean

import java.io.Serializable

/**
 * Created by 初夏小溪
 * data :2019/1/7 0007 11:32
 */
class BannerBean : Serializable {

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

    var desc: String? = null
    var id: Int = 0
    var imagePath: String? = null
    var isVisible: Int = 0
    var order: Int = 0
    var title: String? = null
    var type: Int = 0
    var url: String? = null
}