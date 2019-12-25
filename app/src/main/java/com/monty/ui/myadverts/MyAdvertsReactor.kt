package com.monty.ui.myadverts

import com.monty.domain.advert.GetAdvertsObservabler
import com.monty.ui.myadverts.contract.MyAdvertsState
import com.monty.ui.myadverts.contract.NavigateToAdvertDetailEvent
import com.monty.ui.myadverts.contract.NavigateToCreateAdvertEvent
import com.monty.ui.myadverts.contract.OnAddAdvertAction
import com.monty.ui.myadverts.contract.OnAdvertClickAction
import com.monty.ui.myadverts.contract.UpdateMyAdvertsReducer
import com.sumera.koreactor.reactor.MviReactor
import com.sumera.koreactor.reactor.data.MviAction
import io.reactivex.Observable
import javax.inject.Inject

class MyAdvertsReactor @Inject constructor(
    private val getAdvertsObservabler: GetAdvertsObservabler
) : MviReactor<MyAdvertsState>() {

    override fun createInitialState() = MyAdvertsState.INITIAL

    override fun bind(actions: Observable<MviAction<MyAdvertsState>>) {
        val onAdvertClickAction = actions.ofActionType<OnAdvertClickAction>()
        val onAddAdvertAction = actions.ofActionType<OnAddAdvertAction>()

        onAdvertClickAction
            .map { NavigateToAdvertDetailEvent(it.advert.id) }
            .bindToView()

        onAddAdvertAction
            .map { NavigateToCreateAdvertEvent }
            .bindToView()

        attachLifecycleObservable
            .flatMap { getAdvertsObservabler.execute() }
            .map { UpdateMyAdvertsReducer(it.sortedByDescending { it.id }) }
            .bindToView()
    }
}
