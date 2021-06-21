package com.rong.cheng.common

import com.kingja.loadsir.core.LoadSir
import com.rong.cheng.base.BaseApplication
import com.rong.cheng.common.loadsir.callback.*


/**
 * @author: frc
 * @description:
 * @date:  2021/6/18 9:39 上午
 *
 */
open class CommonApplication : BaseApplication() {
    override fun onCreate() {
        super.onCreate()
        LoadSir.beginBuilder()
            .addCallback(ErrorCallback()) //添加各种状态页
            .addCallback(EmptyCallback())
            .addCallback(LoadingCallback())
            .addCallback(TimeoutCallback())
            .addCallback(CustomCallback())
            .setDefaultCallback(LoadingCallback::class.java) //设置默认状态页
            .commit()
    }
}