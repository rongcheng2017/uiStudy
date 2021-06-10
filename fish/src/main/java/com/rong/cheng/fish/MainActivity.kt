package com.rong.cheng.fish

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fish = findViewById<ImageView>(R.id.iv_fish)
        fish.setImageDrawable(FishDrawable())

    }
}