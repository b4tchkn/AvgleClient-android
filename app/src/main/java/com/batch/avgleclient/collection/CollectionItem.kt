package com.batch.avgleclient.collection

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.ColorInt
import com.batch.avgleclient.R
import com.batch.avgleclient.databinding.ItemCollectionBinding
import com.batch.avgleclient.model.AvCollection
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.databinding.BindableItem
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_collection.view.*

class CollectionItem(private val collection: AvCollection.Response.Collection) : BindableItem<ItemCollectionBinding>() {
    override fun bind(viewBinding: ItemCollectionBinding, position: Int) {
        viewBinding.collection = collection
    }

    override fun getLayout() = R.layout.item_collection
//    override fun getSpanSize(spanCount: Int, position: Int) = spanCount / 2
}