package com.monty.ui.detail.contract

import com.monty.data.model.ui.Advert
import com.monty.ui.base.placeholder.PartialLayoutState
import com.sumera.koreactor.reactor.data.MviStateReducer

sealed class AdvertDetailReducer : MviStateReducer<AdvertDetailState>

data class UpdateAdvertReducer(private val advert: Advert) : AdvertDetailReducer() {
    override fun reduce(oldState: AdvertDetailState) = oldState.copy(advert = advert)
}

data class ChangeLayoutStateReducer(private val partialLayoutState: PartialLayoutState) : AdvertDetailReducer() {
    override fun reduce(oldState: AdvertDetailState): AdvertDetailState {
        return oldState.copy(layoutState = partialLayoutState.toFull(oldState.layoutState))
    }
}
