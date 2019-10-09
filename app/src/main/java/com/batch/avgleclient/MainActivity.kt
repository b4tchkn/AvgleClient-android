package com.batch.avgleclient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.batch.avgleclient.model.AvgleApiService
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.lang.Exception

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
        CoroutineScope(Dispatchers.Default).launch(handler) {
            Log.d("batchTag", "kokko")
            val request = avService.getAvCategories()
            Log.d("batchTag", "kokko")
            val response = request.await()
            if (response.isSuccessful) {
                Log.d("batchTag", "success")
            } else {
                Log.d("batchTag", "false")
            }
        }
    }

    private val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
        Log.e("Exception", ":" + throwable)
    }
}
