package com.monty.domain.favourite

import com.monty.data.model.ui.Advert
import com.monty.data.store.FavouriteAdvertsStore
import com.monty.domain.base.BaseObservabler
import io.reactivex.Observable
import javax.inject.Inject

class GetFavouriteAdvertsObservabler @Inject constructor(
    private val favouriteAdvertsStore: FavouriteAdvertsStore
) : BaseObservabler<List<Advert>>() {

    override fun create(): Observable<List<Advert>> {
        return favouriteAdvertsStore.getFavouriteAdverts()
    }
}
