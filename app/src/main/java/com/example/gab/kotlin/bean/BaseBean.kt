package com.example.gab.kotlin.bean

import java.io.Serializable

/**
 * Created by 初夏小溪
 * data :2019/1/7 0007 11:33
 */
class BaseBean : Serializable {

    /**
     * curPage : 2
     * datas : [{"apkLink":"","author":"code小生","chapterId":414,"chapterName":"code小生","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":7465,"link":"https://mp.weixin.qq.com/s/quhz5TsboBQPa2BtEB-v6w","niceDate":"2018-11-05","origin":"","projectLink":"","publishTime":1541347200000,"superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/414/1"}],"title":"基于 opencv 实现人脸检测","type":0,"userId":-1,"visible":1,"zan":0}
     * offset : 20
     * over : false
     * pageCount : 283
     * size : 20
     * total : 5658
     */

    var curPage: Int = 0
    var offset: Int = 0
    var isOver: Boolean = false
    var pageCount: Int = 0
    var size: Int = 0
    var total: Int = 0
    var datas: List<DatasBean>? = null

    class DatasBean : Serializable {
        /**
         * apkLink :
         * author : code小生
         * chapterId : 414
         * chapterName : code小生
         * collect : false
         * courseId : 13
         * desc :
         * envelopePic :
         * fresh : false
         * id : 7465
         * link : https://mp.weixin.qq.com/s/quhz5TsboBQPa2BtEB-v6w
         * niceDate : 2018-11-05
         * origin :
         * projectLink :
         * publishTime : 1541347200000
         * superChapterId : 408
         * superChapterName : 公众号
         * tags : [{"name":"公众号","url":"/wxarticle/list/414/1"}]
         * title : 基于 opencv 实现人脸检测
         * type : 0
         * userId : -1
         * visible : 1
         * zan : 0
         */

        var apkLink: String? = null
        var author: String? = null
        var chapterId: Int = 0
        var chapterName: String? = null
        var isCollect: Boolean = false
        var courseId: Int = 0
        var desc: String? = null
        var envelopePic: String? = null
        var isFresh: Boolean = false
        var id: Int = 0
        var link: String? = null
        var niceDate: String? = null
        var origin: String? = null
        var projectLink: String? = null
        var publishTime: Long = 0
        var superChapterId: Int = 0
        var superChapterName: String? = null
        var title: String? = null
        var type: Int = 0
        var userId: Int = 0
        var visible: Int = 0
        var zan: Int = 0
        var top: String? = null
        var isClick: Boolean = false
        var tags: List<TagsBean>? = null

        class TagsBean : Serializable {
            /**
             * name : 公众号
             * url : /wxarticle/list/414/1
             */

            var name: String? = null
            var url: String? = null
        }
    }
}