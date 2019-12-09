package com.monty.ui.photo.contract

import com.sumera.koreactor.reactor.data.MviAction

sealed class ShowPhotoActions : MviAction<ShowPhotoState>

object OnBackAction : ShowPhotoActions()
