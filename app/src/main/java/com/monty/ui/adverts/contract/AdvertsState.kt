package com.monty.ui.adverts.contract

import com.monty.data.model.ui.Advert
import com.monty.ui.base.placeholder.PlaceholderLayoutState
import com.sumera.koreactor.reactor.data.MviState

data class AdvertsState(
    val adverts: List<Advert>,
    val layoutState: PlaceholderLayoutState
) : MviState {

    companion object {
        val INITIAL = AdvertsState(
            adverts = listOf(),
            layoutState = PlaceholderLayoutState.DEFAULT
        )
    }
}
