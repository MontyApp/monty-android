package com.monty.ui.base

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class NonSwipableViewPager @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ViewPager(context, attrs) {

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent?): Boolean = false

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean = false
}
