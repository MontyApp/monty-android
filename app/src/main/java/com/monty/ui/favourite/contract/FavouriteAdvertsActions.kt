package com.monty.ui.favourite.contract

import com.monty.data.model.ui.Advert
import com.sumera.koreactor.reactor.data.MviAction

sealed class FavouriteAdvertsAction : MviAction<FavouriteAdvertsState>

object OnRefreshAction : FavouriteAdvertsAction()

data class OnAdvertClickAction(private val advert: Advert) : FavouriteAdvertsAction()
