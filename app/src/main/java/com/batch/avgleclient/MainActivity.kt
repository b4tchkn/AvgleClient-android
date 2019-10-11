package com.batch.avgleclient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.batch.avgleclient.model.AvgleApiService
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private val avService = AvgleApiService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        avButton.setOnClickListener {
            tappedAvButton()
        }
    }

    private fun tappedAvButton() {
        CoroutineScope(Dispatchers.IO).launch(handler) {
            async(Dispatchers.Default) {
                Log.d("batchTag", "kokko")
                val request = avService.getAvCategoriesAsync()
                val response = request.await()
                if (response.isSuccessful) {
                    Log.d("batchTag", "success")
                } else {
                    Log.d("batchTag", "false")
                }
            }
        }
    }

    private val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
        Log.e("Exception", ":" + throwable)
    }
}
