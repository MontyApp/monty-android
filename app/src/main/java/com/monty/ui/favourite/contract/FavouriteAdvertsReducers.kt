package com.monty.ui.favourite.contract

import com.monty.data.model.ui.Advert
import com.monty.ui.base.placeholder.PartialLayoutState
import com.monty.ui.base.placeholder.ViewState
import com.sumera.koreactor.reactor.data.MviStateReducer

sealed class FavouriteAdvertsReducer : MviStateReducer<FavouriteAdvertsState>

data class UpdateFavouriteAdvertsReducer(private val items: List<Advert>) : FavouriteAdvertsReducer() {
    override fun reduce(oldState: FavouriteAdvertsState): FavouriteAdvertsState {
        return if (items.isEmpty()) {
            oldState.copy(
                favouriteAdverts = items,
                layoutState = PartialLayoutState(ViewState.EMPTY).toFull(oldState.layoutState)
            )
        } else {
            oldState.copy(
                favouriteAdverts = items,
                layoutState = PartialLayoutState(ViewState.CONTENT).toFull(oldState.layoutState)
            )
        }
    }
}

data class ChangeLayoutStateReducer(private val partialLayoutState: PartialLayoutState) : FavouriteAdvertsReducer() {
    override fun reduce(oldState: FavouriteAdvertsState): FavouriteAdvertsState {
        return oldState.copy(layoutState = partialLayoutState.toFull(oldState.layoutState))
    }
}
