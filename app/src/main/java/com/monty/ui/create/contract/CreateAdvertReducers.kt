package com.monty.ui.create.contract

import com.monty.data.model.ui.SpinnerData
import com.monty.data.model.ui.type.IntervalType
import com.monty.ui.base.SubmitButtonState
import com.monty.ui.base.SubmitState
import com.sumera.koreactor.reactor.data.MviStateReducer
import java.io.File

sealed class CreateAdvertReducer : MviStateReducer<CreateAdvertState>

data class ChangeTitleReducer(private val title: String) : CreateAdvertReducer() {
    override fun reduce(oldState: CreateAdvertState) = oldState.copy(title = title)
}

data class ChangeDescriptionReducer(private val description: String) : CreateAdvertReducer() {
    override fun reduce(oldState: CreateAdvertState) = oldState.copy(description = description)
}

data class ChangePriceReducer(private val price: Float) : CreateAdvertReducer() {
    override fun reduce(oldState: CreateAdvertState) = oldState.copy(price = price)
}

data class ChangeDepositReducer(private val deposit: Float) : CreateAdvertReducer() {
    override fun reduce(oldState: CreateAdvertState) = oldState.copy(deposit = deposit)
}

data class ChangePhotoFileReducer(private val photo: File) : CreateAdvertReducer() {
    override fun reduce(oldState: CreateAdvertState) = oldState.copy(photo = photo)
}

data class ChangeImageReducer(private val image: String) : CreateAdvertReducer() {
    override fun reduce(oldState: CreateAdvertState) = oldState.copy(image = image)
}

data class ChangePhotoStateReducer(private val state: SubmitState) : CreateAdvertReducer() {
    override fun reduce(oldState: CreateAdvertState) = oldState.copy(photoState = state)
}

data class ChangeIntervalTypesReducer(private val intervalTypes: List<IntervalType>) : CreateAdvertReducer() {
    override fun reduce(oldState: CreateAdvertState) = oldState.copy(intervalTypes = intervalTypes)
}

data class ChangeSelectedIntervalTypeReducer(private val selectedIntervalType : SpinnerData) : CreateAdvertReducer() {
    override fun reduce(oldState: CreateAdvertState) = oldState.copy(selectedIntervalType = selectedIntervalType)
}

data class ChangeButtonStateReducer(private val buttonState : SubmitButtonState) : CreateAdvertReducer() {
    override fun reduce(oldState: CreateAdvertState) = oldState.copy(buttonState = buttonState)
}

