package com.kaveh.awsomeanim

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kaveh.awsomeanim.ui.CustomNavigationView

class NavigationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)
        val navView1: CustomNavigationView = findViewById(R.id.nav_view1)

        val type = intent.getStringExtra("type")

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        val mImageView = findViewById<ImageView>(R.id.main_bg)
        var colorFrom = resources.getColor(R.color.color1)
        val listener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
            var colorTo = resources.getColor(R.color.color2)
            when (item.itemId) {
                R.id.navigation_home -> {
                    colorTo = resources.getColor(R.color.color1)
                }
                R.id.navigation_dashboard -> {
                    colorTo = resources.getColor(R.color.color2)
                }
                R.id.navigation_notifications -> {
                    colorTo = resources.getColor(R.color.color3)
                }
            }
            val fromTmp = colorFrom
            val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), fromTmp, colorTo)
            colorAnimation.duration = 250 // milliseconds

            colorAnimation.addUpdateListener { animator ->
                run {
                    mImageView.setBackgroundColor(animator.animatedValue as Int)
                    colorFrom = animator.animatedValue as Int
                }
            }
            colorAnimation.start()
            return@OnNavigationItemSelectedListener true
        }
        navView1.setCustomNavigationListener(listener)

        navView1.setupWithNavController(navController)
        when (type) {
            "trail" -> {
                navView1.animationType = CustomNavigationView.AnimationType.Trail
            }
            "point" -> {
                navView1.animationType = CustomNavigationView.AnimationType.Point
            }
            "fall" -> {
                navView1.animationType = CustomNavigationView.AnimationType.Fall
            }
        }
    }
}