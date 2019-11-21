package com.monty.ui.myadverts

import com.monty.domain.adverts
import com.monty.ui.myadverts.contract.MyAdvertsState
import com.monty.ui.myadverts.contract.UpdateMyAdvertsReducer
import com.sumera.koreactor.reactor.MviReactor
import com.sumera.koreactor.reactor.data.MviAction
import io.reactivex.Observable
import javax.inject.Inject

class MyAdvertsReactor @Inject constructor() : MviReactor<MyAdvertsState>() {

    override fun createInitialState() = MyAdvertsState.INITIAL

    override fun bind(actions: Observable<MviAction<MyAdvertsState>>) {
       attachLifecycleObservable
           .map { UpdateMyAdvertsReducer(adverts.sortedByDescending { it.id }) }
           .bindToView()
    }
}
