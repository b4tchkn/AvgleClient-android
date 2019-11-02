package com.batch.avgleclient

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.batch.avgleclient.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navigationController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_category, R.id.navigation_collection
            )
        )
        setupActionBarWithNavController(navigationController, appBarConfiguration)
        bottomNavigationView.setupWithNavController(navigationController)

//        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
//            return@setOnNavigationItemSelectedListener when (item.itemId) {
//                R.id.bottomNavigationCategoryMenuId -> {
//                    Toast.makeText(this, "Alarm item clicked", Toast.LENGTH_SHORT).show()
//                    true
//                }
//                R.id.bottomNavigationCollectionMenuId -> {
//                    Toast.makeText(this, "Clock item clicked", Toast.LENGTH_SHORT).show()
//                    true
//                }
//                else -> false
//            }
//        }
    }
}
