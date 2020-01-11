package com.monty.data.model.ui.mapper

import android.content.res.Resources
import com.monty.R
import com.monty.data.model.ui.type.IntervalType

object IntervalMapper {
    fun getInterval(resources: Resources, interval: String): String {
        return when (interval) {
            IntervalType.DAY.value -> resources.getString(R.string.interval_day)
            IntervalType.WEEK.value -> resources.getString(R.string.interval_week)
            IntervalType.MONTH.value -> resources.getString(R.string.interval_month)
            IntervalType.TOTAL.value -> resources.getString(R.string.interval_total)
            else -> ""
        }
    }
}