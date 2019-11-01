package com.batch.avgleclient.category

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.batch.avgleclient.model.AvCategory
import com.batch.avgleclient.repository.AvRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import com.batch.avgleclient.R
import kotlinx.coroutines.launch

class CategoryViewModel(application: Application) : AndroidViewModel(application) {
    var categories = MutableLiveData<AvCategory>()
    private val api = AvRepository(application.applicationContext.getString(R.string.API_AVGLE_URL))
    private val scope = CoroutineScope(Dispatchers.Default)

    fun fetchFromRemote() : MutableLiveData<AvCategory>{
        scope.launch {
            categories = api.getAvCategories()
        }
        return categories
    }
}