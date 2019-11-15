package com.batch.avgleclient.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.batch.avgleclient.R
import com.batch.avgleclient.model.AvVideo
import com.batch.avgleclient.repository.AvRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.launch
import java.lang.Exception

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    var topVideos = MutableLiveData<List<AvVideo.Response.Videos>>()
    private val api = AvRepository(application.applicationContext.getString(R.string.API_AVGLE_URL))
    private val scope = CoroutineScope(Dispatchers.Main)

    fun fetchFromRemote() {
        scope.launch {
            try {
                topVideos.value = api.getAvVideos(0).response.videos
                Log.d("LOGLOG", topVideos.value.toString())
            } catch (e: Exception) {
                e.stackTrace
                Log.d("LOGLOG", e.toString())
            }
        }
    }
}