package com.monty.injection.module

import com.monty.injection.PerActivity
import com.monty.ui.create.CreateAdvertActivity
import com.monty.ui.create.CreateAdvertActivityModule
import com.monty.ui.detail.AdvertDetailActivity
import com.monty.ui.detail.AdvertDetailActivityModule
import com.monty.ui.main.MainActivity
import com.monty.ui.main.MainActivityModule
import com.monty.ui.photo.ShowPhotoActivity
import com.monty.ui.photo.ShowPhotoActivityModule
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

    @PerActivity
    @ContributesAndroidInjector(modules = [ShowPhotoActivityModule::class])
    abstract fun showPhotoActivity(): ShowPhotoActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [CreateAdvertActivityModule::class])
    abstract fun createAdvertActivity(): CreateAdvertActivity
}
