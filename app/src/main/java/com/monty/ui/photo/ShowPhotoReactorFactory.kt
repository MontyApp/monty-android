package com.monty.ui.photo

import com.sumera.koreactor.reactor.MviReactorFactory
import javax.inject.Inject
import javax.inject.Provider

class ShowPhotoReactorFactory @Inject constructor(
    private val reactorProvider: Provider<ShowPhotoReactor>
) : MviReactorFactory<ShowPhotoReactor>() {

    override val reactor: ShowPhotoReactor
        get() = reactorProvider.get()
}
