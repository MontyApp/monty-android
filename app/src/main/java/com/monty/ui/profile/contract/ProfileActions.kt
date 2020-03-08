package com.monty.ui.profile.contract

import com.google.firebase.auth.FirebaseUser
import com.sumera.koreactor.reactor.data.MviAction

sealed class ProfileAction : MviAction<ProfileState>

object OnRefreshAction : ProfileAction()

object OnButtonAction : ProfileAction()

object OnSignInAction : ProfileAction()

data class OnSignInSuccessAction(val user: FirebaseUser?) : ProfileAction()

object OnSignInStartAction : ProfileAction()

data class OnFirstNameChangeAction(val firstName: String) : ProfileAction()

data class OnLastNameChangeAction(val lastName: String) : ProfileAction()

data class OnEmailChangeAction(val email: String) : ProfileAction()

data class OnPhoneChangeAction(val phone: String) : ProfileAction()

