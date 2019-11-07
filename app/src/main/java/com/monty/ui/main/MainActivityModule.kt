package com.monty.ui.main

import androidx.fragment.app.FragmentManager
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {

    @Provides
    fun fragmentManager(activity: MainActivity): FragmentManager = activity.supportFragmentManager
}
