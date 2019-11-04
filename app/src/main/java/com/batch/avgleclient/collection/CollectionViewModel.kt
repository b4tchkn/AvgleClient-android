package com.batch.avgleclient.collection

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.batch.avgleclient.R
import com.batch.avgleclient.model.AvCollection
import com.batch.avgleclient.repository.AvRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class CollectionViewModel(application: Application) : AndroidViewModel(application) {
    val collections = MutableLiveData<List<AvCollection.Response.Collection>>()
    private val api = AvRepository(application.applicationContext.getString(R.string.API_AVGLE_URL))
    private val scope = CoroutineScope(Dispatchers.Main)

    fun fetchFromRemote(page: Int) {
        scope.launch {
            try {
                collections.value = api.getAvCollections(page).response.collections
            } catch (e: Exception) {
                e.stackTrace
            }
        }
    }
}