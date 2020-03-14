package com.monty.ui.map

import com.sumera.koreactor.reactor.MviReactorFactory
import javax.inject.Inject
import javax.inject.Provider

class SearchMapReactorFactory @Inject constructor(
    private val reactorProvider: Provider<SearchMapReactor>
) : MviReactorFactory<SearchMapReactor>() {

    override val reactor: SearchMapReactor
        get() = reactorProvider.get()
}
