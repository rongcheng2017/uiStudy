package com.rong.cheng.webview

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import com.google.auto.service.AutoService
import com.rong.cheng.common.ANDROID_ASSET_URI
import com.rong.cheng.common.IS_SHOW_ACTION_BAR
import com.rong.cheng.common.autoservice.IWebViewService
import com.rong.cheng.common.TITLE
import com.rong.cheng.common.URL

/**
 * @author: frc
 * @description:
 * @date:  2021/6/17 6:20 下午
 *
 */

@AutoService(IWebViewService::class)
class WebViewServiceImpl : IWebViewService {
    override fun startWebView(
        context: Context,
        url: String,
        title: String,
        isShowActionBar: Boolean,
    ) {

        val intent = Intent(context, WebViewActivity::class.java)
        intent.putExtra(URL, url)
        intent.putExtra(TITLE, title)
        intent.putExtra(IS_SHOW_ACTION_BAR, isShowActionBar)
        context.startActivity(intent)

    }

    override fun getWebViewFragment(url: String, canNativeRefresh: Boolean): Fragment {
        return WebViewFragment.newInstance(url, canNativeRefresh)
    }

    override fun startDemoHtml(context: Context) {
        val intent = Intent(context, WebViewActivity::class.java)
        intent.putExtra(TITLE, "本地Demo测试页")
        intent.putExtra(URL, ANDROID_ASSET_URI + "demo.html")
        intent.putExtra(IS_SHOW_ACTION_BAR, true)
        context.startActivity(intent)
    }

}