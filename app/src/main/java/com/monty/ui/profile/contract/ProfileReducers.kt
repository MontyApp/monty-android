package com.monty.ui.profile.contract

import com.monty.data.model.ui.User
import com.monty.ui.base.SubmitButtonState
import com.sumera.koreactor.reactor.data.MviStateReducer

sealed class ProfileReducer : MviStateReducer<ProfileState>

data class ChangeProfileReducer(private val profile: User) : ProfileReducer() {
    override fun reduce(oldState: ProfileState) = oldState.copy(
        profile = profile,
        firstName = profile.firstName,
        lastName = profile.lastName,
        email = profile.email,
        phone = profile.phone
    )
}

data class ChangeFirstNameReducer(private val firstName: String) : ProfileReducer() {
    override fun reduce(oldState: ProfileState) = oldState.copy(firstName = firstName)
}

data class ChangeLastNameReducer(private val lastName: String) : ProfileReducer() {
    override fun reduce(oldState: ProfileState) = oldState.copy(lastName = lastName)
}

data class ChangeEmailReducer(private val email: String) : ProfileReducer() {
    override fun reduce(oldState: ProfileState) = oldState.copy(email = email)
}

data class ChangePhoneReducer(private val phone: String) : ProfileReducer() {
    override fun reduce(oldState: ProfileState) = oldState.copy(phone = phone)
}

data class ChangeButtonStateReducer(private val buttonState: SubmitButtonState) : ProfileReducer() {
    override fun reduce(oldState: ProfileState) = oldState.copy(buttonState = buttonState)
}

data class ChangeSignInStateReducer(private val signInState: SubmitButtonState) : ProfileReducer() {
    override fun reduce(oldState: ProfileState) = oldState.copy(signInState = signInState)
}