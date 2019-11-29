package com.batch.avgleclient.utli

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.batch.avgleclient.R
import com.bumptech.glide.Glide
import org.threeten.bp.*
import kotlin.math.floor
import kotlin.math.round

@BindingAdapter("android:imageUrl")
fun ImageView.loadImage(url: String?) {
    Glide.with(context)
        .load(url)
        .placeholder(R.drawable.common_logo)
        .into(this)
}

@BindingAdapter("duration")
fun TextView.duration(duration: Double) {
    val mm = floor(duration / 60)
    val ss = round(duration % 60)
    if (mm > 60) {
        val hh = round(mm / 60)
        val hoursMm = round(mm % 60)
        String.format("%02d", hh.toInt())
        text =
            "${String.format("%02d", hh.toInt())}:${String.format("%02d", hoursMm.toInt())}:${String.format("%02d", ss.toInt())}"
    } else {
        text =
            "${String.format("%02d", mm.toInt())}:${String.format("%02d", ss.toInt())}"
    }
}


@BindingAdapter("addTime")
fun TextView.addTIme(addTime: Int) {
    val zone = ZoneId.systemDefault()
    val dateTime = Instant
        .ofEpochMilli(addTime * 1000L)
        .atZone(zone)
        .toLocalDateTime()
    val now = LocalDateTime.now(zone)
    val period = Period.between(dateTime.toLocalDate(), now.toLocalDate())
    val duration = Duration.between(dateTime, now)
    val periodText = when {
        period.years > 0 -> {
            if (period.years == 1) {
                "${period.years}year"
            } else {
                "${period.years}years"
            }
        }
        period.months > 0 -> {
            if (period.months == 1) {
                "${period.months}month"
            } else {
                "${period.months}months"
            }
        }
        period.days > 6 -> {
            if (period.days / 7 == 1) {
                "${period.days / 7}week"
            } else {
                "${period.days / 7}weeks"
            }
        }
        period.days > 0 -> {
            if (period.days == 1) {
                "${period.days}day"
            } else {
                "${period.days}days"
            }
        }
        duration.toHours() > 0 -> {
            if (duration.toHours() == 1L) {
                "${duration.toHours()}hour"
            } else {
                "${duration.toHours()}hours"
            }
        }
        duration.toMinutes() > 0 -> {
            if (duration.toMinutes() == 1L) {
                "${duration.toMinutes()}minute"
            } else {
                "${duration.toMinutes()}minutes"
            }
        }
        else -> {
            if (duration.seconds == 1L) {
                "${duration.seconds}second"
            } else {
                "${duration.seconds}seconds"
            }
        }
    }
    text = periodText+" ago"
}

@BindingAdapter("likes", "dislikes")
fun TextView.likes(likes: Int, dislikes: Int) {
    if (likes != 0) {
        val bunshi = likes.toDouble()
        val bunbo = (likes + dislikes).toDouble()
        text = ((bunshi/bunbo) * 100).toInt().toString() + "%"
    } else {
        text = "0%"
    }
}

@BindingAdapter("views")
fun TextView.views(views: Int) {
    var totalViewsNumber = views
    var viewNumber = ""
    if (totalViewsNumber > 1000) {
        val viewK = views.toFloat() / 1000
        viewNumber = String.format("%.1f", viewK) + "K"
        if (totalViewsNumber > 1000000) {
            val viewM = views.toFloat() / 1000000
            viewNumber = String.format("%.1f", viewM) + "M"
        }
    } else {
        viewNumber = totalViewsNumber.toString()
    }
    text = viewNumber.toString()+" views"
}