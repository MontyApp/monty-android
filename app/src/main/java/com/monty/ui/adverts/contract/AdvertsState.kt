package com.monty.ui.adverts.contract

import com.monty.data.model.ui.Advert
import com.monty.data.model.ui.Category
import com.monty.ui.base.placeholder.PlaceholderLayoutState
import com.sumera.koreactor.reactor.data.MviState

data class AdvertsState(
    val adverts: List<Advert>,
    val categories: List<Category>,
    val selectedCategory: Category,
    val layoutState: PlaceholderLayoutState
) : MviState {

    companion object {
        val INITIAL = AdvertsState(
            adverts = listOf(),
            categories = listOf(),
            selectedCategory = Category.EMPTY,
            layoutState = PlaceholderLayoutState.DEFAULT
        )
    }
}
