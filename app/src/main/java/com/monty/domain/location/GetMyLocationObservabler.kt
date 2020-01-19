package com.monty.domain.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.LocationRequest
import com.monty.domain.base.BaseObservabler
import com.monty.injection.ApplicationContext
import com.monty.tool.permissions.LocationPermission
import com.patloew.rxlocation.RxLocation
import io.reactivex.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetMyLocationObservabler @Inject constructor(
    @ApplicationContext val context: Context,
    private val locationPermission: LocationPermission
) : BaseObservabler<Location>() {

    private val locationRequest = LocationRequest.create().apply {
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        smallestDisplacement = 40f
        fastestInterval = TimeUnit.MINUTES.toMillis(1)
        interval = TimeUnit.MINUTES.toMillis(1)
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() = RxLocation(context)
        .location()
        .updates(locationRequest)
        .onErrorReturn { Location("") }

    @SuppressLint("MissingPermission")
    override fun create(): Observable<Location> {
        return if (locationPermission.isEnabled()) {
            getLocation()
        } else {
            Observable.just(Location(""))
        }
    }
}
