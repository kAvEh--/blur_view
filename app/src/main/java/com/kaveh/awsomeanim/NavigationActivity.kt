package com.kaveh.awsomeanim

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
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
//        val navView2: CustomNavigationView = findViewById(R.id.nav_view2)
//        val navView3: CustomNavigationView = findViewById(R.id.nav_view3)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView1.setupWithNavController(navController)
        navView1.animationType = CustomNavigationView.AnimationType.Fall
//        navView2.animationType = CustomNavigationView.AnimationType.Trail
//        navView2.setOnNavigationItemSelectedListener { item ->
//            when (item.itemId) {
//            }
//            true
//        }
    }
}
