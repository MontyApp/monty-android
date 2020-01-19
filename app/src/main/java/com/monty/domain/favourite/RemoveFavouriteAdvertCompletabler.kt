package com.monty.domain.favourite

import com.monty.data.store.FavouriteAdvertsStore
import com.monty.domain.base.BaseCompletabler
import io.reactivex.Completable
import javax.inject.Inject

class RemoveFavouriteAdvertCompletabler @Inject constructor(
    private val favouriteAdvertsStore: FavouriteAdvertsStore
) : BaseCompletabler() {

    private var advertId: String = ""

    fun init(advertId: String) = apply {
        this.advertId = advertId
    }

    override fun create(): Completable {
        return favouriteAdvertsStore.removeFavouriteAdvert(advertId)
    }
}
