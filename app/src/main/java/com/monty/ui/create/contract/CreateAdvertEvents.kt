package com.monty.ui.create.contract

import com.sumera.koreactor.reactor.data.MviEvent

sealed class CreateAdvertEvent : MviEvent<CreateAdvertState>()

data class ErrorEvent(val message: String) : CreateAdvertEvent()

object SuccessEvent : CreateAdvertEvent()
