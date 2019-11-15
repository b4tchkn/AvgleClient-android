package com.batch.avgleclient.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AvCategoryDao {
    @Insert
    suspend fun insertAll(vararg categories: AvCategory.Response.Category): List<Long>

    @Query("SELECT * FROM category")
    suspend fun getAllCategories(): List<AvCategory.Response.Category>

    @Query("DELETE FROM category")
    suspend fun deleteAllCategories()
}