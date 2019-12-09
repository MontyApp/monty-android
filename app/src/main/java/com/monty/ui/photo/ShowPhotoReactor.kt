package com.monty.ui.photo

import com.monty.ui.photo.contract.BackEvent
import com.monty.ui.photo.contract.ChangePhotoStateReducer
import com.monty.ui.photo.contract.OnBackAction
import com.monty.ui.photo.contract.ShowPhotoState
import com.sumera.koreactor.reactor.MviReactor
import com.sumera.koreactor.reactor.data.MviAction
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Named

class ShowPhotoReactor @Inject constructor(
    @Named("photoAddress") private val photoAddress: String,
    @Named("photoAddressType") private val photoAddressType: String
) : MviReactor<ShowPhotoState>() {

    override fun createInitialState() = ShowPhotoState("", "")

    override fun bind(actions: Observable<MviAction<ShowPhotoState>>) {
        val onBackAction = actions.ofActionType<OnBackAction>()

        attachLifecycleObservable
            .map { ChangePhotoStateReducer(photoAddress, photoAddressType) }
            .bindToView()

        onBackAction
            .map { BackEvent }
            .bindToView()
    }
}
