package com.monty.data.model.ui

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "user")
data class User(
    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "photo")
    val photo: String
) {

    companion object {
        val EMPTY = User(
            name = "",
            photo = ""
        )
    }
}