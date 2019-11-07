package com.monty.tool.extensions

import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView

fun BottomNavigationView.getMenuItemPosition(item: MenuItem): Int {
    val itemId = item.itemId
    val menu = this.menu
    val size = menu.size()
    for (i in 0 until size) {
        if (menu.getItem(i).itemId == itemId) {
            return i
        }
    }
    return -1
}