package com.batch.avgleclient.home

import android.app.Application
import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.batch.avgleclient.R
import com.batch.avgleclient.model.AvVideo
import com.batch.avgleclient.repository.AvRepository

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    val topVideos: LiveData<PagedList<AvVideo.Response.Video>>
    private val api = AvRepository(application.applicationContext.getString(R.string.API_AVGLE_URL))
    val loading = MutableLiveData<Boolean>()
    private val factory = DataSourceFactory(api, viewModelScope, loading)
    val isRefreshing = MediatorLiveData<Boolean>()

    init {
        val pagedVideosConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(50).build()
        topVideos = LivePagedListBuilder(factory, pagedVideosConfig)
            .build()
        val loadingObserver = Observer<Boolean> {
            if (it) return@Observer
            isRefreshing.value = it
        }
        isRefreshing.addSource(loading, loadingObserver)
    }

    fun refresh() {
        factory.invalidate()
    }
}