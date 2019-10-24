package com.batch.avgleclient.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.batch.avgleclient.R
import com.batch.avgleclient.repository.AvRepository
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        categoryButton.setOnClickListener {
            tappedCategoryButton()
        }
        collectionButton.setOnClickListener {
            tappedCollectionButton()
        }
        videoButton.setOnClickListener {
            tappedVideoButton()
        }
    }

    private fun tappedCategoryButton() {
        val service = AvRepository().getRetrofit()
        val scope = CoroutineScope(Dispatchers.Default)
        scope.launch {
            try {
                val avCategories = service.getAvCategories()
                jsonTextView.text = avCategories.toString()
            }catch(e: Exception) {
            }
        }
    }

    private fun tappedCollectionButton() {
        val service = AvRepository().getRetrofit()
        val scope = CoroutineScope(Dispatchers.Default)
        scope.launch {
            try {
                val avCollections = service.getAvCollections(1)
                jsonTextView.text = avCollections.toString()
            }catch(e: Exception) {
            }
        }
    }

    private fun tappedVideoButton() {
        val service = AvRepository().getRetrofit()
        val scope = CoroutineScope(Dispatchers.Default)
        scope.launch {
            try {
                val avVideos = service.getAvVideos(0)
                jsonTextView.text = avVideos.toString()
            }catch(e: Exception) {
            }
        }
    }

}
