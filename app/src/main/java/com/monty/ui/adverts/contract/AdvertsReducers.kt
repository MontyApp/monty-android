package com.monty.ui.adverts.contract

import com.monty.data.model.ui.Advert
import com.monty.ui.base.placeholder.PartialLayoutState
import com.monty.ui.base.placeholder.ViewState
import com.sumera.koreactor.reactor.data.MviStateReducer

sealed class AdvertsReducer : MviStateReducer<AdvertsState>

data class UpdateAdvertsReducer(private val items: List<Advert>) : AdvertsReducer() {
    override fun reduce(oldState: AdvertsState): AdvertsState {
        return if (items.isEmpty()) {
            oldState.copy(
                adverts = items,
                layoutState = PartialLayoutState(ViewState.EMPTY).toFull(oldState.layoutState)
            )
        } else {
            oldState.copy(
                adverts = items,
                layoutState = PartialLayoutState(ViewState.CONTENT).toFull(oldState.layoutState)
            )
        }
    }
}

data class ChangeLayoutStateReducer(private val partialLayoutState: PartialLayoutState) : AdvertsReducer() {
    override fun reduce(oldState: AdvertsState): AdvertsState {
        return oldState.copy(layoutState = partialLayoutState.toFull(oldState.layoutState))
    }
}
