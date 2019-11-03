package com.batch.avgleclient.category

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.batch.avgleclient.model.AvCategory
import com.batch.avgleclient.repository.AvRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import com.batch.avgleclient.R
import kotlinx.coroutines.launch

class CategoryViewModel(application: Application) : AndroidViewModel(application) {
    var categories = MutableLiveData<List<AvCategory.Response.Category>>()
    private val api = AvRepository(application.applicationContext.getString(R.string.API_AVGLE_URL))
    private val scope = CoroutineScope(Dispatchers.Main)

    fun fetchFromRemote() {
        scope.launch {
            categories.postValue(api.getAvCategories())
        }
    }
}