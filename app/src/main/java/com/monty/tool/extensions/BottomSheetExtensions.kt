package com.monty.tool.extensions

import android.view.View
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import com.monty.ui.base.BaseBottomSheetFragment

fun BaseBottomSheetFragment.expandBottomSheet(view: View) {
    view.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            val bottomSheet = requireDialog().findViewById(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout?
            bottomSheet?.let {
                val behavior = com.google.android.material.bottomsheet.BottomSheetBehavior.from(it)
                behavior.state = com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
                behavior.skipCollapsed = true
                view.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        }
    })
}

fun BaseBottomSheetFragment.halfExpandBottomSheet(view: View) {
    view.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            val bottomSheet = requireDialog().findViewById(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout?
            bottomSheet?.let {
                val behavior = com.google.android.material.bottomsheet.BottomSheetBehavior.from(it)
                behavior.state = com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_HALF_EXPANDED
                behavior.skipCollapsed = true
                view.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        }
    })
}
