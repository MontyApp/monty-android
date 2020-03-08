package com.monty.ui.profile.contract

import com.sumera.koreactor.reactor.data.MviEvent

sealed class ProfileEvent : MviEvent<ProfileState>()

data class ErrorEvent(val message: String) : ProfileEvent()

object SignInEvent : ProfileEvent()
