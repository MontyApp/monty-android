package com.monty.ui.favourite

import com.sumera.koreactor.reactor.MviReactorFactory
import javax.inject.Inject
import javax.inject.Provider

class FavouriteAdvertsReactorFactory @Inject constructor(
    private val reactorProvider: Provider<FavouriteAdvertsReactor>
) : MviReactorFactory<FavouriteAdvertsReactor>() {

    override val reactor: FavouriteAdvertsReactor
        get() = reactorProvider.get()
}
