package com.monty.tool.helper

import android.content.Context
import org.threeten.bp.LocalDateTime
import java.io.File
import java.util.*

object FileHelper {

    fun temp(context: Context): File {
        return File(context.externalCacheDir, UUID.randomUUID().toString() + LocalDateTime.now().toString())
    }
}
