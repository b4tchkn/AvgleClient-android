package com.batch.avgleclient.category

import com.airbnb.epoxy.TypedEpoxyController
import com.batch.avgleclient.ItemCategoryBindingModel_
import com.batch.avgleclient.itemCategory
import com.batch.avgleclient.model.AvCategory

class CategoryListController : TypedEpoxyController<List<AvCategory.Response.Category>>() {


    override fun buildModels(data: List<AvCategory.Response.Category>) {
        data.forEach {
            itemCategory {
                id(it.uuid)
                category(it)
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