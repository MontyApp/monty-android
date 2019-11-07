package com.monty.tool.extensions

import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.widget.Toast
import androidx.annotation.*
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat

fun Context.pixelSize(@DimenRes dimen: Int): Int {
    return resources.getDimensionPixelSize(dimen)
}

@ColorInt
fun Context.color(@ColorRes color: Int): Int {
    return ContextCompat.getColor(this, color)
}

fun Context.drawable(@DrawableRes drawable: Int): Drawable? {
    return ContextCompat.getDrawable(this, drawable)
}

fun Context.font(@FontRes font: Int): Typeface? {
    return ResourcesCompat.getFont(this, font)
}

fun Context.showToast(message: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, length).show()
}
