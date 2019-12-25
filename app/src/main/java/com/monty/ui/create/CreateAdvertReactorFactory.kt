package com.monty.ui.create

import com.sumera.koreactor.reactor.MviReactorFactory
import javax.inject.Inject
import javax.inject.Provider

class CreateAdvertReactorFactory @Inject constructor(
    private val reactorProvider: Provider<CreateAdvertReactor>
) : MviReactorFactory<CreateAdvertReactor>() {

    override val reactor: CreateAdvertReactor
        get() = reactorProvider.get()
}
