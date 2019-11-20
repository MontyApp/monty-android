package com.monty.ui.profile.contract

import com.sumera.koreactor.reactor.data.MviState

data class ProfileState(
    val profile: String
) : MviState {

    companion object {
        val INITIAL = ProfileState(
            profile = ""
        )
    }
}
