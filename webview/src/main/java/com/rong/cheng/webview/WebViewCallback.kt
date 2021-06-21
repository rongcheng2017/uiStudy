package com.rong.cheng.webview


/**
 * @author: frc
 * @description:
 * @date:  2021/6/18 10:10 上午
 *
 */
interface WebViewCallback {

    fun pageStarted(url: String?)

    fun pageFinished(url: String?)


    fun onError(url: String?, error: String)

    fun updateTitle(title: String?)


}