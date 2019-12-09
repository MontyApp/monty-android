package com.monty.ui.detail

import com.monty.domain.advert.GetAdvertObservabler
import com.monty.ui.detail.contract.*
import com.sumera.koreactor.reactor.MviReactor
import com.sumera.koreactor.reactor.data.MviAction
import io.reactivex.Observable
import javax.inject.Inject

class AdvertDetailReactor @Inject constructor(
    private val getAdvertObservabler: GetAdvertObservabler,
    private val advertId: Int
) : MviReactor<AdvertDetailState>() {

    override fun createInitialState() = AdvertDetailState.INITIAL

    override fun bind(actions: Observable<MviAction<AdvertDetailState>>) {
        val onPhotoClick = actions.ofActionType<OnPhotoClick>()
        val onBackAction = actions.ofActionType<OnBackAction>()

        onBackAction.map { BackEvent }.bindToView()

        onPhotoClick
            .flatMapSingle { stateSingle }
            .map { NavigateToShowPhotoEvent(it.advert.image) }
            .bindToView()

        attachLifecycleObservable
           .flatMap { getAdvertObservabler.init(advertId).execute() }
           .map { UpdateAdvertReducer(it) }
           .bindToView()
    }
}
