package com.monty.data.model.ui

import android.location.Location
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import com.google.firebase.firestore.GeoPoint
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "address")
data class Address(
    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "latitude")
    val latitude: Double,

    @ColumnInfo(name = "longitude")
    val longitude: Double
) : Parcelable {

    companion object {
        val EMPTY = Address(
            name = "",
            latitude = 0.0,
            longitude = 0.0
        )

        val PRAGUE = Address(
            name = "Prague",
            latitude = 50.0755,
            longitude = 14.4378
        )

        fun fromApi(apiAddress: GeoPoint) = Address(
            name = "",
            latitude = apiAddress.latitude,
            longitude = apiAddress.longitude
        )
    }

    fun toLocation() = Location("").apply {
        latitude = this@Address.latitude
        longitude = this@Address.longitude
    }
}