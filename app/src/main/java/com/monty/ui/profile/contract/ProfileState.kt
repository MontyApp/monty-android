package com.monty.ui.profile.contract

import com.monty.data.model.ui.User
import com.monty.ui.base.SubmitButtonState
import com.sumera.koreactor.reactor.data.MviState

data class ProfileState(
    val profile: User,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phone: String,
    val buttonState: SubmitButtonState,
    val signInState: SubmitButtonState
) : MviState {

    companion object {
        val INITIAL = ProfileState(
            profile = User.EMPTY,
            firstName = "",
            lastName = "",
            email = "",
            phone = "",
            buttonState = SubmitButtonState.DISABLED,
            signInState = SubmitButtonState.IDLE
        )
    }
}
