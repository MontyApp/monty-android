package com.monty.ui.profile.contract

import com.sumera.koreactor.reactor.data.MviAction

sealed class ProfileAction : MviAction<ProfileState>

object OnRefreshAction : ProfileAction()
