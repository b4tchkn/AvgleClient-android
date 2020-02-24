package com.batch.avgleclient.signup

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.batch.avgleclient.R

class SignupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
    }

    companion object {
        fun createIntent(activity: Activity) = Intent(activity, SignupActivity::class.java)
    }
}
