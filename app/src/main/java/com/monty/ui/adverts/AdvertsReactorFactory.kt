package com.monty.ui.adverts

import com.sumera.koreactor.reactor.MviReactorFactory
import javax.inject.Inject
import javax.inject.Provider

class AdvertsReactorFactory @Inject constructor(
    private val reactorProvider: Provider<AdvertsReactor>
) : MviReactorFactory<AdvertsReactor>() {

    override val reactor: AdvertsReactor
        get() = reactorProvider.get()
}
