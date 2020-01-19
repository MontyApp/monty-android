package com.monty.ui.myadverts.contract

import android.location.Location
import com.monty.data.model.ui.Advert
import com.monty.ui.base.placeholder.PartialLayoutState
import com.monty.ui.base.placeholder.ViewState
import com.sumera.koreactor.reactor.data.MviStateReducer

sealed class MyAdvertsReducer : MviStateReducer<MyAdvertsState>

data class UpdateMyAdvertsReducer(private val items: List<Advert>) : MyAdvertsReducer() {
    override fun reduce(oldState: MyAdvertsState): MyAdvertsState {
        return if (items.isEmpty()) {
            oldState.copy(
                myAdverts = items,
                layoutState = PartialLayoutState(ViewState.EMPTY).toFull(oldState.layoutState)
            )
        } else {
            oldState.copy(
                myAdverts = items,
                layoutState = PartialLayoutState(ViewState.CONTENT).toFull(oldState.layoutState)
            )
        }
    }
}

data class ChangeLayoutStateReducer(private val partialLayoutState: PartialLayoutState) : MyAdvertsReducer() {
    override fun reduce(oldState: MyAdvertsState): MyAdvertsState {
        return oldState.copy(layoutState = partialLayoutState.toFull(oldState.layoutState))
    }
}

data class ChangeMyLocationReducer(private val myLocation: Location) : MyAdvertsReducer() {
    override fun reduce(oldState: MyAdvertsState) = oldState.copy(myLocation = myLocation)
}