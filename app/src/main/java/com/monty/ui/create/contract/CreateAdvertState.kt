package com.monty.ui.create.contract

import com.monty.data.model.ui.Advert
import com.sumera.koreactor.reactor.data.MviState

data class CreateAdvertState(
    val title: String,
    val description: String,
    val price: Float,
    val deposit: Float,
    val advert: Advert
) : MviState {

    companion object {
        val INITIAL = CreateAdvertState(
            title = "",
            description = "",
            price = 0f,
            deposit = 0f,
            advert = Advert.EMPTY
        )
    }
}
