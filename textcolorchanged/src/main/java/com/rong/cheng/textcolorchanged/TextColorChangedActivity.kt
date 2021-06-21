package com.rong.cheng.textcolorchanged

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.rong.cheng.textcolorchanged.viewpager.ViewPagerActivity

class TextColorChangedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_color_changed)
        findViewById<View>(R.id.view_pager).setOnClickListener {
            startActivity(Intent(this, ViewPagerActivity::class.java))
        }

        findViewById<View>(R.id.text).setOnClickListener {
            startActivity(Intent(this, TextActivity::class.java))
        }
    }
}