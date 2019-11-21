package com.batch.avgleclient.home

import com.batch.avgleclient.R
import com.batch.avgleclient.databinding.ItemVideoBinding
import com.batch.avgleclient.model.AvVideo
import com.xwray.groupie.databinding.BindableItem
import timber.log.Timber
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.math.floor
import kotlin.math.round

class VideoListItem(private val videoList: AvVideo.Response.Videos) :
    BindableItem<ItemVideoBinding>() {
    override fun getLayout() = R.layout.item_video

    override fun bind(viewBinding: ItemVideoBinding, position: Int) {
        viewBinding.video = videoList
        val mm = floor(videoList.duration / 60)
        val ss = round(videoList.duration % 60)
        if (mm > 60) {
            val hh = round(mm / 60)
            val hoursMm = round(mm % 60)
            String.format("%02d", hh.toInt())
            viewBinding.duration = "${String.format("%02d", hh.toInt())}:${String.format(
                "%02d",
                hoursMm.toInt()
            )}:${String.format("%02d", ss.toInt())}"
        } else {
            viewBinding.duration =
                "${String.format("%02d", mm.toInt())}:${String.format("%02d", ss.toInt())}"
        }

        val currentUnixTime = System.currentTimeMillis() / 1000
        val addUnixTime = videoList.addTime.toLong()
        val diff = calcTimeDiff(currentUnixTime - addUnixTime)
        viewBinding.addTime = diff
    }

    private fun calcTimeDiff(timeInUnix: Long): String {
        val hours = timeInUnix / 3600
        val minutes = (timeInUnix % 3600) / 60
        var result = "0"
        if (hours < 0) {
            if (minutes >= 2) {
                result = "${minutes} minutes ago"
            } else {
                result = "${minutes} minute ago"
            }
        } else {
            if (hours >= 1 && hours < 2) {
                result = "${hours} hour ago"
            } else {
                result = "${hours} hours ago"
            }
        }
        return result
    }
}