package com.monty.ui.photo

import com.monty.tool.constant.Constant
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class ShowPhotoActivityModule {

    @Provides
    @Named("photoAddress")
    fun photoAddress(activity: ShowPhotoActivity): String =
        activity.intent.getStringExtra(Constant.Bundle.PHOTO_ADDRESS)

    @Provides
    @Named("photoAddressType")
    fun photoAddressType(activity: ShowPhotoActivity): String =
        activity.intent.getStringExtra(Constant.Bundle.PHOTO_ADDRESS_TYPE)
}
