package com.ashu.hyd

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.ashu.hyd.R.id.splashGif

class SplashActivity : AppCompatActivity() {

    private val splashScreenTime = 4000
    private lateinit var imageGif : ImageView
    private lateinit var topAnimation : Animation

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        imageGif = findViewById(splashGif)
        topAnimation = AnimationUtils.loadAnimation(this,R.anim.top_animation)
        imageGif.animation = topAnimation
        Handler(Looper.getMainLooper()).postDelayed(
        {
            val intent = Intent(this@SplashActivity,AuthenticationActivity::class.java)
            startActivity(intent)
            finish()
        },splashScreenTime.toLong()
        )
    }
}