package com.monty.ui.detail.contract

import com.monty.data.model.ui.Advert
import com.monty.ui.base.placeholder.PlaceholderLayoutState
import com.sumera.koreactor.reactor.data.MviState

data class AdvertDetailState(
    val advert: Advert,
    val layoutState: PlaceholderLayoutState
) : MviState {

    companion object {
        val INITIAL = AdvertDetailState(
            advert = Advert.EMPTY,
            layoutState = PlaceholderLayoutState.DEFAULT
        )
    }
}
