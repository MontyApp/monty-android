package com.monty.data.model.ui.type

enum class IntervalType(val value: String) {
    DAY("day"),
    WEEK("week"),
    MONTH("month"),
    TOTAL("total")
}

object Intervals {
    val list = listOf(
        IntervalType.DAY,
        IntervalType.WEEK,
        IntervalType.MONTH,
        IntervalType.TOTAL
    )
}