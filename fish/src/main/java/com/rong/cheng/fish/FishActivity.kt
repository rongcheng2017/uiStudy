package com.rong.cheng.fish

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.rong.cheng.base.AutoServiceManager
import com.rong.cheng.common.autoservice.IWebViewService
import java.util.*

class FishActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fish)
        val fish = findViewById<ImageView>(R.id.iv_fish)
        fish.setImageDrawable(FishDrawable())
        fish.setOnClickListener {
            AutoServiceManager.load(IWebViewService::class.java).
                startDemoHtml(this)
        }

    }
}