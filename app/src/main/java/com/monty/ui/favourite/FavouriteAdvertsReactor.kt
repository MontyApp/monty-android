package com.monty.ui.favourite

import com.monty.domain.favourite.GetFavouriteAdvertsObservabler
import com.monty.domain.favourite.RemoveFavouriteAdvertCompletabler
import com.monty.ui.favourite.contract.*
import com.sumera.koreactor.reactor.MviReactor
import com.sumera.koreactor.reactor.data.MviAction
import io.reactivex.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class FavouriteAdvertsReactor @Inject constructor(
    private val getFavouriteAdvertsObservabler: GetFavouriteAdvertsObservabler,
    private val removeFavouriteAdvertCompletabler: RemoveFavouriteAdvertCompletabler
) : MviReactor<FavouriteAdvertsState>() {

    override fun createInitialState() = FavouriteAdvertsState.INITIAL

    override fun bind(actions: Observable<MviAction<FavouriteAdvertsState>>) {
        val onAdvertClickAction = actions.ofActionType<OnAdvertClickAction>()
        val onFavouriteClickAction = actions.ofActionType<OnFavouriteClickAction>()

        onAdvertClickAction
            .map { NavigateToAdvertDetailEvent(it.advert.id) }
            .bindToView()

        onFavouriteClickAction
            .flatMapCompletable { removeFavouriteAdvertCompletabler.init(it.advert.id).execute() }
            .toObservable<Unit>()
            .bindTo()

        attachLifecycleObservable
            .delay(1500, TimeUnit.MILLISECONDS)
            .flatMap { getFavouriteAdvertsObservabler.execute() }
            .map { UpdateFavouriteAdvertsReducer(it) }
            .bindToView()
    }
}
