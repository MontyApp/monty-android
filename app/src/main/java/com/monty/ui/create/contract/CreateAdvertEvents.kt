package com.monty.ui.create.contract

import com.sumera.koreactor.reactor.data.MviEvent

sealed class CreateAdvertEvent : MviEvent<CreateAdvertState>()

data class ErrorEvent(val message: String) : CreateAdvertEvent()

object ShowGetPhotoDialogEvent : CreateAdvertEvent()

object ShowCategoriesEvent : CreateAdvertEvent()

object OpenCameraEvent : CreateAdvertEvent()

object OpenGalleryEvent : CreateAdvertEvent()

object BackEvent : CreateAdvertEvent()

object SuccessEvent : CreateAdvertEvent()
