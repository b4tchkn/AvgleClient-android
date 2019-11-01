package com.batch.avgleclient

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.batch.avgleclient.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //bottomNavigationView.inflateMenu(R.menu.bottom_navigation_menu)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            return@setOnNavigationItemSelectedListener when (item.itemId) {
                R.id.bottomNavigationAlarmMenuId -> {
                    Toast.makeText(this, "Alarm item clicked", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.bottomNavigationClockMenuId -> {
                    Toast.makeText(this, "Clock item clicked", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.bottomNavigationTimerMenuId -> {
                    Toast.makeText(this, "Timer item clicked", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.bottomNavigationStopWatchMenuId -> {
                    Toast.makeText(this, "StopWatch item clicked", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
    }
}
