package com.example.gab.kotlin.bean

/**
 * Created by 初夏小溪
 * data :2019/1/10 0010 13:59
 */
data class DatasItem (
        val superChapterName: String = "",
        val publishTime: Long = 0,
        val visible: Int = 0,
        val niceDate: String = "",
        val projectLink: String = "",
        val author: String = "",
        val zan: Int = 0,
        val origin: String = "",
        val originId: Int = 0,
        val chapterName: String = "",
        val link: String = "",
        val title: String = "",
        val type: Int = 0,
        val userId: Int = 0,
        val tags: List<TagsItem>,
        val apkLink: String = "",
        val envelopePic: String = "",
        val chapterId: Int = 0,
        val superChapterId: Int = 0,
        val id: Int = 0,
        val fresh: Boolean = false,
        var collect: Boolean = false,
        val courseId: Int = 0,
        val desc: String = ""
)