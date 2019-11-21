package com.monty.tool.extensions

import androidx.core.content.res.ResourcesCompat
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.monty.R

fun CollapsingToolbarLayout.titleTypeface() {
    val typeface = ResourcesCompat.getFont(context, R.font.rubik_regular)
    setCollapsedTitleTypeface(typeface)
    setExpandedTitleTypeface(typeface)
}