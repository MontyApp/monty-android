package com.monty.ui.main

import com.monty.domain.advert.SyncAdvertsCompletabler
import com.monty.ui.main.contract.MainState
import com.sumera.koreactor.reactor.MviReactor
import com.sumera.koreactor.reactor.data.MviAction
import io.reactivex.Observable
import javax.inject.Inject

class MainReactor @Inject constructor(
    private val syncAdvertsCompletabler: SyncAdvertsCompletabler
) : MviReactor<MainState>() {

    override fun bind(actions: Observable<MviAction<MainState>>) {
        attachLifecycleObservable
            .flatMapCompletable { syncAdvertsCompletabler.execute() }
            .toObservable<Unit>()
            .bindTo()
    }

    override fun createInitialState() = MainState
}
