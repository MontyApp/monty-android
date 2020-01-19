package com.monty.ui.detail.contract

import android.location.Location
import com.monty.data.model.ui.Advert
import com.monty.data.model.ui.User
import com.monty.ui.base.placeholder.PartialLayoutState
import com.sumera.koreactor.reactor.data.MviStateReducer

sealed class AdvertDetailReducer : MviStateReducer<AdvertDetailState>

data class UpdateAdvertReducer(private val advert: Advert) : AdvertDetailReducer() {
    override fun reduce(oldState: AdvertDetailState) = oldState.copy(advert = advert)
}

data class ChangeUserReducer(private val user: User) : AdvertDetailReducer() {
    override fun reduce(oldState: AdvertDetailState) = oldState.copy(user = user)
}

data class ChangeMyLocationReducer(private val myLocation: Location) : AdvertDetailReducer() {
    override fun reduce(oldState: AdvertDetailState) = oldState.copy(myLocation = myLocation)
}

data class ChangeLayoutStateReducer(private val partialLayoutState: PartialLayoutState) : AdvertDetailReducer() {
    override fun reduce(oldState: AdvertDetailState): AdvertDetailState {
        return oldState.copy(layoutState = partialLayoutState.toFull(oldState.layoutState))
    }
}
