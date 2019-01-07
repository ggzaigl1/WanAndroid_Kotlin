package com.example.gab.wanandroid_kotlin.bean

import java.io.Serializable

/**
 * Created by 初夏小溪
 * data :2019/1/7 0007 11:34
 */
class BookmarkBean : Serializable {

    /**
     * icon :
     * id : 17
     * link : http://www.wanandroid.com/article/list/0?cid=176
     * name : 国内大牛博客集合
     * order : 1
     * visible : 1
     */

    var icon: String? = null
    var id: Int = 0
    var link: String? = null
    var name: String? = null
    var order: Int = 0
    var visible: Int = 0
}