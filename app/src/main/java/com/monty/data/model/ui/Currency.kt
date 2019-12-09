package com.monty.data.model.ui

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "currency")
data class Currency(
    @ColumnInfo(name = "code")
    val code: String,

    @ColumnInfo(name = "sign")
    val sign: String
) {

    companion object {
        val EMPTY = Currency(
            code = "",
            sign = ""
        )
    }
}