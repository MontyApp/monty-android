package com.monty.injection.module

import com.monty.injection.PerFragment
import com.monty.ui.adverts.AdvertsFragment
import com.monty.ui.adverts.AdvertsFragmentModule
import com.monty.ui.myadverts.MyAdvertsFragment
import com.monty.ui.myadverts.MyAdvertsFragmentModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilderModule {

    @PerFragment
    @ContributesAndroidInjector(modules = [AdvertsFragmentModule::class])
    internal abstract fun advertsFragment(): AdvertsFragment

    @PerFragment
    @ContributesAndroidInjector(modules = [MyAdvertsFragmentModule::class])
    internal abstract fun myAdvertsFragment(): MyAdvertsFragment
}
