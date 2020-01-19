package com.monty.tool.extensions

import android.location.Location
import com.monty.data.model.ui.Address

fun Location.getDistanceText(destination: Address): String {
    val distanceInMeters = this.getDistanceInMeters(destination)
    return if (distanceInMeters < 1000) "$distanceInMeters m" else "${distanceInMeters / 1000} km"
}

fun Location.getDistanceInMeters(destination: Address): Long {
    return this.distanceTo(destination.toLocation()).toDouble().round(50)
}

fun Location.getDistanceInKilometers(destination: Address): Long {
    return this.getDistanceInMeters(destination) / 1000
}

fun Location.isValid(): Boolean {
    return this.latitude != 0.0 && this.longitude != 0.0
}

fun Double.round(multipleOf: Int): Long {
    return (Math.floor((this + multipleOf / 2) / multipleOf) * multipleOf).toLong()
}
