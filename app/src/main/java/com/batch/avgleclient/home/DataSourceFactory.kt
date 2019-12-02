package com.batch.avgleclient.home

import androidx.paging.DataSource
import com.batch.avgleclient.model.AvVideo
import com.batch.avgleclient.repository.AvRepository
import kotlinx.coroutines.CoroutineScope

class DataSourceFactory(
    private val api: AvRepository,
    private val scope: CoroutineScope
) : DataSource.Factory<Int, AvVideo.Response.Video>() {
    override fun create(): DataSource<Int, AvVideo.Response.Video>  = VideoDataSource(api, scope)
}