package com.rong.cheng.common.autoservice

import android.content.Context
import androidx.fragment.app.Fragment

/**
 * @author: frc
 * @description:
 * @date:  2021/6/17 10:48 上午
 *
 */
interface IWebViewService {
    /**
     * @param url : 访问的web地址
     * @param isShowActionBar : 是否显示actionBar
     */
    fun startWebView(context: Context, url: String, title: String, isShowActionBar: Boolean)


    fun getWebViewFragment(url:String,canNativeRefresh:Boolean):Fragment


    fun startDemoHtml(context: Context)
}