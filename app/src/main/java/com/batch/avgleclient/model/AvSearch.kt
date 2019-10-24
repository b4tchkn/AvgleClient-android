package com.batch.avgleclient.model

import android.icu.text.CaseMap
import com.squareup.moshi.Json

data class AvSearch(
    val success: Boolean,
    val response: Response
) {
    data class Response(
        @field:Json(name = "has_more")
        val hasMore: Boolean,
        @field:Json(name = "has_videos")
        val hasVideos: Int,
        @field:Json(name = "current_offset")
        val currentOffset: Int,
        val limit: Int,
        val videos: Videos
    ) {
        data class Videos(
            val title: String,
            val keyword: String,
            val channel: String,
            val duration: Float
        )
    }
}