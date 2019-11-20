package com.monty.ui.profile

import com.sumera.koreactor.reactor.MviReactorFactory
import javax.inject.Inject
import javax.inject.Provider

class ProfileReactorFactory @Inject constructor(
    private val reactorProvider: Provider<ProfileReactor>
) : MviReactorFactory<ProfileReactor>() {

    override val reactor: ProfileReactor
        get() = reactorProvider.get()
}
