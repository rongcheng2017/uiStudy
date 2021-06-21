package com.rong.cheng.webview.webviewprocess.webchromeclient

import android.util.Log
import android.webkit.ConsoleMessage
import android.webkit.WebChromeClient
import android.webkit.WebView
import com.rong.cheng.webview.WebViewCallback

/**
 * @author: frc
 * @description:
 * @date:  2021/6/18 2:52 下午
 *
 */
class DefaultWebChromeClient(private val callback: WebViewCallback?) : WebChromeClient() {

    companion object {
        private const val TAG: String = "WEB_CONSOLE_MESSAGE"
    }

    override fun onReceivedTitle(view: WebView?, title: String?) {
        super.onReceivedTitle(view, title)
        callback?.updateTitle(title)
    }

    override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
        Log.d(TAG, consoleMessage?.message() ?: "")
        return super.onConsoleMessage(consoleMessage)

    }
}