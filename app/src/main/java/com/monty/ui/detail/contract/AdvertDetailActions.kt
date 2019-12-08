package com.monty.ui.detail.contract

import com.sumera.koreactor.reactor.data.MviAction

sealed class AdvertDetailAction : MviAction<AdvertDetailState>

object OnRefreshAction : AdvertDetailAction()
