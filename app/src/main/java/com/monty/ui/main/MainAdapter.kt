package com.monty.ui.main

import androidx.annotation.IdRes

interface MainAdapter {
    fun getPositionForItemId(@IdRes menuItemId: Int): Int
}
