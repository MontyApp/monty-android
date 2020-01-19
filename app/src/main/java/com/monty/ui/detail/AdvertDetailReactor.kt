package com.monty.ui.detail

import com.monty.data.model.ui.User
import com.monty.domain.advert.GetAdvertObservabler
import com.monty.domain.favourite.AddFavouriteAdvertCompletabler
import com.monty.domain.favourite.RemoveFavouriteAdvertCompletabler
import com.monty.domain.location.GetMyLocationObservabler
import com.monty.domain.user.GetUserObservabler
import com.monty.domain.user.SyncUserCompletabler
import com.monty.ui.detail.contract.*
import com.sumera.koreactor.reactor.MviReactor
import com.sumera.koreactor.reactor.data.MviAction
import com.sumera.koreactor.util.extension.getChange
import io.reactivex.Observable
import javax.inject.Inject

class AdvertDetailReactor @Inject constructor(
    private val getAdvertObservabler: GetAdvertObservabler,
    private val syncUserCompletabler: SyncUserCompletabler,
    private val getUserObservabler: GetUserObservabler,
    private val getMyLocationObservabler: GetMyLocationObservabler,
    private val addFavouriteAdvertCompletabler: AddFavouriteAdvertCompletabler,
    private val removeFavouriteAdvertCompletabler: RemoveFavouriteAdvertCompletabler,
    private val advertId: String
) : MviReactor<AdvertDetailState>() {

    override fun createInitialState() = AdvertDetailState.INITIAL

    override fun bind(actions: Observable<MviAction<AdvertDetailState>>) {
        val onPhotoClick = actions.ofActionType<OnPhotoClick>()
        val onBackAction = actions.ofActionType<OnBackAction>()
        val onRefreshAction = actions.ofActionType<OnRefreshAction>()
        val onContactAction = actions.ofActionType<OnContactAction>()
        val onContactPhoneAction = actions.ofActionType<OnContactPhoneAction>()
        val onContactEmailAction = actions.ofActionType<OnContactEmailAction>()
        val onMapAction = actions.ofActionType<OnMapAction>()
        val onFavouriteAction = actions.ofActionType<OnFavouriteAction>()

        onBackAction.map { BackEvent }.bindToView()

        onFavouriteAction
            .flatMapSingle { stateSingle }
            .flatMapCompletable {
                if (it.advert.isFavourite) {
                    removeFavouriteAdvertCompletabler.init(advertId).execute()
                } else {
                    addFavouriteAdvertCompletabler.init(advertId).execute()
                }
            }
            .onErrorComplete()
            .toObservable<Unit>()
            .bindTo()

        onMapAction
            .flatMapSingle { stateSingle }
            .map { NavigateToMapEvent(it.advert.address.latitude, it.advert.address.longitude) }
            .bindToView()

        onContactAction
            .flatMapSingle { stateSingle }
            .filter { it.user != User.EMPTY }
            .map { ShowContactDialog(it.user.name) }
            .bindToView()

        onContactPhoneAction
            .flatMapSingle { stateSingle }
            .filter { it.user != User.EMPTY }
            .map { NavigateToPhoneEvent(it.user.phone) }
            .bindToView()

        onContactEmailAction
            .flatMapSingle { stateSingle }
            .filter { it.user != User.EMPTY }
            .map { NavigateToEmailEvent(it.user.email, it.advert.title) }
            .bindToView()

        onPhotoClick
            .flatMapSingle { stateSingle }
            .map { NavigateToShowPhotoEvent(it.advert.image) }
            .bindToView()

        Observable.merge(resumeLifecycleObservable, onRefreshAction)
            .flatMap { getMyLocationObservabler.execute() }
            .map { ChangeMyLocationReducer(it) }
            .bindToView()

        attachLifecycleObservable
            .flatMap { getAdvertObservabler.init(advertId).execute() }
            .map { UpdateAdvertReducer(it) }
            .bindToView()

        stateObservable.getChange { it.advert.userId }
            .filter { it.isNotEmpty() }
            .flatMapCompletable { syncUserCompletabler.init(it).execute() }
            .onErrorComplete()
            .toObservable<Unit>()
            .bindTo()

        stateObservable.getChange { it.advert.userId }
            .filter { it.isNotEmpty() }
            .flatMap { getUserObservabler.init(it).execute() }
            .map { ChangeUserReducer(it) }
            .bindToView()
    }
}
