package com.batch.avgleclient.home

import com.batch.avgleclient.R
import com.batch.avgleclient.databinding.ItemVideoBinding
import com.batch.avgleclient.model.AvVideo
import com.xwray.groupie.databinding.BindableItem
import kotlin.math.floor
import kotlin.math.round

class VideoListItem(private val videoList: AvVideo.Response.Videos) : BindableItem<ItemVideoBinding>() {
    override fun getLayout() = R.layout.item_video

    override fun bind(viewBinding: ItemVideoBinding, position: Int) {
        viewBinding.video = videoList
        val mm = floor(videoList.duration / 60)
        val ss = round(videoList.duration % 60)
        if (mm > 60) {
            val hh = round(mm / 60)
            val hoursMm = round(mm % 60)
            String.format("%02d", hh.toInt())
            viewBinding.duration = "${String.format("%02d", hh.toInt())}:${String.format("%02d", hoursMm.toInt())}:${String.format("%02d", ss.toInt())}"
        } else {
            viewBinding.duration = "${String.format("%02d", mm.toInt())}:${String.format("%02d", ss.toInt())}"
        }
    }
}