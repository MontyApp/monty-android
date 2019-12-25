package com.monty.ui.create.contract

import com.sumera.koreactor.reactor.data.MviAction

sealed class CreateAdvertAction : MviAction<CreateAdvertState>

data class OnTitleChangeAction(val title: String) : CreateAdvertAction()

data class OnDescriptionChangeAction(val description: String) : CreateAdvertAction()

data class OnImageChangeAction(val image: String) : CreateAdvertAction()

data class OnPriceChangeAction(val price: String) : CreateAdvertAction()

data class OnDepositChangeAction(val deposit: String) : CreateAdvertAction()

object OnAddImageClickAction : CreateAdvertAction()

object OnAddAdvertAction : CreateAdvertAction()
