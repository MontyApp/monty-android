package com.monty.ui.favourite.contract

import android.location.Location
import com.monty.data.model.ui.Advert
import com.monty.ui.base.placeholder.PlaceholderLayoutState
import com.sumera.koreactor.reactor.data.MviState

data class FavouriteAdvertsState(
    val favouriteAdverts: List<Advert>,
    val myLocation: Location,
    val layoutState: PlaceholderLayoutState
) : MviState {

    companion object {
        val INITIAL = FavouriteAdvertsState(
            favouriteAdverts = listOf(),
            myLocation = Location(""),
            layoutState = PlaceholderLayoutState.DEFAULT
        )
    }
}
