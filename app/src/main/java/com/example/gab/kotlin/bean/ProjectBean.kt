package com.example.gab.kotlin.bean

import java.io.Serializable

/**
 * Created by 初夏小溪
 * data :2019/1/7 0007 11:37
 */
class ProjectBean : Serializable {

    /**
     * children : []
     * courseId : 13
     * id : 294
     * name : 完整项目
     * order : 145000
     * parentChapterId : 293
     * visible : 0
     */

    var courseId: Int = 0
    var id: Int = 0
    var name: String? = null
    var order: Int = 0
    var parentChapterId: Int = 0
    var visible: Int = 0
    var children: List<*>? = null
    var isSelected: Boolean = false
}