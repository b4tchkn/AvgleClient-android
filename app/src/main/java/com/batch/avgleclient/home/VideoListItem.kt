package com.batch.avgleclient.home

import com.batch.avgleclient.R
import com.batch.avgleclient.databinding.ItemVideoBinding
import com.batch.avgleclient.model.AvVideo
import com.xwray.groupie.databinding.BindableItem

class VideoListItem(private val videoList: AvVideo.Response.Videos) : BindableItem<ItemVideoBinding>() {
    override fun getLayout() = R.layout.item_video

    override fun bind(viewBinding: ItemVideoBinding, position: Int) {
        viewBinding.video = videoList
    }
}