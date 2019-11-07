package com.monty.tool.extensions

import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.annotation.IntRange

@ColorInt
fun Int.withOpacity(@IntRange(from = 0, to = 100) opacity: Int): Int {
    return Color.argb((opacity / 100f * 255).toInt(), Color.red(this), Color.green(this), Color.blue(this))
}