package com.batch.avgleclient.main

import androidx.lifecycle.ViewModel
import com.batch.avgleclient.repository.CategoryRepository

class MainViewModel() : ViewModel() {
    fun batchAv() {
        val service = CategoryRepository().getCategory()
//        CoroutineScope(Dispatchers.Default).launch {
//            val resp = service.getAvCategories()
//            if(resp.isSuccessful) {}
//        }
    }
}