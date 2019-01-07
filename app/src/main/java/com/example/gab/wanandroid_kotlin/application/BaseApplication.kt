package com.example.gab.wanandroid_kotlin.application

import android.app.Application
import android.support.v7.app.AppCompatDelegate
import com.ggz.baselibrary.R
import com.ggz.baselibrary.application.BaseActivityLifecycleCallbacks
import com.ggz.baselibrary.retrofit.ioc.ConfigUtils
import com.ggz.baselibrary.utils.NightModeConfig

/**
 * Created by 初夏小溪
 * data :2019/1/7 0007 16:32
 */
class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ConfigUtils.ConfigBiuder()
            .setBgColor(R.color.colorPrimary)
            //                .setTitleColor(R.color.red)
            //                .setTitleCenter(true)
            //                .setCer(CER)
            .setBASE_URL("http://www.wanandroid.com/")
            .create(this)

//        设置activity 生命周期回调
        registerActivityLifecycleCallbacks(BaseActivityLifecycleCallbacks())

        //根据app上次退出的状态来判断是否需要设置夜间模式,提前在SharedPreference中存了一个是否是夜间模式的boolean值
        val isNightMode = NightModeConfig.getInstance().getNightMode(applicationContext)
        if (isNightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}