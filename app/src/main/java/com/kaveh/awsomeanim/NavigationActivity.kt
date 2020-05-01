package com.kaveh.awsomeanim

import android.graphics.drawable.Animatable2
import android.os.Bundle
import android.view.Menu
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.kaveh.awsomeanim.ui.CustomNavigationView

class NavigationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)
        val navView1: CustomNavigationView = findViewById(R.id.nav_view1)

        val type = intent.getStringExtra("type")

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        navView1.setupWithNavController(navController)
        if (type == "trail") {
            navView1.animationType = CustomNavigationView.AnimationType.Trail
        } else if (type == "point") {
            navView1.animationType = CustomNavigationView.AnimationType.Point
        } else if (type == "fall") {
            navView1.animationType = CustomNavigationView.AnimationType.Fall
        }
    }
}