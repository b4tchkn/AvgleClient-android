package com.batch.avgleclient.collection

import com.batch.avgleclient.R
import com.batch.avgleclient.databinding.ItemCollectionBinding
import com.batch.avgleclient.model.AvCollection
import com.xwray.groupie.databinding.BindableItem

class CollectionItem(private val collection: AvCollection.Response.Collection) :
    BindableItem<ItemCollectionBinding>() {

    override fun getLayout() = R.layout.item_collection

    override fun bind(viewBinding: ItemCollectionBinding, position: Int) {
        viewBinding.collection = collection
        val totalViewsNumber = collection.totalViews
        if (totalViewsNumber > 1000) {
            val viewK = collection.totalViews.toFloat() / 1000
            viewBinding.totalViews = String.format("%.1f", viewK) + "K"
            if (totalViewsNumber > 1000000) {
                val viewM = collection.totalViews.toFloat() / 1000000
                viewBinding.totalViews = String.format("%.1f", viewM) + "M"
            }
        } else {
            viewBinding.totalViews = totalViewsNumber.toString()
        }
    }

    // gridにするとき
    override fun getSpanSize(spanCount: Int, position: Int) = spanCount / 2
}