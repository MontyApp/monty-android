package com.monty.ui.myadverts.contract

import com.monty.data.model.ui.Advert
import com.sumera.koreactor.reactor.data.MviAction

sealed class MyAdvertsAction : MviAction<MyAdvertsState>

object OnRefreshAction : MyAdvertsAction()

data class OnAdvertClickAction(val advert: Advert) : MyAdvertsAction()
