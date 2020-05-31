package com.batch.avgleclient.explore

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.batch.avgleclient.model.AvCategory
import com.batch.avgleclient.repository.AvRepository
import kotlinx.coroutines.launch

class ExploreViewModel : ViewModel() {
    val category = MutableLiveData<List<AvCategory.Response.Category>>()
    private val api = AvRepository()

    fun fetchCategory() {
        viewModelScope.launch {
            try {
                category.value = api.getAvCategories().response.categories
            } catch (e: Exception) {
                e.stackTrace
            }
        }
    }
}