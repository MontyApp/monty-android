package com.monty.ui.main

import com.monty.ui.main.contract.MainState
import com.sumera.koreactor.reactor.MviReactor
import com.sumera.koreactor.reactor.data.MviAction
import io.reactivex.Observable
import javax.inject.Inject

class MainReactor @Inject constructor() : MviReactor<MainState>() {

    override fun bind(actions: Observable<MviAction<MainState>>) {}

    override fun createInitialState() = MainState
}
