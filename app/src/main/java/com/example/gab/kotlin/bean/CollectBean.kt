package com.example.gab.kotlin.bean

import java.io.Serializable

/**
 * Created by 初夏小溪
 * data :2019/1/7 0007 11:34
 */
class CollectBean : Serializable {

    /**
     * curPage : 1
     * datas : [{"author":"Shawn_Dut","chapterId":98,"chapterName":"WebView","courseId":13,"desc":"","envelopePic":"","id":9369,"link":"https://juejin.im/post/58a037df86b599006b3fade4","niceDate":"1小时前","origin":"","originId":2837,"publishTime":1524536947000,"title":"android WebView详解，常见漏洞详解和安全源码","userId":3555,"visible":0,"zan":0},{"author":"LiangLuDev","chapterId":294,"chapterName":"完整项目","courseId":13,"desc":"注册登录、用户信息、用户密码、用户图像修改、书籍分类、本地书籍扫描、书架、书籍搜索（作者名或书籍名）、书籍阅读（仅txt格式，暂不支持PDF等其他格式）、阅读字体、背景颜色、翻页效果等设置、意见反馈（反馈信息发送到我的邮箱）、应用版本更新","envelopePic":"http://www.wanandroid.com/blogimgs/fab6fb8b-c3aa-495f-b6a9-c007d78751c0.gif","id":9370,"link":"http://www.wanandroid.com/blog/show/2116","niceDate":"1小时前","origin":"","originId":2836,"publishTime":1524536947000,"title":"微Yue电子书阅读 WeYueReader","userId":3555,"visible":0,"zan":0},{"author":"小编","chapterId":352,"chapterName":"资讯","courseId":13,"desc":"","envelopePic":"","id":9368,"link":"https://www.oschina.net/openapi","niceDate":"1小时前","origin":"","originId":2842,"publishTime":1524536914000,"title":"开源中国oschina API","userId":3555,"visible":0,"zan":0},{"author":"滑板上的老砒霜 ","chapterId":135,"chapterName":"二维码","courseId":13,"desc":"","envelopePic":"","id":9367,"link":"https://www.jianshu.com/p/a4ba10da4231","niceDate":"1小时前","origin":"","originId":2867,"publishTime":1524536912000,"title":"Android扫一扫：zxing的集成与优化","userId":3555,"visible":0,"zan":0}]
     * offset : 0
     * over : true
     * pageCount : 1
     * size : 20
     * total : 4
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
         * author : Shawn_Dut
         * chapterId : 98
         * chapterName : WebView
         * courseId : 13
         * desc :
         * envelopePic :
         * id : 9369
         * link : https://juejin.im/post/58a037df86b599006b3fade4
         * niceDate : 1小时前
         * origin :
         * originId : 2837
         * publishTime : 1524536947000
         * title : android WebView详解，常见漏洞详解和安全源码
         * userId : 3555
         * visible : 0
         * zan : 0
         */

        var author: String? = null
        var chapterId: Int = 0
        var chapterName: String? = null
        var courseId: Int = 0
        var desc: String? = null
        var envelopePic: String? = null
        var id: Int = 0
        var isCollect = true//设置默认值true （方便 个人收藏界面使用）

        var link: String? = null
        var niceDate: String? = null
        var origin: String? = null
        var originId: Int = 0
        var publishTime: Long = 0
        var title: String? = null
        var userId: Int = 0
        var visible: Int = 0
        var zan: Int = 0
    }
}