package com.monty.ui.map.contract

import com.sumera.koreactor.reactor.data.MviAction

sealed class SearchMapAction : MviAction<SearchMapState>

object OnBackAction : SearchMapAction()

data class OnConfirmNewMarkerPositionAction(val lat: Double, val lon: Double) : SearchMapAction()
