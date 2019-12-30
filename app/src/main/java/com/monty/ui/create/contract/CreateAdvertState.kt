package com.monty.ui.create.contract

import com.monty.data.model.ui.Advert
import com.monty.data.model.ui.SpinnerData
import com.monty.data.model.ui.type.IntervalType
import com.monty.ui.base.SubmitButtonState
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
    val image: String,
    val photoState: SubmitState,
    val buttonState: SubmitButtonState,
    val intervalTypes: List<IntervalType>,
    val selectedIntervalType: SpinnerData?
    ) : MviState {

    companion object {
        val INITIAL = CreateAdvertState(
            title = "",
            description = "",
            price = 0f,
            deposit = 0f,
            advert = Advert.EMPTY,
            photo = null,
            image = "",
            photoState = SubmitState.IDLE,
            buttonState = SubmitButtonState.IDLE,
            intervalTypes = emptyList(),
            selectedIntervalType = null
        )
    }
}
