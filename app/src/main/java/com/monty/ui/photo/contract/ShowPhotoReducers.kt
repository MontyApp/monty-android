package com.monty.ui.photo.contract

import com.sumera.koreactor.reactor.data.MviStateReducer

sealed class ShowPhotoReducers : MviStateReducer<ShowPhotoState>

data class ChangePhotoStateReducer(
    private val photoAddress: String,
    private val photoAddressType: String
) : ShowPhotoReducers() {
    override fun reduce(oldState: ShowPhotoState): ShowPhotoState {
        return oldState.copy(photoAddress = photoAddress, photoAddressType = photoAddressType)
    }
}
