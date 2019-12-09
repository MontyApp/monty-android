package com.monty.data.model.ui

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity

@Entity(tableName = "price")
data class Price(
    @Embedded(prefix = "currency_")
    val currency: Currency,

    @Embedded(prefix = "interval_")
    val interval: Interval,

    @ColumnInfo(name = "value")
    val value: Float
) {

    companion object {
        val EMPTY = Price(
            currency = Currency.EMPTY,
            interval = Interval.EMPTY,
            value = 0f
        )
    }
}