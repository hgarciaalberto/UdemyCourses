package com.udemy.compose.newsapp.model

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.text.format.DateUtils.FORMAT_ABBREV_ALL
import android.text.format.DateUtils.FORMAT_ABBREV_TIME
import android.text.format.DateUtils.MINUTE_IN_MILLIS
import android.text.format.DateUtils.WEEK_IN_MILLIS
import android.text.format.DateUtils.getRelativeDateTimeString
import android.util.Log
import java.util.*

object MockData {

    fun Date.getTimeAgo(context: Context): String {
        val calendar = Calendar.getInstance()
        calendar.time = this

        return getRelativeDateTimeString(
            context, calendar.timeInMillis, MINUTE_IN_MILLIS, WEEK_IN_MILLIS,
            FORMAT_ABBREV_ALL or FORMAT_ABBREV_TIME
        ).toString()
    }

    fun stringToDate(publishedAt: String): Date? {
        val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssxx", Locale.ENGLISH).parse(publishedAt)
        Log.d("published", "$date")
        return date
    }
}