package com.batch.avgleclient.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class AvCollection(
    val success: Boolean,
    val response: Response
) {
    @JsonClass(generateAdapter = true)
    data class Response(
        @Json(name = "has_more")
        val hasMore: Boolean,
        @Json(name = "total_collections")
        val totalCollections: Int,
        @Json(name = "current_offset")
        val currentOffset: Int,
        val limit: Int,
        val collections: List<Collection>
    ) {
        @JsonClass(generateAdapter = true)
        data class Collection(
            val id: String,
            val title: String,
            val keyword: String,
            @Json(name = "cover_url")
            val coverUrl: String,
            @Json(name = "total_views")
            val totalViews: Int,
            @Json(name = "video_count")
            val videoCount: Int,
            @Json(name = "collection_url")
            val collectionUrl: String
        )
    }
}