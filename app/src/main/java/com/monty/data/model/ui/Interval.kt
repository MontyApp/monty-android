package com.monty.data.model.ui

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "interval")
data class Interval(
    @ColumnInfo(name = "name")
    val name: String
) {

    companion object {
        val EMPTY = Interval(
            name = ""
        )
    }
}