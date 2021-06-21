package com.rong.cheng.webview.webviewprocess

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.widget.Toast
import com.google.gson.Gson
import com.rong.cheng.webview.WebViewCallback
import com.rong.cheng.webview.bean.JsParam
import com.rong.cheng.webview.webviewprocess.webchromeclient.DefaultWebChromeClient
import com.rong.cheng.webview.webviewprocess.websetting.DefaultWebSetting
import com.rong.cheng.webview.webviewprocess.webviewclient.DefaultWebViewClient

/**
 * @author: frc
 * @description:
 * @date:  2021/6/18 3:45 下午
 *
 */
class BaseWebView : WebView {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context,
        attrs,
        defStyleAttr, 0)

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int,
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        init()
    }

    private fun init() {
        WebViewProcessCommandDispatcher.get().initAidlConnection()
        DefaultWebSetting.newInstance().setSettings(this)
        addJavascriptInterface(this, "f_web_view")
    }

    fun registerWebViewCallback(callback: WebViewCallback) {
        webViewClient = DefaultWebViewClient(callback)
        webChromeClient = DefaultWebChromeClient(callback)
    }

    @JavascriptInterface
    fun takeNativeAction(jsParams: String?) {
        jsParams?.apply {
            val jsParamObject = Gson().fromJson(jsParams, JsParam::class.java)
            if (jsParamObject != null) {
                    WebViewProcessCommandDispatcher.get()
                        .executeCommand(jsParamObject.name, Gson().toJson(jsParamObject.param))
            }
        }
    }

}