package com.batch.avgleclient.category

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.batch.avgleclient.model.AvCategory
import com.batch.avgleclient.repository.AvRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import com.batch.avgleclient.R
import kotlinx.coroutines.launch
import java.lang.Exception

class CategoryViewModel(application: Application) : AndroidViewModel(application) {
    var categories = MutableLiveData<List<AvCategory.Response.Category>>()
    private val api = AvRepository(application.applicationContext.getString(R.string.API_AVGLE_URL))
    private val scope = CoroutineScope(Dispatchers.Main)

//    init {
//        fetchFromRemote()
//    }

    fun fetchFromRemote() {
//        viewModelScope.launch {
//            categories.value = api.getAvCategories()
//        }
        scope.launch {
            try {
                //            categories.postValue(api.getAvCategories())
                categories.value = api.getAvCategories().response.categories
                Log.d("LOGLOG", categories.value.toString())
            } catch (e: Exception) {
                e.stackTrace
                Log.d("LOGLOG", e.toString())
            }
        }
    }
}