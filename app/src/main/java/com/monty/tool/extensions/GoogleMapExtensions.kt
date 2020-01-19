package com.monty.tool.extensions

import android.content.Context
import android.location.Location
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import com.monty.R

fun GoogleMap.configureMap(context: Context, addressLatLon: LatLng, myLocation: Location) {
    clear()
    uiSettings.isMapToolbarEnabled = false

    val addressMarker = MarkerOptions()
        .position(addressLatLon)

    addMarker(addressMarker)

    if (myLocation.latitude != 0.0 && myLocation.longitude != 0.0) {
        val myLocationLatLon = LatLng(myLocation.latitude, myLocation.longitude)
        val myLocationMarker = MarkerOptions()
            .position(myLocationLatLon)
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_myposition))

        addMarker(myLocationMarker)

        val location = LatLngBounds.Builder()
            .include(myLocationLatLon)
            .include(addressLatLon)
            .build()

        setPadding(
            context.pixelSize(R.dimen.d4),
            context.pixelSize(R.dimen.d8),
            context.pixelSize(R.dimen.d4),
            context.pixelSize(R.dimen.d4)
        )
        moveCamera(CameraUpdateFactory.newLatLngBounds(location, 0))
    } else {
        val cameraPosition = CameraPosition.Builder().target(addressLatLon).zoom(11f).build()
        moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }
}