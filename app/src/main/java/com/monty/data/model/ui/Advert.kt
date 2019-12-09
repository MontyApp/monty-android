package com.monty.data.model.ui

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "advert")
data class Advert(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "interval")
    val interval: String,

    @ColumnInfo(name = "image")
    val image: String,

    @ColumnInfo(name = "price")
    val price: String,

    @ColumnInfo(name = "is_favourite")
    val isFavourite: Boolean = false
) {

    companion object {
        val EMPTY = Advert(
            id = -1,
            title = "",
            image = "",
            interval = "",
            description = "",
            price = ""
        )
    }
}