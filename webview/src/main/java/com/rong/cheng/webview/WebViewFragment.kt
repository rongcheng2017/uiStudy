package com.rong.cheng.webview

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.kingja.loadsir.callback.Callback
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.rong.cheng.common.CAN_NATIVE_REFRESH
import com.rong.cheng.common.URL
import com.rong.cheng.common.loadsir.callback.ErrorCallback
import com.rong.cheng.common.loadsir.callback.LoadingCallback
import com.rong.cheng.webview.databinding.FragmentWebviewBinding
import com.rong.cheng.webview.webviewprocess.webchromeclient.DefaultWebChromeClient
import com.rong.cheng.webview.webviewprocess.websetting.DefaultWebSetting
import com.rong.cheng.webview.webviewprocess.webviewclient.DefaultWebViewClient

/**
 * @author: frc
 * @description:
 * @date:  2021/6/17 8:14 下午
 *
 */
class WebViewFragment : Fragment(), WebViewCallback,
    SwipeRefreshLayout.OnRefreshListener {
    private var mIsError = false;
    private lateinit var mUrl: String
    private var canNativeRefresh: Boolean = false
    private lateinit var binding: FragmentWebviewBinding
    private var mLoadService: LoadService<Callback>? = null

    companion object {
        private const val TAG = "WebView"
        fun newInstance(url: String, canNativeRefresh: Boolean): WebViewFragment {
            val fragment = WebViewFragment()
            val bundle = Bundle()
            bundle.putString(URL, url)
            bundle.putBoolean(CAN_NATIVE_REFRESH, canNativeRefresh)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            mUrl = getString(URL) ?: ""
            canNativeRefresh = getBoolean(CAN_NATIVE_REFRESH)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate<FragmentWebviewBinding>(inflater,
            R.layout.fragment_webview,
            container,
            false)
        binding.webView.registerWebViewCallback(this)
        binding.webView.loadUrl(mUrl)

        binding.refreshLayout.setOnRefreshListener(this)

        mLoadService = LoadSir.getDefault().register(binding.refreshLayout) {
            mLoadService?.showCallback(LoadingCallback::class.java)
            binding.webView.reload()
        } as LoadService<Callback>?

        return mLoadService?.loadLayout
    }

    override fun pageStarted(url: String?) {
        mLoadService?.showCallback(LoadingCallback::class.java)
    }

    override fun pageFinished(url: String?) {
        if (mIsError) {
            binding.refreshLayout.isEnabled = true
            mLoadService?.showCallback(ErrorCallback::class.java)
        } else {
            binding.refreshLayout.isEnabled = canNativeRefresh
            mLoadService?.showSuccess()
        }
        mIsError = false
    }

    override fun onError(
        url: String?,
        error: String,
    ) {
        //错误完了还会调finish
        mIsError = true
        binding.refreshLayout.isRefreshing = false
        Log.e(TAG, error)
    }

    override fun updateTitle(title: String?) {
        title?.apply {
            if (this@WebViewFragment.activity is WebViewActivity) {
                (this@WebViewFragment.activity as WebViewActivity).updateTitle(this)
            }
        }
    }


    override fun onRefresh() {
        binding.webView.reload()
    }
}