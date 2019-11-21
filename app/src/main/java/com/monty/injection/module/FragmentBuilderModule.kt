package com.monty.injection.module

import com.monty.injection.PerFragment
import com.monty.ui.adverts.AdvertsFragment
import com.monty.ui.adverts.AdvertsFragmentModule
import com.monty.ui.favourite.FavouriteAdvertsFragment
import com.monty.ui.favourite.FavouriteAdvertsFragmentModule
import com.monty.ui.myadverts.MyAdvertsFragment
import com.monty.ui.myadverts.MyAdvertsFragmentModule
import com.monty.ui.profile.ProfileFragment
import com.monty.ui.profile.ProfileFragmentModule
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

    @PerFragment
    @ContributesAndroidInjector(modules = [FavouriteAdvertsFragmentModule::class])
    internal abstract fun favouriteAdvertsFragment(): FavouriteAdvertsFragment

    @PerFragment
    @ContributesAndroidInjector(modules = [ProfileFragmentModule::class])
    internal abstract fun profileFragment(): ProfileFragment
}
