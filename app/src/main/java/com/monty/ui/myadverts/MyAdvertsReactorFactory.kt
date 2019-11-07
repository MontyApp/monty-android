package com.monty.ui.myadverts

import com.sumera.koreactor.reactor.MviReactorFactory
import javax.inject.Inject
import javax.inject.Provider

class MyAdvertsReactorFactory @Inject constructor(
    private val reactorProvider: Provider<MyAdvertsReactor>
) : MviReactorFactory<MyAdvertsReactor>() {

    override val reactor: MyAdvertsReactor
        get() = reactorProvider.get()
}
