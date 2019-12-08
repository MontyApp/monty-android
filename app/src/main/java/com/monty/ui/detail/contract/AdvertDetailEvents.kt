package com.monty.ui.detail.contract

import com.sumera.koreactor.reactor.data.MviEvent

sealed class AdvertDetailEvent : MviEvent<AdvertDetailState>()

data class ErrorEvent(val message: String) : AdvertDetailEvent()
