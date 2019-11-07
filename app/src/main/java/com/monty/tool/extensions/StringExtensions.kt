package com.monty.tool.extensions

import android.os.Build
import android.text.Html
import android.text.Spanned
import com.monty.tool.constant.Constant
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime
import org.threeten.bp.format.DateTimeFormatter

fun String.toLocalDate(): LocalDate {
    return LocalDate.parse(this, Constant.Formatter.YEAR_MONTH_DAY)
}

fun String.toLocalDateTime(): LocalDateTime {
    return LocalDateTime.parse(this, DateTimeFormatter.ISO_DATE_TIME)
}

fun String.toLocalTime(): LocalTime {
    return LocalTime.parse(this)
}

@Suppress("DEPRECATION")
fun String.fromHtml(): Spanned {
    return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
        Html.fromHtml(this)
    } else {
        Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    }
}

fun String.capitalize(): String {
    return "${this.substring(0, 1).toUpperCase()}${this.substring(1)}"
}

fun String.withDecimalDot() = replace(',', '.')
