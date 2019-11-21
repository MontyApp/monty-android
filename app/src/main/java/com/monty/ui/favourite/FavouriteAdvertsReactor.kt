package com.monty.ui.favourite

import com.monty.domain.adverts
import com.monty.ui.favourite.contract.FavouriteAdvertsState
import com.monty.ui.favourite.contract.UpdateFavouriteAdvertsReducer
import com.sumera.koreactor.reactor.MviReactor
import com.sumera.koreactor.reactor.data.MviAction
import io.reactivex.Observable
import javax.inject.Inject

class FavouriteAdvertsReactor @Inject constructor() : MviReactor<FavouriteAdvertsState>() {

    override fun createInitialState() = FavouriteAdvertsState.INITIAL

    override fun bind(actions: Observable<MviAction<FavouriteAdvertsState>>) {
       attachLifecycleObservable
           .map { UpdateFavouriteAdvertsReducer(adverts) }
           .bindToView()
    }
}
