package com.monty.ui.adverts.contract

import android.location.Location
import com.monty.data.model.ui.Advert
import com.monty.data.model.ui.Category
import com.monty.ui.base.placeholder.PartialLayoutState
import com.monty.ui.base.placeholder.ViewState
import com.sumera.koreactor.reactor.data.MviStateReducer

sealed class AdvertsReducer : MviStateReducer<AdvertsState>

data class UpdateAdvertsReducer(private val items: List<Advert>) : AdvertsReducer() {
    override fun reduce(oldState: AdvertsState): AdvertsState {
        return if (items.isEmpty()) {
            oldState.copy(
                adverts = items,
                showSkeleton = false,
                layoutState = PartialLayoutState(ViewState.EMPTY).toFull(oldState.layoutState)
            )
        } else {
            oldState.copy(
                adverts = items,
                showSkeleton = false,
                layoutState = PartialLayoutState(ViewState.CONTENT).toFull(oldState.layoutState)
            )
        }
    }
}

data class ChangeLayoutStateReducer(private val partialLayoutState: PartialLayoutState) : AdvertsReducer() {
    override fun reduce(oldState: AdvertsState) = oldState.copy(layoutState = partialLayoutState.toFull(oldState.layoutState))
}

data class ChangeCategoriesReducer(private val categories: List<Category>) : AdvertsReducer() {
    override fun reduce(oldState: AdvertsState) = oldState.copy(categories = categories)
}

data class ChangeSelectedCategoryReducer(private val selectedCategory: Category) : AdvertsReducer() {
    override fun reduce(oldState: AdvertsState) = oldState.copy(selectedCategory = selectedCategory)
}

data class ChangeShowSkeletonReducer(private val showSkeleton: Boolean) : AdvertsReducer() {
    override fun reduce(oldState: AdvertsState) = oldState.copy(showSkeleton = showSkeleton)
}

data class ChangeMyLocationReducer(private val myLocation: Location) : AdvertsReducer() {
    override fun reduce(oldState: AdvertsState) = oldState.copy(myLocation = myLocation)
}

data class ChangeIsLocationAllowedRecuder(private val isLocationAllowed: Boolean) : AdvertsReducer() {
    override fun reduce(oldState: AdvertsState) = oldState.copy(isLocationAllowed = isLocationAllowed)
}