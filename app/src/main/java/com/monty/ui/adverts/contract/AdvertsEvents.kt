package com.monty.ui.adverts.contract

import com.sumera.koreactor.reactor.data.MviEvent

sealed class AdvertsEvent : MviEvent<AdvertsState>()

object NavigateToCreateAdvertEvent : MviEvent<AdvertsState>()

data class ErrorEvent(val message: String) : AdvertsEvent()

data class NavigateToAdvertDetailEvent(val advertId: Int) : AdvertsEvent()

object ShowCategoriesDialogEvent : AdvertsEvent()
