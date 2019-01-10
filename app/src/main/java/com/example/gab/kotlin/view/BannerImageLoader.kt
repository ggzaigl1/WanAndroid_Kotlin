package com.example.gab.kotlin.view

import android.content.Context
import android.widget.ImageView
import com.youth.banner.loader.ImageLoader

/**
 * Created by 初夏小溪
 * data :2019/1/10 0010 11:18
 */
class BannerImageLoader : ImageLoader() {
    override fun displayImage(context: Context, path: Any?, imageView: ImageView) {
        ImageUtlis.load(context, path as String, imageView)
    }
}