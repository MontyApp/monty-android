package com.monty.domain.favourite

import com.monty.data.store.FavouriteAdvertsStore
import com.monty.domain.base.BaseCompletabler
import io.reactivex.Completable
import javax.inject.Inject

class AddFavouriteAdvertCompletabler @Inject constructor(
    private val favouriteAdvertsStore: FavouriteAdvertsStore
) : BaseCompletabler() {

    private var advertId: Int = -1

    fun init(advertId: Int) = apply {
        this.advertId = advertId
    }

    override fun create(): Completable {
        return favouriteAdvertsStore.addFavouriteAdvert(advertId)
    }
}
