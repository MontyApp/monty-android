package com.monty.ui.map.contract

import com.monty.data.model.ui.Address
import com.sumera.koreactor.reactor.data.MviState

data class SearchMapState(
    val address: Address
) : MviState {

    companion object {

        val INITAL = SearchMapState(
            address = Address.PRAGUE
        )
    }
}
