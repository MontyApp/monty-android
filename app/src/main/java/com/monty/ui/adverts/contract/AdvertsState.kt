package com.monty.ui.adverts.contract

import android.location.Location
import com.monty.data.model.ui.Advert
import com.monty.data.model.ui.Category
import com.monty.ui.base.placeholder.PlaceholderLayoutState
import com.sumera.koreactor.reactor.data.MviState

data class AdvertsState(
    val adverts: List<Advert>,
    val categories: List<Category>,
    val selectedCategory: Category,
    val showSkeleton: Boolean,
    val myLocation: Location,
    val isLocationAllowed: Boolean,
    val layoutState: PlaceholderLayoutState
) : MviState {

    companion object {
        val INITIAL = AdvertsState(
            adverts = listOf(),
            categories = listOf(),
            showSkeleton = false,
            isLocationAllowed = false,
            myLocation = Location(""),
            selectedCategory = Category.EMPTY,
            layoutState = PlaceholderLayoutState.DEFAULT
        )
    }
}
