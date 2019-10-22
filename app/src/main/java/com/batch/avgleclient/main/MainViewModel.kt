package com.batch.avgleclient.main

import androidx.lifecycle.ViewModel
import com.batch.avgleclient.model.CategoryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel() : ViewModel() {
    fun batchAv() {
        val service = CategoryRepository().getCategory()
        CoroutineScope(Dispatchers.Default).launch {
            val request = service.getAvCategories()
            val response = request.await()
        }
    }
}