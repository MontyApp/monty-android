package com.monty.ui.adverts

import com.monty.data.model.ui.Category
import com.monty.domain.GetCategoriesSingler
import com.monty.domain.advert.GetAdvertsObservabler
import com.monty.domain.favourite.AddFavouriteAdvertCompletabler
import com.monty.domain.favourite.RemoveFavouriteAdvertCompletabler
import com.monty.domain.location.GetMyLocationObservabler
import com.monty.tool.permissions.LocationPermission
import com.monty.ui.adverts.contract.*
import com.sumera.koreactor.reactor.MviReactor
import com.sumera.koreactor.reactor.data.MviAction
import io.reactivex.Observable
import io.reactivex.Single
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AdvertsReactor @Inject constructor(
    private val getAdvertsObservabler: GetAdvertsObservabler,
    private val getCategoriesSingler: GetCategoriesSingler,
    private val locationPermission: LocationPermission,
    private val getMyLocationObservabler: GetMyLocationObservabler,
    private val addFavouriteAdvertCompletabler: AddFavouriteAdvertCompletabler,
    private val removeFavouriteAdvertCompletabler: RemoveFavouriteAdvertCompletabler
) : MviReactor<AdvertsState>() {

    override fun createInitialState() = AdvertsState.INITIAL

    override fun bind(actions: Observable<MviAction<AdvertsState>>) {
        val onAdvertClickAction = actions.ofActionType<OnAdvertClickAction>()
        val onFavouriteClickAction = actions.ofActionType<OnFavouriteClickAction>()
        val onCategoriesClickAction = actions.ofActionType<OnCategoriesClickAction>()
        val onCategoryClickAction = actions.ofActionType<OnCategoryClickAction>()
        val onAddAdvertClickAction = actions.ofActionType<OnAddAdvertClickAction>()
        val onRefreshAction = actions.ofActionType<OnRefreshAction>()
        val onAllowLocationAction = actions.ofActionType<OnAllowLocationAction>()

        onAdvertClickAction.map { NavigateToAdvertDetailEvent(it.advert.id) }.bindToView()
        onAddAdvertClickAction.map { NavigateToCreateAdvertEvent }.bindToView()
        onCategoriesClickAction.map { ShowCategoriesDialogEvent }.bindToView()
        onAllowLocationAction.map { ChangeIsLocationAllowedRecuder(true) }.bindToView()

        onCategoryClickAction
            .flatMapSingle { action ->
                stateSingle.map { state ->
                    ChangeSelectedCategoryReducer(
                        if (state.selectedCategory == action.category) Category.EMPTY else action.category
                    )
                }
            }.bindToView()

        onFavouriteClickAction
            .flatMapCompletable {
                if (it.advert.isFavourite) {
                    removeFavouriteAdvertCompletabler.init(it.advert.id).execute()
                } else {
                    addFavouriteAdvertCompletabler.init(it.advert.id).execute()
                }
            }.toObservable<Unit>()
            .bindTo()

        Observable.merge(resumeLifecycleObservable, onRefreshAction)
            .flatMap { getMyLocationObservabler.execute() }
            .map { ChangeMyLocationReducer(it) }
            .bindToView()

        resumeLifecycleObservable
            .flatMapSingle { Single.just(locationPermission.isEnabled()) }
            .map { ChangeIsLocationAllowedRecuder(it) }
            .bindToView()

        attachLifecycleObservable
            .flatMapSingle { Single.just(locationPermission.isEnabled()) }
            .filter { !it }
            .map { RequestLocationPermissionEvent }
            .bindToView()

        attachLifecycleObservable
            .delay(1500, TimeUnit.MILLISECONDS)
            .flatMap { getAdvertsObservabler.execute() }
            .map { UpdateAdvertsReducer(it) }
            .bindToView()

        attachLifecycleObservable
            .flatMapSingle { getCategoriesSingler.execute() }
            .map { ChangeCategoriesReducer(it) }
            .bindToView()
    }
}
