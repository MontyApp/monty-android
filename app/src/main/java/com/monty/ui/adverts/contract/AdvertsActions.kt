package com.monty.ui.adverts.contract

import com.monty.data.model.ui.Advert
import com.sumera.koreactor.reactor.data.MviAction

sealed class AdvertsAction : MviAction<AdvertsState>

object OnRefreshAction : AdvertsAction()

data class OnAdvertClickAction(private val advert: Advert) : AdvertsAction()
