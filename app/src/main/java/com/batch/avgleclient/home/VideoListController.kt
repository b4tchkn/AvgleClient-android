package com.batch.avgleclient.home

import android.view.View
import com.airbnb.epoxy.TypedEpoxyController
import com.batch.avgleclient.itemVideo
import com.batch.avgleclient.model.AvVideo


class VideoListController(private val callback: ClickListener) : TypedEpoxyController<List<AvVideo.Response.Videos>>() {

    interface ClickListener {
        fun itemClickListener(item: AvVideo.Response.Videos)
    }

    override fun buildModels(data: List<AvVideo.Response.Videos>) {
        data.forEach { video ->
            itemVideo {
                id(video.uid)
                video(video)
                itemClickListener(View.OnClickListener { callback.itemClickListener(video) })
            }

        }
    }
}