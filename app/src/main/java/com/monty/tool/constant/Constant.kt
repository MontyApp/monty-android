package com.monty.tool.constant

import org.threeten.bp.format.DateTimeFormatter

interface Constant {

    object Database {
        const val ADVERTS = "adverts"
        const val USERS = "users"
    }

    object Bundle {
        const val TAB_ID = "tab_id"
        const val ADVERT_ID = "advert_id"
        const val PHOTO_ADDRESS = "photo_address"
        const val PHOTO_ADDRESS_TYPE = "photo_address_type"
    }

    object Intent {
        const val TAKE_PHOTO_REQUEST = 1
        const val PICK_IMAGE = 2
        const val LOCATION_PERMISSION_REQUEST = 3
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
