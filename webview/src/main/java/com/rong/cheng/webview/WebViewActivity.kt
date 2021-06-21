package com.rong.cheng.webview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import com.rong.cheng.base.AutoServiceManager
import com.rong.cheng.base.BaseApplication
import com.rong.cheng.common.IS_SHOW_ACTION_BAR
import com.rong.cheng.common.TITLE
import com.rong.cheng.common.URL
import com.rong.cheng.common.autoservice.IWebViewService
import com.rong.cheng.webview.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {
    lateinit var binding: ActivityWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_web_view)
        val url = intent.getStringExtra(URL) ?: ""
        val titleStr = intent.getStringExtra(TITLE)
        val showActionBar = intent.getBooleanExtra(IS_SHOW_ACTION_BAR, true)

        binding.title.text = titleStr
        binding.actionBar.visibility = if (showActionBar) View.VISIBLE else View.GONE
        binding.back.setOnClickListener {
            this.finish()
        }

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.web_view_fragment, WebViewFragment.newInstance(url, true))
        transaction.commit()

    }


    fun updateTitle(title: String) {
        binding.title.text = title
    }

}