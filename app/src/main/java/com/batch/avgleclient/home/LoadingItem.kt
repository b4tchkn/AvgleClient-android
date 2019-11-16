package com.batch.avgleclient.home

import com.batch.avgleclient.R
import com.batch.avgleclient.databinding.ItemLoadingBinding
import com.xwray.groupie.databinding.BindableItem

class LoadingItem :
    BindableItem<ItemLoadingBinding>() {
    override fun getLayout() = R.layout.item_loading

    override fun bind(viewBinding: ItemLoadingBinding, position: Int) {
        // do nothing
    }
}