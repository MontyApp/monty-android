package com.monty.ui.myadverts

import com.monty.domain.advert.GetAdvertsObservabler
import com.monty.domain.favourite.AddFavouriteAdvertCompletabler
import com.monty.domain.favourite.RemoveFavouriteAdvertCompletabler
import com.monty.domain.location.GetMyLocationObservabler
import com.monty.ui.myadverts.contract.*
import com.sumera.koreactor.reactor.MviReactor
import com.sumera.koreactor.reactor.data.MviAction
import io.reactivex.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MyAdvertsReactor @Inject constructor(
    private val getAdvertsObservabler: GetAdvertsObservabler,
    private val getMyLocationObservabler: GetMyLocationObservabler,
    private val addFavouriteAdvertCompletabler: AddFavouriteAdvertCompletabler,
    private val removeFavouriteAdvertCompletabler: RemoveFavouriteAdvertCompletabler
) : MviReactor<MyAdvertsState>() {

    override fun createInitialState() = MyAdvertsState.INITIAL

    override fun bind(actions: Observable<MviAction<MyAdvertsState>>) {
        val onAdvertClickAction = actions.ofActionType<OnAdvertClickAction>()
        val onAddAdvertAction = actions.ofActionType<OnAddAdvertAction>()
        val onRefreshAction = actions.ofActionType<OnRefreshAction>()
        val onFavouriteAction = actions.ofActionType<OnFavouriteAction>()

        onAdvertClickAction
            .map { NavigateToAdvertDetailEvent(it.advert.id) }
            .bindToView()

        onAddAdvertAction
            .map { NavigateToCreateAdvertEvent }
            .bindToView()

        onFavouriteAction
            .flatMapCompletable {
                if (it.advert.isFavourite) {
                    removeFavouriteAdvertCompletabler.init(it.advert.id).execute()
                } else {
                    addFavouriteAdvertCompletabler.init(it.advert.id).execute()
                }
            }.toObservable<Unit>()
            .bindTo()

        Observable.merge(resumeLifecycleObservable, onRefreshAction)
            .flatMap { getMyLocationObservabler.execute() }
            .map { ChangeMyLocationReducer(it) }
            .bindToView()

        attachLifecycleObservable
            .delay(1500, TimeUnit.MILLISECONDS)
            .flatMap { getAdvertsObservabler.execute() }
            .map { UpdateMyAdvertsReducer(it.sortedByDescending { it.createdAt }) }
            .bindToView()
    }
}
