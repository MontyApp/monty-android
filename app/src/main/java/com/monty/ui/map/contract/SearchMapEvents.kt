package com.monty.ui.map.contract

import com.monty.data.model.ui.Address
import com.sumera.koreactor.reactor.data.MviEvent

sealed class SearchMapEvent : MviEvent<SearchMapState>()

object BackEvent : SearchMapEvent()

data class FinishWithResult(val address: Address) : SearchMapEvent()
