package com.monty.tool.extensions

import android.view.View

fun View.visible(visible: Boolean = true) {
    this.visibility = if (visible) View.VISIBLE else View.GONE
}

fun View.visibleOrInvisible(visible: Boolean = true) {
    this.visibility = if (visible) View.VISIBLE else View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.animateShow() {
    if (visibility != View.VISIBLE) {
        visible()
        alpha = 0f
        animate().alpha(1f)
            .duration = 200
    }
}
