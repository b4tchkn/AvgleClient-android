package com.batch.avgleclient.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class AvCategory(
    val success: Boolean,
    val response: Response
) {
    data class Response(
        val categories: List<Category>
    ) {
        @Entity
        @JsonClass(generateAdapter = true)
        data class Category(
            @ColumnInfo(name = "chid")
            @Json(name = "CHID")
            val chid: String,
            @ColumnInfo(name = "name")
            val name: String,
            @ColumnInfo(name = "slug")
            val slug: String,
            @ColumnInfo(name = "total_videos")
            @Json(name = "total_videos")
            val totalVideos: Int,
            @ColumnInfo(name = "short_name")
            @Json(name = "shortname")
            val shortName: String,
            @ColumnInfo(name = "category_url")
            @Json(name = "category_url")
            val categoryUrl: String,
            @ColumnInfo(name = "cover_url")
            @Json(name = "cover_url")
            val coverUrl: String
        ) {
            @PrimaryKey(autoGenerate = true)
            var uuid: Int = 0
        }
    }
}