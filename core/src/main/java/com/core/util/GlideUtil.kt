package com.core.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.core.R

/**
 *  @ProjectName: core2024
 *  @Package: com.core.util
 *  @Author: lu
 *  @CreateDate: 2024/2/19 13:53
 *  @Des:
 */
class GlideUtil {
    //加载图片，开启缓存
    fun ImageView.setUrl(url: String?) {
        Glide.with(context).load(url)
            .placeholder(R.mipmap.default_img) // 占位符，异常时显示的图片
            .error(R.mipmap.default_img) // 错误时显示的图片
            .skipMemoryCache(false) //启用内存缓存
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE) //磁盘缓存策略
            .into(this)
    }

    //加载圆形图片
    fun ImageView.setUrlCircle(url: String?) {
        Glide.with(context).load(url)
            .placeholder(R.mipmap.default_head)
            .error(R.mipmap.default_head)
            .skipMemoryCache(false) //启用内存缓存
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .transform(CenterCrop()) // 圆形
            .into(this)
    }

    //加载圆角图片
    fun ImageView.setUrlRound(url: String?, radius: Int = 10) {
        Glide.with(context).load(url)
            .placeholder(R.mipmap.default_img)
            .error(R.mipmap.default_img)
            .skipMemoryCache(false) // 启用内存缓存
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .transform(CenterCrop(), RoundedCorners(radius))
            .into(this)
    }

    //加载Gif图片
    fun ImageView.setUrlGif(url: String?) {
        Glide.with(context).asGif().load(url)
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .placeholder(R.mipmap.default_img)
            .error(R.mipmap.default_img)
            .into(this)
    }
}