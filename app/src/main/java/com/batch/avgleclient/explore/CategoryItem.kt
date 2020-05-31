package com.batch.avgleclient.explore

import com.batch.avgleclient.R
import com.batch.avgleclient.databinding.ItemCategoryBinding
import com.batch.avgleclient.model.AvCategory
import com.xwray.groupie.databinding.BindableItem

class CategoryItem(val category: AvCategory.Response.Category) :
    BindableItem<ItemCategoryBinding>() {

    override fun getLayout() = R.layout.item_category

    override fun bind(viewBinding: ItemCategoryBinding, position: Int) {
        viewBinding.category = category
    }
}