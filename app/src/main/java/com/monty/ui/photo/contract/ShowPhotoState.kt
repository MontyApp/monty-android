package com.monty.ui.photo.contract

import com.sumera.koreactor.reactor.data.MviState

data class ShowPhotoState(
    val photoAddress: String,
    val photoAddressType: String
) : MviState
