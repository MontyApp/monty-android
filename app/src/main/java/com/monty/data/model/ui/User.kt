package com.monty.data.model.ui

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.monty.data.model.response.ApiUser

@Entity(tableName = "user")
data class User(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "first_name")
    val firstName: String,

    @ColumnInfo(name = "last_name")
    val lastName: String,

    @ColumnInfo(name = "avatar")
    val avatar: String,

    @ColumnInfo(name = "email")
    val email: String,

    @ColumnInfo(name = "phone")
    val phone: String
) {

    @Ignore
    val name = "$firstName $lastName"

    companion object {
        val EMPTY = User(
            id = "",
            firstName = "",
            lastName = "",
            avatar = "",
            email = "",
            phone = ""
        )

        fun fromApi(apiUser: ApiUser, id: String) = User(
            id = id,
            firstName = apiUser.firstName,
            lastName = apiUser.lastName,
            avatar = apiUser.avatar,
            email = apiUser.email,
            phone = apiUser.phone
        )
    }
}