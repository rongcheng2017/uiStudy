package com.rong.cheng.lottie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable

class LottieActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lottie)
        val lottieAnimationView = findViewById<LottieAnimationView>(R.id.animation_view)


        lottieAnimationView.setAnimationFromUrl("https://cqz-1256838880.cos.ap-shanghai.myqcloud.com/bird1.json")
        lottieAnimationView.repeatMode=LottieDrawable.REVERSE
        lottieAnimationView.repeatCount= LottieDrawable.INFINITE
        lottieAnimationView.playAnimation()

    }
}