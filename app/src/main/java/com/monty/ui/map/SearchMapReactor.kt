package com.monty.ui.map

import android.location.Location
import com.monty.data.model.ui.Address
import com.monty.domain.location.GetMyLocationObservabler
import com.monty.ui.map.contract.*
import com.sumera.koreactor.reactor.MviReactor
import com.sumera.koreactor.reactor.data.MviAction
import io.reactivex.Observable
import javax.inject.Inject

class SearchMapReactor @Inject constructor(
    private val getMyLocationObservabler: GetMyLocationObservabler
) : MviReactor<SearchMapState>() {

    override fun createInitialState() = SearchMapState.INITAL

    override fun bind(actions: Observable<MviAction<SearchMapState>>) {
        val onBackAction = actions.ofActionType<OnBackAction>()
        val onConfirmNewMarkerPositionAction = actions.ofActionType<OnConfirmNewMarkerPositionAction>()

        onBackAction.map { BackEvent }.bindToView()

        attachLifecycleObservable
            .flatMapSingle { getMyLocationObservabler.execute().first(Location("")) }
            .filter { it != Location("") }
            .map { ChangeAddressReducer(Address.EMPTY.copy(latitude = it.latitude, longitude = it.longitude)) }
            .bindToView()

        onConfirmNewMarkerPositionAction
            .flatMapSingle { action ->
                stateSingle.map {
                    FinishWithResult(Address.EMPTY.copy(latitude = action.lat, longitude = action.lon))
                }
            }
            .bindToView()
    }
}
