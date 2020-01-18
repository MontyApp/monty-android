package com.monty.data.model.ui

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.google.firebase.firestore.GeoPoint

@Entity(tableName = "address")
data class Address(
    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "latitude")
    val latitude: Double,

    @ColumnInfo(name = "longitude")
    val longitude: Double
) {

    companion object {
        val EMPTY = Address(
            name = "",
            latitude = 0.0,
            longitude = 0.0
        )

        fun fromApi(apiAddress: GeoPoint) = Address(
            name = "",
            latitude = apiAddress.latitude,
            longitude = apiAddress.longitude
        )
    }
}