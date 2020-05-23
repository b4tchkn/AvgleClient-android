package com.batch.avgleclient.home

import android.view.View
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging.PagedListEpoxyController
import com.batch.avgleclient.ItemVideoBindingModel_
import com.batch.avgleclient.itemLoading
import com.batch.avgleclient.model.AvVideo


class VideoListController(private val callback: ClickListener) :
    PagedListEpoxyController<AvVideo.Response.Video>() {

    var isLoading: Boolean = false
        set(value) {
            field = value
            if (field) {
                requestModelBuild()
            }
        }

    interface ClickListener {
        fun itemClickListener(item: AvVideo.Response.Video)
        fun moreClickListener(item: AvVideo.Response.Video)
    }

    override fun buildItemModel(
        currentPosition: Int,
        item: AvVideo.Response.Video?
    ): EpoxyModel<*> {
        return ItemVideoBindingModel_().apply {
            id(currentPosition)
            item?.let {
                video(it)
            }
            itemClickListener(View.OnClickListener { callback.itemClickListener(video()) })
            moreClickListener(View.OnClickListener { callback.moreClickListener(video()) })
        }
    }

    override fun addModels(models: List<EpoxyModel<*>>) {
        super.addModels(models)
        if (isLoading) {
            itemLoading {
                id("loading")
            }
        }
    }
}