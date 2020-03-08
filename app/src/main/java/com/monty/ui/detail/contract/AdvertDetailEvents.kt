package com.monty.ui.detail.contract

import com.monty.data.model.ui.User
import com.sumera.koreactor.reactor.data.MviEvent

sealed class AdvertDetailEvent : MviEvent<AdvertDetailState>()

data class ErrorEvent(val message: String) : AdvertDetailEvent()

object BackEvent : AdvertDetailEvent()

object ShowDeleteDialogEvent : AdvertDetailEvent()

data class ShowContactDialog(val user: User) : AdvertDetailEvent()

data class NavigateToPhoneEvent(val phone: String) : AdvertDetailEvent()

data class NavigateToEditEvent(val advertId: String) : AdvertDetailEvent()

data class NavigateToMapEvent(val lat: Double, val lon: Double) : AdvertDetailEvent()

data class NavigateToEmailEvent(val email: String, val title: String) : AdvertDetailEvent()

data class NavigateToShowPhotoEvent(val url: String) : AdvertDetailEvent()
