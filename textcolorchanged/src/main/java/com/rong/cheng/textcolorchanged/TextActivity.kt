package com.rong.cheng.textcolorchanged

import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class TextActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text)
        val textView = findViewById<SimpleColorChangeTextView>(R.id.text)
        val animator = ObjectAnimator.ofFloat(textView, "textPercent", 0f,1f)
        animator.duration = 2000
        animator.start()

    }
}