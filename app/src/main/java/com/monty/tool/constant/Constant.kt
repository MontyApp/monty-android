package com.monty.tool.constant

import org.threeten.bp.format.DateTimeFormatter

interface Constant {

    object Bundle {
        const val TAB_ID = "tab_id"
        const val ADVERT_ID = "advert_id"
    }

    object Formatter {
        val YEAR_MONTH_DAY: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val HOUR_MINUTE: DateTimeFormatter = DateTimeFormatter.ofPattern("H:mm")
        val DAY_MONTH: DateTimeFormatter = DateTimeFormatter.ofPattern("d. M.")
        val DAY_MONTH_LONG: DateTimeFormatter = DateTimeFormatter.ofPattern("d. MMMM")
        val DAY_MONTH_WEEKDAY: DateTimeFormatter = DateTimeFormatter.ofPattern("d. M. (EEEE)")
        val DAY_MONTH_YEAR: DateTimeFormatter = DateTimeFormatter.ofPattern("d. M. yyyy")
        val WEEKDAY: DateTimeFormatter = DateTimeFormatter.ofPattern("EEEE")
    }
}
