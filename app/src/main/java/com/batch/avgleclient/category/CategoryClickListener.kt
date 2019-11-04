package com.batch.avgleclient.category

import android.view.View
import com.batch.avgleclient.model.AvCategory

interface CategoryClickListener {
    fun onCategoryClicked(category: AvCategory.Response.Category)
}