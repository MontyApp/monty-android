package com.monty.tool.permissions

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.monty.injection.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationPermission @Inject constructor(
    @ApplicationContext private val context: Context
) {

    fun isEnabled(): Boolean {
        val checkAccessFineLocation = ContextCompat
            .checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION)
        val checkCoarseFineLocation = ContextCompat
            .checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION)
        val isAccessFineLocationGranted = checkAccessFineLocation == PackageManager.PERMISSION_GRANTED
        val isAccessCoarseLocationGranted = checkCoarseFineLocation == PackageManager.PERMISSION_GRANTED

        return isAccessFineLocationGranted && isAccessCoarseLocationGranted
    }
}