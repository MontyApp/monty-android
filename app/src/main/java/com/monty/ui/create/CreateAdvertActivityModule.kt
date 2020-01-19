package com.monty.ui.create

import com.monty.tool.constant.Constant
import dagger.Module
import dagger.Provides

@Module
class CreateAdvertActivityModule {

    @Provides
    fun advertId(activity: CreateAdvertActivity) =
        activity.intent.getStringExtra(Constant.Bundle.ADVERT_ID) ?: ""
}
