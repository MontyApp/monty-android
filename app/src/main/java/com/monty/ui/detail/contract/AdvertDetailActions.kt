package com.monty.ui.detail.contract

import com.sumera.koreactor.reactor.data.MviAction

sealed class AdvertDetailAction : MviAction<AdvertDetailState>

object OnRefreshAction : AdvertDetailAction()

object OnDeleteAction : AdvertDetailAction()

object OnDeletePositiveAction : AdvertDetailAction()

object OnBackAction : AdvertDetailAction()

object OnMapAction : AdvertDetailAction()

object OnFavouriteAction : AdvertDetailAction()

object OnEditAction : AdvertDetailAction()

object OnContactAction : AdvertDetailAction()

object OnContactPhoneAction : AdvertDetailAction()

object OnContactEmailAction : AdvertDetailAction()

object OnPhotoClick: AdvertDetailAction()
