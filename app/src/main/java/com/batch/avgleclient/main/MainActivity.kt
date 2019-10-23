package com.batch.avgleclient.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.batch.avgleclient.R
import com.batch.avgleclient.repository.CategoryRepository
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        avButton.setOnClickListener {
            tappedAvButton()
        }
    }

    private fun tappedAvButton() {
        val service = CategoryRepository().getCategory()
        val scope = CoroutineScope(Dispatchers.Default)
        scope.launch {
            try {
                val avCategories = service.getAvCategories()
                Log.d("bachi", avCategories.toString())
            }catch(e: Exception) {
                Log.d("bachi", e.toString())
            }
        }
    }
}
