package com.rong.cheng.base

import android.app.Application

/**
 * @author: frc
 * @description:
 * @date:  2021/6/17 8:46 下午
 *
 */
open class BaseApplication : Application() {
    companion object {
       lateinit var sApplication: Application
    }

    override fun onCreate() {
        super.onCreate()
        sApplication = this
    }
}