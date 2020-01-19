package com.monty.tool.intent

import android.content.Context
import android.content.Intent
import android.net.Uri

object Navigation {
    fun callPhone(phoneNumber: String): Intent {
        return Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
    }

    fun smsPhone(phoneNumber: String): Intent {
        return Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:$phoneNumber"))
    }

    fun sendEmail(email: String, subject: String): Intent {
        return Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:$email")).apply {
            putExtra(Intent.EXTRA_SUBJECT, subject)
        }
    }

    fun getPhotoFromCamera(fileUri: Uri): Intent {
        return Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
            .putExtra(android.provider.MediaStore.EXTRA_OUTPUT, fileUri)
            .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }

    fun showOnMap(lat: Double, lon: Double, context: Context): Intent {
        val showOnMapIntent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:$lat,$lon?z=15&q=$lat,$lon"))

        return if (showOnMapIntent.resolveActivity(context.packageManager) != null) {
            showOnMapIntent
        } else {
            Navigation.showOnMapViaBrowser(lat, lon)
        }
    }

    private fun showOnMapViaBrowser(lat: Double, lon: Double): Intent {
        val mapUri = Uri.parse("http://www.google.com/maps/place/$lat,$lon/@$lat,$lon,15z")
        return Intent(Intent.ACTION_VIEW, mapUri)
    }
}