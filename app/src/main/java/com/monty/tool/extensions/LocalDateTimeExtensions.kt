package com.monty.tool.extensions

import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId

fun LocalDateTime.isToday(): Boolean {
    val today = LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault())
    return today.year == this.year && today.month == this.month && today.dayOfMonth == this.dayOfMonth
}

fun LocalDateTime.isTomorrow(): Boolean {
    val tomorrow = LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()).plusDays(1)
    return tomorrow.year == this.year && tomorrow.month == this.month && tomorrow.dayOfMonth == this.dayOfMonth
}

fun LocalDate.isNotMIN(): Boolean {
    return this != LocalDate.MIN
}

fun LocalDateTime.isAfterNow(): Boolean {
    val now = LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault())
    return this.isAfter(now)
}

fun LocalDateTime.isBeforeNow(): Boolean {
    val now = LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault())
    return this.isBefore(now)
}
