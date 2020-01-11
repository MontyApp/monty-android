package com.monty.data.model.ui

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class Group(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "name")
    val name: String
) {

    companion object {
        val EMPTY = Group(
            id = "",
            name = ""
        )
    }
}