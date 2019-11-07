package com.monty.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.monty.R
import com.monty.ui.adverts.AdvertsFragment
import javax.inject.Inject

class MainPagerAdapter @Inject constructor(
    fm: FragmentManager
) : FragmentStatePagerAdapter(fm), MainAdapter {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> AdvertsFragment.newInstance()
            1 -> Fragment()
            2 -> Fragment()
            3 -> Fragment()
            else -> Fragment()
        }
    }

    override fun getCount(): Int = 4

    override fun getPositionForItemId(menuItemId: Int): Int {
        return when (menuItemId) {
            R.id.menu_home -> 0
            R.id.menu_my -> 1
            R.id.menu_favourite -> 2
            R.id.menu_profile -> 3
            else -> throw IllegalStateException("Trying to access unknown item $menuItemId in MainBusinessPagerAdapter")
        }
    }
}
