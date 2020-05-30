package com.batch.avgleclient

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navigationController = findNavController(R.id.nav_host_fragment)
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.navigation_home, R.id.navigation_explore, R.id.navigation_collection
//            )
//        )
//        setupActionBarWithNavController(navigationController, appBarConfiguration)
        bottom_navigation_view.setupWithNavController(navigationController)
    }

    companion object {
        fun createIntent(activity: Activity) = Intent(activity, MainActivity::class.java)
    }
}
