package com.example.gab.wanandroid_kotlin.bean

/**
 * Created by 初夏小溪
 * data :2019/1/7 0007 11:37
 */
class UpDateBean {

    /**
     * result : 操作成功
     * lesprint_uuid_api : fc20854d-ebd4-4e46-9bbd-6fed15397ef4
     * resource : https://api.lccfd.51sprint.com/lesprint/
     * CONTEXT_PATH : /lesprint
     * version : {"content":"","down_url":"http://sprintfd.oss-cn-hangzhou.aliyuncs.com/apk/ldz_2.3.2.apk","is_force_update":false,"version":"2.3.2"}
     * status : 0
     */

    var result: String? = null
    var lesprint_uuid_api: String? = null
    var resource: String? = null
    var contexT_PATH: String? = null
    var version: VersionBean? = null
    var status: Int = 0

    class VersionBean {
        /**
         * content :
         * down_url : http://sprintfd.oss-cn-hangzhou.aliyuncs.com/apk/ldz_2.3.2.apk
         * is_force_update : false
         * version : 2.3.2
         */

        var content: String? = null
        var down_url: String? = null
        var isIs_force_update: Boolean = false
        var version: String? = null
    }
}