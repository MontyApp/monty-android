package com.monty.ui.adverts

import com.monty.data.model.ui.Category
import com.monty.domain.GetCategoriesSingler
import com.monty.domain.advert.GetAdvertsObservabler
import com.monty.domain.advert.SyncAdvertsCompletabler
import com.monty.domain.behavior.LoadingCompletableBehavior
import com.monty.domain.favourite.AddFavouriteAdvertCompletabler
import com.monty.domain.favourite.RemoveFavouriteAdvertCompletabler
import com.monty.domain.location.GetMyLocationObservabler
import com.monty.tool.permissions.LocationPermission
import com.monty.ui.adverts.contract.*
import com.monty.ui.base.placeholder.PartialLayoutState
import com.monty.ui.base.placeholder.PullState
import com.monty.ui.common.sort.SortOption
import com.sumera.koreactor.behaviour.completable
import com.sumera.koreactor.behaviour.messages
import com.sumera.koreactor.behaviour.triggers
import com.sumera.koreactor.reactor.MviReactor
import com.sumera.koreactor.reactor.data.AttachState
import com.sumera.koreactor.reactor.data.MviAction
import io.reactivex.Observable
import io.reactivex.Single
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AdvertsReactor @Inject constructor(
    private val getAdvertsObservabler: GetAdvertsObservabler,
    private val syncAdvertsCompletabler: SyncAdvertsCompletabler,
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
        val onSortClickAction = actions.ofActionType<OnSortClickAction>()
        val onSortOptionClickAction = actions.ofActionType<OnSortOptionClickAction>()
        val onRefreshAction = actions.ofActionType<OnRefreshAction>()
        val onAllowLocationAction = actions.ofActionType<OnAllowLocationAction>()

        onAdvertClickAction.map { NavigateToAdvertDetailEvent(it.advert.id) }.bindToView()
        onAddAdvertClickAction.map { NavigateToCreateAdvertEvent }.bindToView()
        onCategoriesClickAction.map { ShowCategoriesDialogEvent }.bindToView()
        onSortClickAction.map { ShowSortOptionsDialogEvent }.bindToView()
        onSortOptionClickAction.map { ChangeSelectedSortOptionReducer(it.sortOption) }.bindToView()
        onAllowLocationAction.map { ChangeIsLocationAllowedRecuder(true) }.bindToView()

        onSortOptionClickAction
            .filter { it.sortOption == SortOption.NEAREST }
            .filter { !locationPermission.isEnabled() }
            .map { RequestLocationPermissionEvent }
            .bindToView()

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

        Observable.merge(attachLifecycleObservable, onRefreshAction)
            .flatMapCompletable { syncAdvertsCompletabler.execute() }
            .onErrorComplete()
            .toObservable<Unit>()
            .bindTo()

        LoadingCompletableBehavior<AttachState, AdvertsState>(
            triggers = triggers(attachLifecycleObservable),
            cancelPrevious = true,
            worker = completable { syncAdvertsCompletabler.execute() },
            onStart = messages(),
            onError = messages { ErrorEvent(it.message.toString()) },
            onComplete = messages()
        )
        LoadingCompletableBehavior<OnRefreshAction, AdvertsState>(
            triggers = triggers(onRefreshAction),
            cancelPrevious = true,
            worker = completable { syncAdvertsCompletabler.execute() },
            onStart = messages { ChangeLayoutStateReducer(PartialLayoutState(pullState = PullState.REFRESHING)) },
            onError = messages { ChangeLayoutStateReducer(PartialLayoutState(pullState = PullState.IDLE)) },
            onComplete = messages { ChangeLayoutStateReducer(PartialLayoutState(pullState = PullState.IDLE)) }
        )

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
