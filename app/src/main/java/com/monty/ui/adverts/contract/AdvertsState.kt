package com.monty.ui.adverts.contract

import com.monty.data.model.ui.Advert
import com.monty.data.model.ui.Category
import com.monty.ui.base.placeholder.PlaceholderLayoutState
import com.monty.ui.common.sort.SortOption
import com.sumera.koreactor.reactor.data.MviState

data class AdvertsState(
    val adverts: List<Advert>,
    val categories: List<Category>,
    val selectedCategory: Category,
    val selectedSortOption: SortOption,
    val showSkeleton: Boolean,
    val layoutState: PlaceholderLayoutState
) : MviState {

    companion object {
        val INITIAL = AdvertsState(
            adverts = listOf(),
            categories = listOf(),
            showSkeleton = false,
            selectedCategory = Category.EMPTY,
            selectedSortOption = SortOption.IDLE,
            layoutState = PlaceholderLayoutState.DEFAULT
        )
    }
}
