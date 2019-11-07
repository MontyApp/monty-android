package com.monty.ui.adverts

import com.monty.domain.adverts
import com.monty.ui.adverts.contract.AdvertsState
import com.monty.ui.adverts.contract.UpdateAdvertsReducer
import com.sumera.koreactor.reactor.MviReactor
import com.sumera.koreactor.reactor.data.MviAction
import io.reactivex.Observable
import javax.inject.Inject

class AdvertsReactor @Inject constructor() : MviReactor<AdvertsState>() {

    override fun createInitialState() = AdvertsState.INITIAL

    override fun bind(actions: Observable<MviAction<AdvertsState>>) {
       attachLifecycleObservable
           .map { UpdateAdvertsReducer(adverts) }
           .bindToView()
    }
}
