package com.batch.avgleclient.model

import com.squareup.moshi.Json

data class AvVideo(
    val success: Boolean,
    val response: Response
) {
    data class Response(
        @field:Json(name = "has_more")
        val hasMore: Boolean,
        @field:Json(name = "total_videos")
        val totalVideos: Int,
        @field:Json(name = "current_offset")
        val currentOffset: Int,
        val limit: Int,
        val videos: List<Videos>
    ) {
        data class Videos(
            val title: String,
            val keyword: String,
            val channel: String,
            val duration: Float,
            @field:Json(name = "framerate")
            val frameRate: Float,
            val hd: Boolean,
            @field:Json(name = "addtime")
            val addTime: Int,
            @field:Json(name = "viewnumber")
            val viewNumber: Int,
            val likes: Int,
            val dislikes: Int,
            @field:Json(name = "video_url")
            val videoUrl: String,
            @field:Json(name = "embedded_url")
            val embeddedUrl: String,
            @field:Json(name = "preview_url")
            val previewUrl: String,
            @field:Json(name = "preview_video_url")
            val previewVideoUrl: String,
            val private: Boolean,
            val vid: String,
            val uid: String
        )
    }
}