package com.monty.ui.create.contract

import com.monty.data.model.ui.Price
import com.sumera.koreactor.reactor.data.MviStateReducer

sealed class CreateAdvertReducer : MviStateReducer<CreateAdvertState>

data class ChangeTitleReducer(private val title: String) : CreateAdvertReducer() {
    override fun reduce(oldState: CreateAdvertState) = oldState.copy(title = title)
}

//data class ChangeImageReducer(private val image: String) : CreateAdvertReducer() {
//    override fun reduce(oldState: CreateAdvertState) = oldState.copy(image = image)
//}

data class ChangeDescriptionReducer(private val description: String) : CreateAdvertReducer() {
    override fun reduce(oldState: CreateAdvertState) = oldState.copy(description = description)
}

data class ChangePriceReducer(private val price: Float) : CreateAdvertReducer() {
    override fun reduce(oldState: CreateAdvertState) = oldState.copy(price = price)
}

data class ChangeDepositReducer(private val deposit: Float) : CreateAdvertReducer() {
    override fun reduce(oldState: CreateAdvertState) = oldState.copy(deposit = deposit)
}
