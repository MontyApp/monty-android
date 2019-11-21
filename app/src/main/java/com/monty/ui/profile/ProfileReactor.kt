package com.monty.ui.profile

import com.monty.ui.profile.contract.ProfileState
import com.sumera.koreactor.reactor.MviReactor
import com.sumera.koreactor.reactor.data.MviAction
import io.reactivex.Observable
import javax.inject.Inject

class ProfileReactor @Inject constructor() : MviReactor<ProfileState>() {

    override fun createInitialState() = ProfileState.INITIAL

    override fun bind(actions: Observable<MviAction<ProfileState>>) {}
}
