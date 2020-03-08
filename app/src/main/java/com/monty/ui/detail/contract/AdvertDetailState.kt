package com.monty.ui.detail.contract

import android.location.Location
import com.monty.data.model.ui.Advert
import com.monty.data.model.ui.User
import com.monty.ui.base.placeholder.PlaceholderLayoutState
import com.sumera.koreactor.reactor.data.MviState

data class AdvertDetailState(
    val advert: Advert,
    val user: User,
    val profile: User,
    val myLocation: Location,
    val layoutState: PlaceholderLayoutState
) : MviState {

    companion object {
        val INITIAL = AdvertDetailState(
            advert = Advert.EMPTY,
            user = User.EMPTY,
            myLocation = Location(""),
            layoutState = PlaceholderLayoutState.DEFAULT
        )
    }
}
