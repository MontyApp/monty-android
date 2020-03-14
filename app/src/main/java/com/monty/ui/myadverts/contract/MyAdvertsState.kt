package com.monty.ui.myadverts.contract

import android.location.Location
import com.monty.data.model.ui.Advert
import com.monty.data.model.ui.User
import com.monty.ui.base.placeholder.PlaceholderLayoutState
import com.sumera.koreactor.reactor.data.MviState

data class MyAdvertsState(
    val myAdverts: List<Advert>,
    val myLocation: Location,
    val profile: User,
    val layoutState: PlaceholderLayoutState
) : MviState {

    companion object {
        val INITIAL = MyAdvertsState(
            myAdverts = listOf(),
            myLocation = Location(""),
            profile = User.EMPTY,
            layoutState = PlaceholderLayoutState.DEFAULT
        )
    }
}
