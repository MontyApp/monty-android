package com.monty.ui.myadverts

import com.monty.domain.advert.GetAdvertsObservabler
import com.monty.domain.location.GetMyLocationObservabler
import com.monty.ui.myadverts.contract.*
import com.sumera.koreactor.reactor.MviReactor
import com.sumera.koreactor.reactor.data.MviAction
import io.reactivex.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MyAdvertsReactor @Inject constructor(
    private val getAdvertsObservabler: GetAdvertsObservabler,
    private val getMyLocationObservabler: GetMyLocationObservabler
) : MviReactor<MyAdvertsState>() {

    override fun createInitialState() = MyAdvertsState.INITIAL

    override fun bind(actions: Observable<MviAction<MyAdvertsState>>) {
        val onAdvertClickAction = actions.ofActionType<OnAdvertClickAction>()
        val onAddAdvertAction = actions.ofActionType<OnAddAdvertAction>()
        val onRefreshAction = actions.ofActionType<OnRefreshAction>()

        onAdvertClickAction
            .map { NavigateToAdvertDetailEvent(it.advert.id) }
            .bindToView()

        onAddAdvertAction
            .map { NavigateToCreateAdvertEvent }
            .bindToView()

        Observable.merge(resumeLifecycleObservable, onRefreshAction)
            .flatMap { getMyLocationObservabler.execute() }
            .map { ChangeMyLocationReducer(it) }
            .bindToView()

        attachLifecycleObservable
            .delay(1500, TimeUnit.MILLISECONDS)
            .flatMap { getAdvertsObservabler.execute() }
            .map { UpdateMyAdvertsReducer(it.sortedByDescending { it.id }) }
            .bindToView()
    }
}
