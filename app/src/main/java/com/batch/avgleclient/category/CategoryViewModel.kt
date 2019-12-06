package com.batch.avgleclient.category

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.batch.avgleclient.R
import com.batch.avgleclient.model.AvCategory
import com.batch.avgleclient.model.AvCategoryDatabase
import com.batch.avgleclient.repository.AvRepository
import com.batch.avgleclient.utli.SharedPreferencesHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoryViewModel(application: Application) : AndroidViewModel(application) {

    var categories = MutableLiveData<List<AvCategory.Response.Category>>()
    val loading = MutableLiveData<Boolean>()
    private var refreshTime = 5 * 60 * 1000 * 1000 * 1000L
    private var prefHelper = SharedPreferencesHelper(getApplication())
    private val api = AvRepository(application.applicationContext.getString(R.string.API_AVGLE_URL))
    private val scope = CoroutineScope(Dispatchers.Main)

    fun refresh() {
        val updateTime = prefHelper.getUpdateTime()
        if (updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime) {
            fetchFromDatabase()
        } else {
            fetchFromRemote()
        }
    }

    private fun fetchFromRemote() {
        loading.value = true
        scope.launch {
            try {
                val avCategories = api.getAvCategories().response.categories
                categoriesRetrieved(avCategories)
                storeCategoriesLocally(avCategories)
            } catch (e: Exception) {
                e.stackTrace
            }
        }
    }

    private fun fetchFromDatabase() {
        loading.value = true
        scope.launch {
            val avCategories = AvCategoryDatabase(getApplication()).avCategoryDao().getAllCategories()
            categoriesRetrieved(avCategories)
        }
    }

    private fun categoriesRetrieved(categoryList: List<AvCategory.Response.Category>) {
        categories.value = categoryList
        loading.value = false
    }

    private fun storeCategoriesLocally(list: List<AvCategory.Response.Category>) {
        scope.launch {
            val dao = AvCategoryDatabase(getApplication()).avCategoryDao()
            dao.deleteAllCategories()
            val result = dao.insertAll(*list.toTypedArray())
            var i = 0
            while (i < list.size) {
                list[i].uuid = result[i].toInt()
                ++i
            }
            categoriesRetrieved(list)
        }
        prefHelper.saveUpdateTime(System.nanoTime())
    }
}