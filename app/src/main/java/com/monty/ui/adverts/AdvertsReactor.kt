package com.monty.ui.adverts

import com.monty.domain.advert.GetAdvertsObservabler
import com.monty.domain.favourite.AddFavouriteAdvertCompletabler
import com.monty.domain.favourite.RemoveFavouriteAdvertCompletabler
import com.monty.ui.adverts.contract.*
import com.sumera.koreactor.reactor.MviReactor
import com.sumera.koreactor.reactor.data.MviAction
import io.reactivex.Observable
import javax.inject.Inject

class AdvertsReactor @Inject constructor(
    private val getAdvertsObservabler: GetAdvertsObservabler,
    private val addFavouriteAdvertCompletabler: AddFavouriteAdvertCompletabler,
    private val removeFavouriteAdvertCompletabler: RemoveFavouriteAdvertCompletabler
) : MviReactor<AdvertsState>() {

    override fun createInitialState() = AdvertsState.INITIAL

    override fun bind(actions: Observable<MviAction<AdvertsState>>) {
        val onAdvertClickAction = actions.ofActionType<OnAdvertClickAction>()
        val onFavouriteClickAction = actions.ofActionType<OnFavouriteClickAction>()

        onFavouriteClickAction
            .flatMapCompletable {
                if (it.advert.isFavourite) {
                    removeFavouriteAdvertCompletabler.init(it.advert.id).execute()
                } else {
                    addFavouriteAdvertCompletabler.init(it.advert.id).execute()
                }
            }.toObservable<Unit>()
            .bindTo()

        onAdvertClickAction
            .map { NavigateToAdvertDetailEvent(it.advert.id) }
            .bindToView()

        attachLifecycleObservable
            .flatMap { getAdvertsObservabler.execute() }
            .map { UpdateAdvertsReducer(it) }
            .bindToView()
    }
}
