package com.batch.avgleclient.collection

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.batch.avgleclient.model.AvCollection
import com.batch.avgleclient.repository.AvRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CollectionViewModel(application: Application) : AndroidViewModel(application) {

    val collections = MutableLiveData<List<AvCollection.Response.Collection>>()
    private val api = AvRepository()
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