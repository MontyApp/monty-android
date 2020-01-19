package com.monty.data.model.ui

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_advert")
data class FavouriteAdvert(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String
) {

    companion object {
        val EMPTY = FavouriteAdvert(id = "")
    }
}