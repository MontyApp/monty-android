package com.monty.ui.map.contract

import com.monty.data.model.ui.Address
import com.sumera.koreactor.reactor.data.MviStateReducer

sealed class SearchMapReducer : MviStateReducer<SearchMapState>

class ChangeAddressReducer(private val address: Address) : SearchMapReducer() {
    override fun reduce(oldState: SearchMapState) = oldState.copy(address = address)
}
