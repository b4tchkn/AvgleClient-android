package com.batch.avgleclient.category

import android.view.View
import com.airbnb.epoxy.TypedEpoxyController
import com.batch.avgleclient.itemCategory
import com.batch.avgleclient.model.AvCategory

class CategoryListController(private val callback: ClickListener) : TypedEpoxyController<List<AvCategory.Response.Category>>() {

    interface ClickListener {
        fun itemClickListener(item: AvCategory.Response.Category)
    }

    override fun buildModels(data: List<AvCategory.Response.Category>) {
        data.forEach { category ->
            itemCategory {
                id(category.uuid)
                category(category)
                itemClickListener(View.OnClickListener { callback.itemClickListener(category) })
            }
        }
        // この書き方もある
//        data.forEach {category ->
//            ItemCategoryBindingModel_()
//                .category(category)
//                .id(category.uuid)
//                .addTo(this)
//        }
    }

}