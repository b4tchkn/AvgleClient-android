package com.batch.avgleclient.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.batch.avgleclient.R
import com.batch.avgleclient.model.CategoryRepository
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

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
        CoroutineScope(Dispatchers.Default).launch {
            val request = service.getAvCategories()
            val response = request.await()
            if (response.isSuccessful) {
                print(response.body())
            }
        }
    }

    private val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
        Log.e("Exception", ":" + throwable)
    }
}
