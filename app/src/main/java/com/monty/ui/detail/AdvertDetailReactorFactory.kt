package com.monty.ui.detail

import com.sumera.koreactor.reactor.MviReactorFactory
import javax.inject.Inject
import javax.inject.Provider

class AdvertDetailReactorFactory @Inject constructor(
    private val reactorProvider: Provider<AdvertDetailReactor>
) : MviReactorFactory<AdvertDetailReactor>() {

    override val reactor: AdvertDetailReactor
        get() = reactorProvider.get()
}
