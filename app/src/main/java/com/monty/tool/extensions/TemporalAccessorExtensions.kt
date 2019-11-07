package com.monty.tool.extensions

import com.monty.tool.constant.Constant
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.temporal.TemporalAccessor

fun TemporalAccessor.toISOString(): String {
    return DateTimeFormatter.ISO_DATE_TIME.format(this)
}

fun TemporalAccessor.toHmm(): String {
    return Constant.Formatter.HOUR_MINUTE.format(this)
}

fun TemporalAccessor.toStringYMD(): String {
    return Constant.Formatter.YEAR_MONTH_DAY.format(this)
}

fun TemporalAccessor.todMEEEE(): String {
    return Constant.Formatter.DAY_MONTH_WEEKDAY.format(this)
}

fun TemporalAccessor.todMMMM(): String {
    return Constant.Formatter.DAY_MONTH_LONG.format(this)
}

fun TemporalAccessor.todMyyyy(): String {
    return Constant.Formatter.DAY_MONTH_YEAR.format(this)
}

fun TemporalAccessor.todM(): String {
    return Constant.Formatter.DAY_MONTH.format(this)
}

fun TemporalAccessor.toEEEE(): String {
    return Constant.Formatter.WEEKDAY.format(this)
}
