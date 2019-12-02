package com.batch.avgleclient.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class AvVideo(
    val success: Boolean,
    val response: Response
) {
    @JsonClass(generateAdapter = true)
    data class Response(
        @Json(name = "has_more")
        val hasMore: Boolean,
        @Json(name = "total_videos")
        val totalVideos: Int,
        @Json(name = "current_offset")
        val currentOffset: Int,
        val limit: Int,
        val videos: List<Video>
    ) {
        @JsonClass(generateAdapter = true)
        data class Video(
            val title: String,
            val keyword: String,
            val channel: String,
            val duration: Float,
            @Json(name = "framerate")
            val frameRate: Float,
            val hd: Boolean,
            @Json(name = "addtime")
            val addTime: Int,
            @Json(name = "viewnumber")
            val viewNumber: Int,
            val likes: Int,
            val dislikes: Int,
            @Json(name = "video_url")
            val videoUrl: String,
            @Json(name = "embedded_url")
            val embeddedUrl: String,
            @Json(name = "preview_url")
            val previewUrl: String,
            @Json(name = "preview_video_url")
            val previewVideoUrl: String,
            val private: Boolean,
            val vid: String,
            val uid: String
        )
    }
}