package com.batch.avgleclient.collection

import com.batch.avgleclient.R
import com.batch.avgleclient.databinding.ItemCollectionBinding
import com.batch.avgleclient.model.AvCollection
import com.xwray.groupie.databinding.BindableItem

class CollectionItem(private val collection: AvCollection.Response.Collection) : BindableItem<ItemCollectionBinding>() {

    override fun getLayout() = R.layout.item_collection

    override fun bind(viewBinding: ItemCollectionBinding, position: Int) {
        viewBinding.collection = collection
    }
    // gridにするとき
    override fun getSpanSize(spanCount: Int, position: Int) = spanCount / 2
}