package com.monty.ui.favourite.contract

import com.sumera.koreactor.reactor.data.MviEvent

sealed class FavouriteAdvertsEvent : MviEvent<FavouriteAdvertsState>()

data class ErrorEvent(val message: String) : FavouriteAdvertsEvent()

data class NavigateToAdvertDetailEvent(val advertId: String) : FavouriteAdvertsEvent()
