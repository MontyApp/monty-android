package com.monty.ui.photo.contract

import com.sumera.koreactor.reactor.data.MviEvent

sealed class ShowPhotoEvents : MviEvent<ShowPhotoState>()

object BackEvent : ShowPhotoEvents()
