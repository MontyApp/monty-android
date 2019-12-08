package com.monty.injection.module

import com.monty.injection.PerActivity
import com.monty.ui.detail.AdvertDetailActivity
import com.monty.ui.detail.AdvertDetailActivityModule
import com.monty.ui.main.MainActivity
import com.monty.ui.main.MainActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @PerActivity
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun mainActivity(): MainActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [AdvertDetailActivityModule::class])
    abstract fun advertDetailActivity(): AdvertDetailActivity
}
