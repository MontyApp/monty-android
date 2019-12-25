package com.monty.tool.intent

import android.content.Intent
import android.net.Uri

object Navigation {
    fun getPhotoFromCamera(fileUri: Uri): Intent {
        return Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
            .putExtra(android.provider.MediaStore.EXTRA_OUTPUT, fileUri)
            .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }
}