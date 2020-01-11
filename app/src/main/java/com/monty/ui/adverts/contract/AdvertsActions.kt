package com.monty.ui.adverts.contract

import com.monty.data.model.ui.Advert
import com.monty.data.model.ui.Category
import com.sumera.koreactor.reactor.data.MviAction

sealed class AdvertsAction : MviAction<AdvertsState>

object OnRefreshAction : AdvertsAction()

object OnCategoriesClickAction : AdvertsAction()

object OnAddAdvertClickAction : AdvertsAction()

data class OnCategoryClickAction(val category: Category) : AdvertsAction()

data class OnAdvertClickAction(val advert: Advert) : AdvertsAction()

data class OnFavouriteClickAction(val advert: Advert) : AdvertsAction()
