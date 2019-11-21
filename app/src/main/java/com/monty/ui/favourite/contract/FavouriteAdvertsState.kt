package com.monty.ui.favourite.contract

import com.monty.data.model.ui.Advert
import com.monty.ui.base.placeholder.PlaceholderLayoutState
import com.sumera.koreactor.reactor.data.MviState

data class FavouriteAdvertsState(
    val favouriteAdverts: List<Advert>,
    val layoutState: PlaceholderLayoutState
) : MviState {

    companion object {
        val INITIAL = FavouriteAdvertsState(
            favouriteAdverts = listOf(),
            layoutState = PlaceholderLayoutState.DEFAULT
        )
    }
}
