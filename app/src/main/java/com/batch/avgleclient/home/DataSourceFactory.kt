package com.batch.avgleclient.home

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.batch.avgleclient.model.AvVideo
import com.batch.avgleclient.repository.AvRepository
import kotlinx.coroutines.CoroutineScope

class DataSourceFactory(
    private val api: AvRepository,
    private val scope: CoroutineScope,
    private val loading: MutableLiveData<Boolean>
) : DataSource.Factory<Int, AvVideo.Response.Video>() {

    private val dataSource = MutableLiveData<VideoDataSource>()

    override fun create(): DataSource<Int, AvVideo.Response.Video> {
        val dataSource = VideoDataSource(api, scope, loading)
        this.dataSource.postValue(dataSource)
        return dataSource
    }
}