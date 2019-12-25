package com.monty.ui.create.contract

import com.monty.data.model.ui.Advert
import com.monty.ui.base.SubmitState
import com.sumera.koreactor.reactor.data.MviState
import java.io.File

data class CreateAdvertState(
    val title: String,
    val description: String,
    val price: Float,
    val deposit: Float,
    val advert: Advert,
    val photo: File?,
    val photoUuid: String?,
    val photoState: SubmitState
    ) : MviState {

    companion object {
        val INITIAL = CreateAdvertState(
            title = "",
            description = "",
            price = 0f,
            deposit = 0f,
            advert = Advert.EMPTY,
            photo = null,
            photoUuid = null,
            photoState = SubmitState.IDLE
        )
    }
}
