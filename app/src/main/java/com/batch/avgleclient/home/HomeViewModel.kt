package com.batch.avgleclient.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.batch.avgleclient.R
import com.batch.avgleclient.model.AvVideo
import com.batch.avgleclient.repository.AvRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    var topVideos = MutableLiveData<List<AvVideo.Response.Videos>>()
    val loading = MutableLiveData<Boolean>()
    private val api = AvRepository(application.applicationContext.getString(R.string.API_AVGLE_URL))

    fun init() {
        fetchNext(0)
    }

    fun fetchNext(page: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val videos = api.getAvVideos(page).response.videos
                    topVideos.postValue(videos)
                } catch (e: Exception) {
                    e.stackTrace
                }
            }
        }
    }
}