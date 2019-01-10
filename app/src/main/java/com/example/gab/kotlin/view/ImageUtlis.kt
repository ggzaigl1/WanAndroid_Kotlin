package com.example.gab.kotlin.view

import android.content.Context
import android.support.v7.widget.AppCompatImageView
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions

/**
 * Created by 初夏小溪
 * data :2019/1/10 0010 11:19
 */
class ImageUtlis {
    companion object {
        fun load(context: Context, url: String, imageView: ImageView) {
            Glide.with(context)
                    .load(url)
                    .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                    .transition(withCrossFade())
                    .into(imageView)
        }
    }
}