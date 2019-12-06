package com.batch.avgleclient.home

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.batch.avgleclient.model.AvVideo
import com.batch.avgleclient.repository.AvRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class VideoDataSource(
    private val api: AvRepository,
    private val scope: CoroutineScope,
    private val loading: MutableLiveData<Boolean>
) : PageKeyedDataSource<Int, AvVideo.Response.Video>() {


    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, AvVideo.Response.Video>
    ) {
        scope.launch {
            callApi(0) { next, videos ->
                callback.onResult(videos, null, next)
            }
        }
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, AvVideo.Response.Video>
    ) {
        scope.launch {
            callApi(params.key) { next, videos ->
                callback.onResult(videos, next)
            }
        }
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, AvVideo.Response.Video>
    ) {
    }

    private suspend fun callApi(
        page: Int,
        callback: (next: Int?, videos: List<AvVideo.Response.Video>) -> Unit
    ) {
        try {
            loading.postValue(true)
            val videos = api.getAvVideos(page)
            var next: Int? = null
            if (videos.response.hasMore) {
                next = page + 1
            }
            callback(next, videos.response.videos)
            loading.postValue(false)
        } catch (e: Exception) {
            e.stackTrace
            loading.postValue(false)
        }
    }
}