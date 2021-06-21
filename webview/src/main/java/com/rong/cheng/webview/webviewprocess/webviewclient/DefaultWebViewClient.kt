package com.rong.cheng.webview.webviewprocess.webviewclient

import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Build
import android.webkit.*
import com.rong.cheng.webview.WebViewCallback

/**
 * @author: frc
 * @description:
 * @date:  2021/6/18 10:08 上午
 *
 */
class DefaultWebViewClient(private val callback: WebViewCallback?) : WebViewClient() {


    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        callback?.pageStarted(url)
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        callback?.pageFinished(url)
    }

    override fun onReceivedHttpError(
        view: WebView?,
        request: WebResourceRequest?,
        errorResponse: WebResourceResponse?,
    ) {
        super.onReceivedHttpError(view, request, errorResponse)
        callback?.onError(request?.url?.toString(), errorResponse?.statusCode?.toString() ?: "")
    }

    override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
        super.onReceivedSslError(view, handler, error)
        callback?.onError(error?.url ?: "", error?.primaryError?.toString() ?: "")
    }

    override fun onReceivedError(
        view: WebView?,
        request: WebResourceRequest,
        error: WebResourceError,
    ) {
        super.onReceivedError(view, request, error)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            callback?.onError(request.url.toString(), error.description.toString())
        } else {
            callback?.onError(request.url.toString(), "error")
        }
    }

}