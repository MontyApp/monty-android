package com.monty.data.model.ui

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "address")
data class Address(
    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "latitude")
    val latitude: String,

    @ColumnInfo(name = "longitude")
    val longitude: String
) {

    companion object {
        val EMPTY = Address(
            name = "",
            latitude = "",
            longitude = ""
        )
    }
}