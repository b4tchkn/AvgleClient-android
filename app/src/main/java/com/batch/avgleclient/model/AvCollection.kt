package com.batch.avgleclient.model

import com.squareup.moshi.Json

data class AvCollection(
    val success: Boolean,
    val response: Response
) {
    data class Response(
        @field:Json(name = "has_more")
        val hasMore: Boolean,
        @field:Json(name = "total_collections")
        val totalCollections: Int,
        @field:Json(name = "current_offset")
        val currentOffset: Int,
        val limit: Int,
        val collections: List<Collection>
    ) {
        data class Collection(
            val id: String,
            val title: String,
            val keyword: String,
            @field:Json(name = "cover_url")
            val coverUrl: String,
            @field:Json(name = "total_views")
            val totalViews: Int,
            @field:Json(name = "video_count")
            val videoCount: Int,
            @field:Json(name = "collection_url")
            val collectionUrl: String
        )
    }
}