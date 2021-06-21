package com.rong.cheng.uistudy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.rong.cheng.lottie.LottieActivity
import com.rong.cheng.nestedscroll.NestedScrollViewActivity
import com.rong.cheng.photoview.PhotoView
import com.rong.cheng.photoview.PhotoViewActivity
import com.rong.cheng.recyclerview.RecyclerViewActivity
import com.rong.cheng.textcolorchanged.TextColorChangedActivity
import com.rong.cheng.uistudy.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.recyclerView.setOnClickListener(this)
        binding.nestedScrollView.setOnClickListener(this)
        binding.textColorChange.setOnClickListener(this)
        binding.lottie.setOnClickListener(this)
        binding.photoView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.recyclerView -> {
                startActivity(Intent(this@MainActivity, RecyclerViewActivity::class.java))
            }
            binding.nestedScrollView -> {
                startActivity(Intent(this, NestedScrollViewActivity::class.java))
            }
            binding.textColorChange -> {
                startActivity(Intent(this, TextColorChangedActivity::class.java))
            }
            binding.lottie -> {
                startActivity(Intent(this, LottieActivity::class.java))
            }
            binding.photoView -> {
                startActivity(Intent(this, PhotoViewActivity::class.java))
            }
        }
    }
}