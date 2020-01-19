package com.monty.ui.detail

import com.monty.tool.constant.Constant
import dagger.Module
import dagger.Provides

@Module
class AdvertDetailActivityModule {

    @Provides
    fun advertId(activity: AdvertDetailActivity) = activity.intent.getStringExtra(Constant.Bundle.ADVERT_ID) ?: ""
}
