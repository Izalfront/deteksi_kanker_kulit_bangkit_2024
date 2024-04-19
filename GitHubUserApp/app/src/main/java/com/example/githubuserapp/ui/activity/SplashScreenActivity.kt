package com.example.githubuserapp.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.githubuserapp.R

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        configurationActionBar()
        initializeSplashScreen()
    }

    private fun configurationActionBar(){
        if(supportActionBar!=null)
            this.supportActionBar?.hide()
    }

    @Suppress("DEPRECATION")
    private fun initializeSplashScreen() {
        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, splashTime)
    }

    companion object {
        var splashTime : Long = 3000
    }
}