package com.monty.ui.myadverts.contract

import com.sumera.koreactor.reactor.data.MviEvent

sealed class MyAdvertsEvent : MviEvent<MyAdvertsState>()

data class ErrorEvent(val message: String) : MyAdvertsEvent()

data class NavigateToAdvertDetailEvent(val advertId: String) : MyAdvertsEvent()

object NavigateToCreateAdvertEvent : MyAdvertsEvent()
