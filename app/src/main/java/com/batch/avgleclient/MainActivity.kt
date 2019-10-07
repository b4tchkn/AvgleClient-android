package com.batch.avgleclient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.batch.avgleclient.model.AvgleApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val avService = AvgleApiService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        CoroutineScope(Dispatchers.IO).launch{
            val request = avService.getAvCategories()
            val response = request.await()
            if (response.isSuccessful) {
                print(response.body())
            } else {
                print(response.errorBody())
            }
        }
    }
}
