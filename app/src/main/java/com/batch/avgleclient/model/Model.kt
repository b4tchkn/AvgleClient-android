package com.batch.avgleclient.model

import com.squareup.moshi.Json

data class AvCategory(
    val success: Boolean,
    val response: Response
) {
    data class Response(
        val categories: List<Category>
    ) {
        data class Category(
            @field:Json(name = "CHID")
            val chid: String,
            val name: String,
            val slug: String,
            @field:Json(name = "total_videos")
            val totalVideos: Int,
            @field:Json(name = "shortname")
            val shortName: String,
            @field:Json(name = "category_url")
            val categoryUrl: String,
            @field:Json(name = "cover_url")
            val coverUrl: String
        )
    }
}