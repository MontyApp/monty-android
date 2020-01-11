package com.monty.data.model.ui

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class Category(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @Embedded(prefix = "parent_")
    val parent: Group,

    @ColumnInfo(name = "name")
    val name: String
) {

    companion object {
        val EMPTY = Category(
            id = -1,
            parent = Group.EMPTY,
            name = ""
        )
    }
}